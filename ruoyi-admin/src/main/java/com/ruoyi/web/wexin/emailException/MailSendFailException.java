package com.ruoyi.web.wexin.emailException;

/**
 * 邮箱发送失败
 */
public class MailSendFailException extends  ServiceExcepton {

    public MailSendFailException() {
        super();
    }

    public MailSendFailException(String message) {
        super(message);
    }

    public MailSendFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendFailException(Throwable cause) {
        super(cause);
    }

    protected MailSendFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
