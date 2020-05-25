package com.ruoyi.web.wexin.emailException;

public class EmailRexAleadException  extends  ServiceExcepton{
    public EmailRexAleadException() {
        super();
    }

    public EmailRexAleadException(String message) {
        super(message);
    }

    public EmailRexAleadException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailRexAleadException(Throwable cause) {
        super(cause);
    }

    protected EmailRexAleadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
