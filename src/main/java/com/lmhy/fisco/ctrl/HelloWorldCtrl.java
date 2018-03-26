package com.lmhy.fisco.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmhy.fisco.contract.HelloWorld;
import com.lmhy.fisco.ext.ApiResult;
import com.lmhy.fisco.ext.FISCO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.channel.dto.EthereumResponse;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("hw")
@Slf4j
public class HelloWorldCtrl extends BaseCtrl {

    private static final String FILE_PATH = "c:/helloword.json";
    @GetMapping("deploy")
    public ApiResult deploy(){
        String adr = StringUtils.EMPTY;
        try {
            HelloWorld helloWorld = HelloWorld.deploy(FISCO.web3j,FISCO.credentials,FISCO.GAS_PRICE, FISCO.GAS_LIMIT, FISCO.INIT_WEI).get();
            adr = helloWorld.getContractAddress();

            JSONObject json = new JSONObject();
            json.put("hwAdr",adr);
            Files.write(Paths.get(FILE_PATH), json.toJSONString().getBytes());
        } catch (InterruptedException |ExecutionException |IOException e) {
            e.printStackTrace();
        }
        return toSuccess(adr);
    }

    @GetMapping("run")
    public ApiResult run(){
        try {
            List<String> strList = Files.readAllLines(Paths.get(FILE_PATH));
            String jsonStr = StringUtils.join(strList, "");
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String adr = jsonObject.getString("hwAdr");
            HelloWorld helloWorld = HelloWorld.load(adr,FISCO.web3j,FISCO.credentials,FISCO.GAS_PRICE, FISCO.GAS_LIMIT);

            String str =helloWorld.say().get().getValue();
            log.info("[HelloWorld]获取合约数据为:{}",str);
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            helloWorld.set(new Utf8String("Hello Fisco"), new TransactionSucCallback() {
                @Override
                public void onResponse(EthereumResponse ethereumResponse) {
                    try {
                        TransactionReceipt transactionReceipt = objectMapper.readValue(ethereumResponse.getContent(), TransactionReceipt.class);
                        log.info("[HelloWorld]成功发送交易，内容：{}",transactionReceipt);
                        List<HelloWorld.MsgEventResponse> msgEventResponseList = HelloWorld.getMsgEvents(transactionReceipt);
                        for (HelloWorld.MsgEventResponse response : msgEventResponseList) {
                            String address = response._from.getTypeAsString();
                            int code = response._code.getValue().intValue();
                            String str = response._str.getValue();
                            log.info("[HelloWorld]event log: {}-{}-{}",address,code,str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return toSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return toFail();
    }
}
