package com.lmhy.fisco.ext;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult<T> {
    private Boolean success = true;
    private String msg = "操作成功";
    private T data;
}
