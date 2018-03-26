package com.lmhy.fisco.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmhy.fisco.contract.SUser;
import com.lmhy.fisco.ext.BcosKit;
import com.lmhy.fisco.ext.CKit;
import com.lmhy.fisco.ext.FISCO;
import lombok.extern.slf4j.Slf4j;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class SUserService {
    public SUser sUser;


    public SUserService() {
        String address = CKit.get("sUser");
        sUser = SUser.load(address, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);
    }

    private JSONObject mergeResult(List<Type> list) {
        JSONObject temp = new JSONObject(5);
        Type<Uint256> v1 = list.get(0);
        Type<Address> v2 = list.get(1);
        Type<Uint256> v3 = list.get(2);
        Type<Utf8String> v4 = list.get(3);
        Type<Uint256> v5 = list.get(4);

        temp.put("id", v1.getValue());
        temp.put("address", v2.toString());
        temp.put("cate", v3.getValue());
        temp.put("name", v4.toString());
        temp.put("time", v5.getValue());
        return temp;
    }


    public Integer reg(String name, Integer type) {
        BcosKit.Accout accout = BcosKit.generateAccount();
        if (accout == null) {
            throw new RuntimeException("生成账户异常");
        }
        log.info("生成的账户为:{}", accout);
        try {
            Integer id = sUser.len().get().getValue().intValue();
            TransactionReceipt tr = sUser.reg(new Utf8String(name), new Address(accout.getAddress()), new Uint256(new BigInteger(type + ""))).get();
            log.info("交易输出为:{}", tr);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject findByIndex(Integer _index) {
        try {
            List<Type> list = sUser.accmap(new Uint256(new BigInteger(_index + ""))).get();
//            Tuple5<BigInteger, String, BigInteger, String, BigInteger> tuple = sUser.accmap(new BigInteger(_index + "")).send();
            return mergeResult(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray findAll() {
        try {
            BigInteger len = sUser.len().get().getValue();
            int lenInt = len.intValue();
            JSONArray array = new JSONArray(lenInt);
            for (int i = 0; i < lenInt; i++) {
                List<Type> list = sUser.accmap(new Uint256(new BigInteger(i + ""))).get();
//                Tuple5<BigInteger, String, BigInteger, String, BigInteger> tuple5 = sUser.accmap(new BigInteger(i + "")).send();
                array.add(mergeResult(list));
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveOrder(String orderAdr) {
        try {
            TransactionReceipt tr = sUser.saveOrder(new Address(orderAdr)).get();
            log.info("{}", tr);
        } catch (Exception e) {
            log.error("SUserService@saveOrder error", e);
        }
    }

    public String[] findAllOrder() {

        try {
            Uint256 lengthUint = sUser.orderSize().get();
            BigInteger lengthBig = lengthUint.getValue();
            int size = lengthBig.intValue();
            String[] arr = new String[size];
            for (int i = 0; i < size; i++) {
                BigInteger tempBig = new BigInteger(i + "");
                Address address = sUser.orderList(new Uint256(tempBig)).get();
                String adr = address.toString();
                arr[i] = adr;
            }
            return arr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
