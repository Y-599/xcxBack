package com.ruoyi.web.wexin.emailException;

/**
 * 用户提交的验证码错误
 */
public class MailCodeFailException extends  ServiceExcepton {
    public MailCodeFailException() {
        super();
    }

    public MailCodeFailException(String message) {
        super(message);
    }

    public MailCodeFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailCodeFailException(Throwable cause) {
        super(cause);
    }

    protected MailCodeFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
