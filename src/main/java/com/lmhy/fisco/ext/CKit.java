package com.lmhy.fisco.ext;

import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.*;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Hash;
import org.bcos.web3j.utils.Numeric;

import java.util.Arrays;
import java.util.HashMap;

public class CKit {
    private static HashMap<String, String> adrMap = new HashMap<>();

    public static void init(HashMap<String, String> _map) {
        adrMap = _map;
    }

    public static String get(String type) {
        return adrMap.get(type);
    }

    public static void main(String[] args) {
       /* Event event = new Event("Msg",
                Arrays.asList(new TypeReference<Address>() {
                }),
                Arrays.asList(new TypeReference<Uint256>() {
                }, new TypeReference<Utf8String>() {
                }));
        System.out.println(EventEncoder.encode(event));*/

        String hexStr = buildEventSignature("Msg(address,uint256,string)");
        System.out.println(hexStr);
        System.out.println(Numeric.hexStringToByteArray(hexStr));
    }

    static String buildEventSignature(String methodSignature) {
        byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash);
    }
}
