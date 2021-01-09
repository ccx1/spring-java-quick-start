package com.ccx.result;

import com.ccx.enums.HttpStatus;
import lombok.Data;

@Data
public class CodeException extends RuntimeException {

    private int code;

    public CodeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CodeException(HttpStatus httpStatus) {
        super(httpStatus.getMsg());
        this.code = httpStatus.getCode();
    }


}
