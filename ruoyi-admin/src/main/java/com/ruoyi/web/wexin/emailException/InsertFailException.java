package com.ruoyi.web.wexin.emailException;

public class InsertFailException extends  ServiceExcepton {
    public InsertFailException() {
        super();
    }

    public InsertFailException(String message) {
        super(message);
    }

    public InsertFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertFailException(Throwable cause) {
        super(cause);
    }

    protected InsertFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
