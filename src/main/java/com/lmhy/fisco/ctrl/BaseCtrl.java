package com.lmhy.fisco.ctrl;

import com.lmhy.fisco.ext.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BaseCtrl<T> {
    protected ApiResult toSuccess() {
        return toSuccess(null);
    }

    protected ApiResult toSuccess(T t) {
        return toResult(true, "操作成功", t);
    }

    protected ApiResult toFail() {
        return toResult(false, "操作失败", null);
    }
    protected ApiResult toFail(String msg) {
        return toResult(false, msg, null);
    }

    protected ApiResult<T> toResult(boolean result, String msg, T t) {
        return new ApiResult<>(result, msg, t);
    }
}
