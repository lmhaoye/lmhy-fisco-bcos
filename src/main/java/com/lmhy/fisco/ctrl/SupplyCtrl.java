package com.lmhy.fisco.ctrl;

import com.lmhy.fisco.ext.ApiResult;
import com.lmhy.fisco.service.SOrderService;
import com.lmhy.fisco.service.SUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/s")
public class SupplyCtrl extends BaseCtrl {
    @Autowired
    private SUserService sUserService;
    @Autowired
    private SOrderService sOrderService;

    @PostMapping("user")
    public ApiResult<String> userPost(String name, @RequestParam(value = "type", defaultValue = "0") Integer type) {
        if(StringUtils.isBlank(name)){
            return toFail();
        }
        return toSuccess(sUserService.reg(name, type));
    }

    @GetMapping("user")
    public ApiResult userGet(String id) {
        if(StringUtils.isNotBlank(id))
            return toSuccess(sUserService.findByIndex(NumberUtils.toInt(id)));
        return toSuccess(sUserService.findAll());
    }

    @PostMapping("order")
    public ApiResult orderPost(Integer fee, String from, String to) {
        return toSuccess(sOrderService.create(fee, from, to));
    }

    @GetMapping("order")
    public ApiResult orderGet(String id) {
        if (StringUtils.isNotBlank(id))
            return toSuccess(sOrderService.findOne(id));
        else {
            String[] orderArr = sUserService.findAllOrder();
            return toSuccess(sOrderService.findByArr(orderArr));
        }
    }

    @PostMapping("order/splic")
    public ApiResult orderSplic(String adr, String from, String to, Integer fee) {
        return toSuccess(sOrderService.splic(adr, from, to, fee));
    }

    @GetMapping("order/splic")
    public ApiResult orderSplicGet(String id) {
        return toSuccess(sOrderService.findData(id, "splic"));
    }

    @PostMapping("order/load")
    public ApiResult orderLoadPost(String adr,String reNo,String from,String to,Integer fee){
        return toSuccess(sOrderService.load(adr,reNo,from,to,fee));
    }
    @GetMapping("order/load")
    public ApiResult orderLoadGet(String id){
        return toSuccess(sOrderService.findData(id,"load"));
    }
    @PostMapping("order/repay")
    public ApiResult orderRepayPost(String adr,String reNo,String from,String to,Integer fee){
        return toSuccess(sOrderService.repay(adr,reNo,from,to,fee));
    }
    @GetMapping("order/repay")
    public ApiResult orderRepayGet(String id){
        return toSuccess(sOrderService.findData(id,"repay"));
    }

}
