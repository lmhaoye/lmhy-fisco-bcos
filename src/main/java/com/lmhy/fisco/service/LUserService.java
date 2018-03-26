package com.lmhy.fisco.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmhy.fisco.contract.LUser;
import com.lmhy.fisco.ext.BcosKit;
import com.lmhy.fisco.ext.CKit;
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

@Service
@Slf4j
public class LUserService {
    private LUser lUser;

    public LUserService() {
        String adr = CKit.get("lUser");
        lUser = LUser.load(adr, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);
    }

    public String reg(String name, Integer type) {
        BcosKit.Accout accout = BcosKit.generateAccount();
        log.info("生成用户数据为：{}", accout);
        try {
            TransactionReceipt tr = lUser.add(new Address(accout.getAddress()), new Utf8String(name), new Uint256(new BigInteger(type + ""))).get();
            if (tr != null)
                return accout.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public JSONArray findAll() {
        JSONArray array = new JSONArray();
        try {
            Integer size = lUser.umapSize().get().getValue().intValue();
            for (int i = 0; i < size; i++) {
//                Tuple3<String, String, BigInteger> tuple3 = lUser.umap(new BigInteger(i + "")).send();
                List<Type> list = lUser.umap(new Uint256(new BigInteger(i + ""))).get();
                JSONObject jsonObject = new JSONObject(3);
                Type<Address> v1 = list.get(0);
                Type<Utf8String> v2 = list.get(1);
                Type<Uint256> v3 = list.get(2);
                jsonObject.put("adr", v1.toString());
                jsonObject.put("name", v2.toString());
                jsonObject.put("type", v3.getValue());
                array.add(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public void saveOrder(String adr) {
        try {
            lUser.addOrder(new Address(adr)).get();
        } catch (Exception e) {
            log.error("saveOrder", e);
        }
    }

}
