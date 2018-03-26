package com.lmhy.fisco.ctrl;

import com.lmhy.fisco.ext.ApiResult;
import com.lmhy.fisco.ext.BcosKit;
import com.lmhy.fisco.ext.FISCO;
import com.lmhy.fisco.service.SUserService;
import lombok.extern.slf4j.Slf4j;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@Slf4j
public class IndexCtrl extends BaseCtrl {

    @Autowired
    SUserService sUserService;



    @GetMapping("init")
    public ApiResult init() {
        BcosKit.deploySys();
        HashMap map = BcosKit.getAdr();
        log.info("部署用户合约完成");
        return toSuccess(map);
    }

    @GetMapping("test")
    public ApiResult test(){
        log.info("调用web3的getBlockNumber接口");
        EthBlockNumber ethBlockNumber = null;
        try {
            ethBlockNumber = FISCO.web3j.ethBlockNumber().sendAsync().get();
            log.info("当前区块高度:{}", ethBlockNumber.getBlockNumber());
            BigInteger bigInteger = sUserService.sUser.len().get().getValue();
            log.info("获取出来的int为：{}",bigInteger);
            ethBlockNumber = FISCO.web3j.ethBlockNumber().sendAsync().get();
            log.info("当前区块高度:{}", ethBlockNumber.getBlockNumber());
        } catch (ExecutionException |InterruptedException e) {
            e.printStackTrace();
        }




        return toSuccess();
    }

    @GetMapping("")
    public String index() throws IOException {
       return "Hello";
    }

    @GetMapping("upload")
    public ModelAndView uploadGet(ModelAndView modelMap){
        modelMap.setViewName("upload");

        return modelMap;
    }

    @PostMapping("upload")
    @ResponseBody
    public ApiResult upload(MultipartHttpServletRequest request){
        List<MultipartFile> files = request.getFiles("file");
        String param = request.getParameter("param");
        log.info(param);
        for (MultipartFile file : files) {
            log.info(file.getOriginalFilename());
        }
        log.info(param);

        return toSuccess();
    }
}
