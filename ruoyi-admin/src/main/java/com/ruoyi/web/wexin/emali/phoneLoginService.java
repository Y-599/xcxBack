package com.ruoyi.web.wexin.emali;

import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;

public interface phoneLoginService {

    EmailRexPopj phone_login(String account, String password);

    void change_password(EmailRexPopj emailRexPopj);

}
