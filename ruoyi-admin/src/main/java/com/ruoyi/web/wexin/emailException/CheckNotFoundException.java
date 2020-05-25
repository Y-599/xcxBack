package com.ruoyi.web.wexin.emailException;

public class CheckNotFoundException extends  ServiceExcepton {
    public CheckNotFoundException() {
        super();
    }

    public CheckNotFoundException(String message) {
        super(message);
    }

    public CheckNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CheckNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
