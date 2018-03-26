package com.lmhy.fisco.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmhy.fisco.contract.SOrder;
import com.lmhy.fisco.ext.FISCO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class SOrderService {
    @Autowired
    private SUserService sUserService;

    private SOrder get(String address) {
        return SOrder.load(address, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);
    }

    public JSONObject create(Integer fee, String from, String to) {
        String code = "tx-" + System.currentTimeMillis();
        try {
            SOrder sOrder = SOrder.deploy(FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT, FISCO.INIT_WEI, new Utf8String(code), new Uint256(new BigInteger(fee + "")), new Address(from), new Address(to)).get();

            String address = sOrder.getContractAddress();
            sUserService.saveOrder(address);
            log.info("生成订单[{}]成功", code);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address", address);
            jsonObject.put("code", code);

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String splic(String adr, String from, String to, Integer fee) {
        SOrder sOrder = get(adr);
        try {
            String no = "txcd" + System.currentTimeMillis();
            TransactionReceipt tr = sOrder.splitOrder(new Utf8String(no), new Address(from), new Address(to), new Uint256(new BigInteger(fee + ""))).get();
            if (tr != null)
                return no;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public String load(String adr, String reNo, String from, String to, Integer fee) {
        SOrder sOrder = get(adr);
        try {
            String no = "txld" + System.currentTimeMillis();
            TransactionReceipt tr = sOrder.loadFun(new Utf8String(reNo), new Utf8String(no), new Address(from), new Address(to), new Uint256(new BigInteger(fee + ""))).get();
            if (tr != null) return no;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public String repay(String adr, String reNo, String from, String to, Integer fee) {
        SOrder sOrder = get(adr);
        try {
            String no = "txrp" + System.currentTimeMillis();
            TransactionReceipt tr = sOrder.repayFun(new Utf8String(reNo), new Utf8String(no), new Address(from), new Address(to), new Uint256(new BigInteger(fee + ""))).get();
            if (tr != null) return no;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public JSONArray findByArr(String[] arr) {
        JSONArray array = new JSONArray(arr.length);
        SOrder sOrder;
        for (String adr : arr) {
            sOrder = get(adr);
            try {
                List<Type> info = sOrder.info().get();
                Type<Utf8String> v1 = info.get(0);
                Type<Uint256> v2= info.get(1);
//                Tuple2<String, BigInteger> info = sOrder.info().send();
                String from = sOrder.from().get().toString();
                String to = sOrder.to().get().toString();
                BigInteger time = sOrder.createTime().get().getValue();
                BigInteger cmapSize = sOrder.cmapSize().get().getValue();
                BigInteger lmapSize = sOrder.lmapSize().get().getValue();
                BigInteger rmapSize = sOrder.rmapSize().get().getValue();

                JSONObject jsonObject = new JSONObject(9);
                jsonObject.put("adr", adr);
                jsonObject.put("code", v1.toString());
                jsonObject.put("fee", v2.getValue());
                jsonObject.put("from", from);
                jsonObject.put("to", to);
                jsonObject.put("createTime", time);
                jsonObject.put("cmapSize", cmapSize);
                jsonObject.put("lmapSize", lmapSize);
                jsonObject.put("rmapSize", rmapSize);

                array.add(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    public JSONArray findData(String adr, String queryType) {
        SOrder sOrder = get(adr);
        JSONArray array = new JSONArray();
        try {
            switch (queryType) {
                case "splic": {
                    BigInteger cmapSize = sOrder.cmapSize().get().getValue();
                    Integer size = cmapSize.intValue();
                    if (size == 0) return array;
                    for (int i = 0; i < size; i++) {
                        List<Type> list = sOrder.cmap(new Uint256(new BigInteger(i + ""))).get();
                        JSONObject jsonObject = new JSONObject();
                        Type<Utf8String> v1 = list.get(0);
                        Type<Address> v2 = list.get(1);
                        Type<Address> v3 = list.get(2);
                        Type<Uint256> v4 = list.get(3);
                        Type<Uint256> v5 = list.get(4);

                        jsonObject.put("id", i);
                        jsonObject.put("no", v1.toString());
                        jsonObject.put("from", v2.toString());
                        jsonObject.put("ow", v3.toString());
                        jsonObject.put("time", v4.getValue());
                        jsonObject.put("fee", v5.getValue());
                        array.add(jsonObject);
                    }
                }
                break;
                case "load": {
                    BigInteger lmapSize = sOrder.lmapSize().get().getValue();
                    Integer size = lmapSize.intValue();
                    if (size == 0) return array;
                    for (int i = 0; i < size; i++) {
//                        Tuple6<String, String, String, String, BigInteger, BigInteger> tuple6 = sOrder.lmap(new BigInteger(i + "")).send();
                        List<Type> list = sOrder.lmap(new Uint256(new BigInteger(i + ""))).get();
                        JSONObject jsonObject = new JSONObject();
                        Type<Utf8String> v1 = list.get(0);
                        Type<Utf8String> v2 = list.get(1);
                        Type<Address> v3 = list.get(2);
                        Type<Address> v4 = list.get(3);
                        Type<Uint256> v5 = list.get(4);
                        Type<Uint256> v6 = list.get(5);

                        jsonObject.put("no", v1.toString());
                        jsonObject.put("reNo", v2.toString());
                        jsonObject.put("from", v3.toString());
                        jsonObject.put("to", v4.toString());
                        jsonObject.put("fee", v5.getValue());
                        jsonObject.put("time", v6.getValue());

                        /*jsonObject.put("no", tuple6.getValue1());
                        jsonObject.put("reNo", tuple6.getValue2());
                        jsonObject.put("from", tuple6.getValue3());
                        jsonObject.put("to", tuple6.getValue4());
                        jsonObject.put("fee", tuple6.getValue5());
                        jsonObject.put("time", tuple6.getValue6());*/

                        array.add(jsonObject);
                    }
                }
                break;
                case "repay": {
                    BigInteger rmapSize = sOrder.rmapSize().get().getValue();
                    Integer size = rmapSize.intValue();
                    if (size == 0) return array;
                    for (int i = 0; i < size; i++) {
//                        Tuple6<String, String, String, String, BigInteger, BigInteger> tuple6 = sOrder.rmap(new BigInteger(i + "")).send();
                        List<Type> list = sOrder.rmap(new Uint256(new BigInteger(i + ""))).get();
                        JSONObject jsonObject = new JSONObject();
                        Type<Utf8String> v1 = list.get(0);
                        Type<Utf8String> v2 = list.get(1);
                        Type<Address> v3 = list.get(2);
                        Type<Address> v4 = list.get(3);
                        Type<Uint256> v5 = list.get(4);
                        Type<Uint256> v6 = list.get(5);

                        jsonObject.put("no", v1.toString());
                        jsonObject.put("reNo", v2.toString());
                        jsonObject.put("from", v3.toString());
                        jsonObject.put("to", v4.toString());
                        jsonObject.put("fee", v5.getValue());
                        jsonObject.put("time", v6.getValue());
                        array.add(jsonObject);
                    }
                }
                break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("findOne", e);
        }
        return array;
    }

    public JSONObject findOne(String adr) {
        JSONObject jsonObject = new JSONObject(12);
        try {
            SOrder sOrder = get(adr);
//            Tuple2<String, BigInteger> info = sOrder.info().send();
            List<Type> info = sOrder.info().get();
            String from = sOrder.from().get().toString();
            String to = sOrder.to().get().toString();
            BigInteger time = sOrder.createTime().get().getValue();
            BigInteger cmapSize = sOrder.cmapSize().get().getValue();
            BigInteger lmapSize = sOrder.lmapSize().get().getValue();
            BigInteger rmapSize = sOrder.rmapSize().get().getValue();

            Type<Utf8String> v1 = info.get(0);
            Type<Uint256> v2 = info.get(1);

            jsonObject.put("adr", adr);
            jsonObject.put("code", v1.toString());
            jsonObject.put("fee", v2.getValue());
            jsonObject.put("from", from);
            jsonObject.put("to", to);
            jsonObject.put("createTime", time);
            jsonObject.put("cmapSize", cmapSize);
            jsonObject.put("lmapSize", lmapSize);
            jsonObject.put("rmapSize", rmapSize);

            JSONArray cArr = findData(adr, "splic");
            JSONArray lArr = findData(adr, "load");
            JSONArray rArr = findData(adr, "repay");

            jsonObject.put("cmapArr", cArr);
            jsonObject.put("lmapArr", lArr);
            jsonObject.put("rmapArr", rArr);
        } catch (Exception e) {
            log.error("findOne", e);
        }
        return jsonObject;
    }
}
