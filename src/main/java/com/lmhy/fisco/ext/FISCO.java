package com.lmhy.fisco.ext;

import lombok.extern.slf4j.Slf4j;
import org.bcos.channel.client.Service;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FISCO {
    public static Web3j web3j = null;
    public static Credentials credentials = null;
    public static final BigInteger GAS_PRICE = new BigInteger("30000000");
    public static final BigInteger GAS_LIMIT = new BigInteger("30000000");
    public static final BigInteger INIT_WEI = new BigInteger("0");

    public static void init(Service service) {
        service.run();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        web3j = Web3j.build(channelEthereumService);


        ECKeyPair keyPair = null;
        try {

            keyPair = Keys.createEcKeyPair();
            Address address = new Address(Keys.getAddress(keyPair));
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        credentials = Credentials.create(keyPair);


        log.info("调用web3的getBlockNumber接口");
        EthBlockNumber ethBlockNumber = null;
        try {
            ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        } catch (ExecutionException |InterruptedException e) {
            e.printStackTrace();
        }
        log.info("当前区块高度:{}", ethBlockNumber.getBlockNumber());

        readUserContract();


    }

    public static void readUserContract() {
        HashMap map = BcosKit.getAdr();
        if (map != null) {
            CKit.init(map);
        }
    }
}
