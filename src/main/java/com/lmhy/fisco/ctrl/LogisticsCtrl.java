package com.lmhy.fisco.ctrl;

import com.lmhy.fisco.ext.ApiResult;
import com.lmhy.fisco.service.LOrderService;
import com.lmhy.fisco.service.LUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/l")
public class LogisticsCtrl extends BaseCtrl {
    @Autowired
    private LUserService lUserService;
    @Autowired
    private LOrderService lOrderService;

    @GetMapping("user")
    public ApiResult userGet() {
        return toSuccess(lUserService.findAll());
    }

    @PostMapping("user")
    public ApiResult userPost(String name, Integer type) {
        return toSuccess(lUserService.reg(name, type));
    }

    @PostMapping("order")
    public ApiResult order(Integer fee, String goods, Integer weight, String from, String to, String go) {
        return toSuccess(lOrderService.create(fee, goods, weight, from, to, go));
    }

    @PostMapping("order/yd")
    public ApiResult orderYd(String adr) {
        return toSuccess(lOrderService.createYd(adr));
    }

    @GetMapping("order/ts")
    public ApiResult orderTs(String adr) {
        return toSuccess(lOrderService.findTs(adr));
    }

    @PostMapping("order/ts")
    public ApiResult orderTsPost(String adr, String carNo, Integer weight) {
        return toSuccess(lOrderService.createTs(adr, carNo, weight));
    }

    @PostMapping("order/rc")
    public ApiResult orderRcPost(String adr, String reNo) {
        return toSuccess(lOrderService.createReceipt(adr, reNo));
    }

    @GetMapping("order/rc")
    public ApiResult orderRcGet(String adr) {
        return toSuccess(lOrderService.findInfo(adr));
    }
}
