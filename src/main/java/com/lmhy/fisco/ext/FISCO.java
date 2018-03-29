package com.lmhy.fisco.ext;

import com.alibaba.fastjson.JSONObject;
import com.lmhy.fisco.contract.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.channel.client.Service;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.EthLog;
import rx.Observable;
import rx.Subscription;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("当前区块高度:{}", ethBlockNumber.getBlockNumber());

        readUserContract();

        Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
            log.info("[new tx] {}", tx);
        });


        try {
            List<String> strList = Files.readAllLines(Paths.get("c:/helloword.json"));
            String jsonStr = StringUtils.join(strList, "");
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String adr = jsonObject.getString("hwAdr");

            Event event = new Event("Msg",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                    }),
                    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                    }, new TypeReference<Utf8String>() {
                    }));
            log.info("event - encode:{}",EventEncoder.encode(event));
            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, adr);
            filter.addSingleTopic(EventEncoder.encode(event));

            web3j.ethLogObservable(filter).map(x -> {
                List<String> topics = x.getTopics();
                String encodedEventSignature = EventEncoder.encode(event);
                log.info("event----{}",topics);
                log.info("event----{}",encodedEventSignature);
//                EventValues eventValues = extractEventParameters(event, log);
                return x;
            });

            /*EthLog ethLog = web3j.ethGetLogs(filter).send();
            List<EthLog.LogResult>  rs = ethLog.getLogs();
            log.info("{}",rs);*/

            /*HelloWorld helloWorld = HelloWorld.load(adr, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);

            Observable<HelloWorld.MsgEventResponse> ob = helloWorld.msgEventObservable(DefaultBlockParameterName.EARLIEST,
                    DefaultBlockParameterName.LATEST);
            ob.subscribe(da -> log.info("event log -->{}", da));
            log.info("{}", ob);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void readUserContract() {
        HashMap map = BcosKit.getAdr();
        if (map != null) {
            CKit.init(map);
        }
    }
}
