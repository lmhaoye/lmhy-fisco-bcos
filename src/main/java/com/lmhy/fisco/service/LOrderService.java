package com.lmhy.fisco.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmhy.fisco.contract.LOrder;
import com.lmhy.fisco.ext.FISCO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class LOrderService {
    private LOrder get(String adr) {
        return LOrder.load(adr, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);
    }

    public JSONObject create(Integer fee, String goods, Integer weight, String from, String to, String go) {
        String myCode = "my" + System.currentTimeMillis();
        String wlCode = "wl" + System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject(3);
        try {
            LOrder lOrder = LOrder.deploy(FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT,FISCO.INIT_WEI, new Utf8String(myCode), new Uint256(new BigInteger(fee + "")), new Utf8String(goods), new Uint256(new BigInteger(weight + "")), new Address(from), new Address(to), new Address(go), new Utf8String(wlCode)).get();
            String address = lOrder.getContractAddress();
            log.info("address:{}", address);
            jsonObject.put("myCode", myCode);
            jsonObject.put("wlCode", wlCode);
            jsonObject.put("address", address);
            jsonObject.put("fee",fee);
            jsonObject.put("goods",goods);
            jsonObject.put("weight",weight);
            jsonObject.put("from",from);
            jsonObject.put("to",to);
            jsonObject.put("go",go);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String createYd(String adr) {
        LOrder lOrder = get(adr);
        String ydCode = "yd" + System.currentTimeMillis();
        try {
            TransactionReceipt tr = lOrder.geneYd(new Utf8String(ydCode)).get();
            if (tr != null)
                return ydCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public String createTs(String adr, String carNo, Integer weight) {
        LOrder lOrder = get(adr);
        try {
            String tsCode = "ts" + System.currentTimeMillis();
            TransactionReceipt tr = lOrder.geneTs(new Utf8String(tsCode), new Utf8String(carNo), new Uint256(new BigInteger(weight + ""))).get();
            if (tr != null)
                return tsCode;
        } catch (Exception e) {
            log.error("", e);
        }
        return StringUtils.EMPTY;
    }

    public JSONArray findTs(String adr) {
        LOrder lOrder = get(adr);
        JSONArray array = new JSONArray();
        try {
            Integer size = lOrder.ydSize().get().getValue().intValue();
            if (size == 0) return array;
            for (int i = 0; i < size; i++) {
//                Tuple4<String, String, BigInteger, BigInteger> tuple4 = lOrder.ydMap(new BigInteger(i + "")).send();
                List<Type> list = lOrder.ydMap(new Uint256(new BigInteger(i + ""))).get();
                Type<Utf8String> v1 = list.get(0);
                Type<Utf8String> v2 = list.get(1);
                Type<Uint256> v3 = list.get(2);
                Type<Uint256> v4 = list.get(3);
                JSONObject jsonObject = new JSONObject(6);

                jsonObject.put("code", v1.toString());
                jsonObject.put("carNo", v2.toString());
                jsonObject.put("weight", v3.getValue());
                jsonObject.put("state", v4.getValue());
                array.add(jsonObject);
            }
        } catch (Exception e) {
            log.error("findTs", e);
        }
        return array;
    }

    public String createReceipt(String adr, String reNo) {
        LOrder lOrder = get(adr);
        try {
            String reCode = "rc" + System.currentTimeMillis();
            TransactionReceipt tr = lOrder.toReceipt(new Utf8String(reNo), new Utf8String(reCode)).get();
            if (tr != null)
                return reCode;
        } catch (Exception e) {
            log.error("createReceipt", e);
        }
        return StringUtils.EMPTY;
    }

    public JSONArray findReceipt(String adr) {
        LOrder lOrder = get(adr);
        JSONArray array = new JSONArray();
        try {
            Integer size = lOrder.rcSize().get().getValue().intValue();
            if (size == 0) return array;
            for (int i = 0; i < size; i++) {
//                Tuple3<String, String, BigInteger> tuple3 = lOrder.rcMap(new BigInteger(i + "")).send();
                List<Type> list = lOrder.rcMap(new Uint256(new BigInteger(i + ""))).get();
                Type<Utf8String> v1 = list.get(0);
                Type<Utf8String> v2 = list.get(1);
                Type<Uint256> v3 = list.get(2);
                JSONObject jsonObject = new JSONObject(3);
                jsonObject.put("code", v1.toString());
                jsonObject.put("reNo", v2.toString());
                jsonObject.put("time", v3.getValue());
                array.add(jsonObject);
            }
        } catch (Exception e) {
            log.error("findReceipt", e);
        }
        return array;
    }

    public JSONArray findInfo(String adr) {
        JSONArray resultArr = new JSONArray();
        JSONArray tsArr = findTs(adr);
        JSONArray rcArr = findReceipt(adr);
        for (int i = 0; i < tsArr.size(); i++) {
            JSONObject ele = tsArr.getJSONObject(i);
            for (int j = 0; j < rcArr.size(); j++) {
                JSONObject rEle = rcArr.getJSONObject(j);
                if (StringUtils.equals(ele.getString("code"), rEle.getString("reNo"))) {
                    ele.put("rcCode", rEle.getString("code"));
                    ele.put("rcTime", rEle.get("time"));
                    break;
                }
            }
            resultArr.add(ele);
        }
        return resultArr;

    }
}
