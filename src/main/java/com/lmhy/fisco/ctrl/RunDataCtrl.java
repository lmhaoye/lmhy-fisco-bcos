package com.lmhy.fisco.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmhy.fisco.contract.RunData;
import com.lmhy.fisco.ext.ApiResult;
import com.lmhy.fisco.ext.FISCO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.channel.dto.EthereumResponse;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("rd")
public class RunDataCtrl extends BaseCtrl {
    @GetMapping("deploy")
    public ApiResult deploy(){
        String address = StringUtils.EMPTY;
        try {
            RunData runData = RunData.deploy(FISCO.web3j,FISCO.credentials,FISCO.GAS_PRICE, FISCO.GAS_LIMIT, FISCO.INIT_WEI).get();
            address = runData.getContractAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return toSuccess(address);
    }

    @GetMapping("run")
    public ApiResult run(){
        try {
            String adr = "0x70fba72209974313ec21d74bf48d2dd9e47b86b4";
            RunData runData = RunData.load(adr, FISCO.web3j, FISCO.credentials, FISCO.GAS_PRICE, FISCO.GAS_LIMIT);

            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

            runData.setMap(new Utf8String("Hello"),new TransactionSucCallback(){
                @Override
                public void onResponse(EthereumResponse ethereumResponse) {
                    try {
                        TransactionReceipt transactionReceipt = objectMapper.readValue(ethereumResponse.getContent(), TransactionReceipt.class);
                        log.info("[RunData]成功发送交易，内容：{}",transactionReceipt);
                        List<RunData.ClickEventResponse> clickEventResponses = RunData.getClickEvents(transactionReceipt);
                        clickEventResponses.forEach(clickEventResponse -> {
                            Address address = clickEventResponse._from;
                            String conValue = clickEventResponse.con.getValue();
                            Integer idVal = clickEventResponse.id.getValue().intValue();
                            log.info("event log => {}-{}-{}",address,conValue,idVal);
                        });
                    }catch (Exception e){
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
