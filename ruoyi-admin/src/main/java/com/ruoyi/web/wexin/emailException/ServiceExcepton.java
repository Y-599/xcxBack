package com.ruoyi.web.wexin.emailException;

public class ServiceExcepton extends RuntimeException {
    public ServiceExcepton() {
        super();
    }

    public ServiceExcepton(String message) {
        super(message);
    }

    public ServiceExcepton(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceExcepton(Throwable cause) {
        super(cause);
    }

    protected ServiceExcepton(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
