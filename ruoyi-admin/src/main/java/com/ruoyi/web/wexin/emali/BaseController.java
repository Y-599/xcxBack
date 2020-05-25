package com.ruoyi.web.wexin.emali;


import com.ruoyi.web.JsonResult;
import com.ruoyi.web.wexin.emailException.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


/**
 * 控制器类的基类
 */
@RestController
@ControllerAdvice
public class BaseController {

	/**
	 * 操作成功的状态码
	 */
	static final int SUCCESS = 200;


	@ExceptionHandler(ServiceExcepton.class)
	public JsonResult<Void> handle(Throwable e) {
		JsonResult<Void> jr = new JsonResult<>(e);

		if (e instanceof MailSendFailException) {
			jr.setState(201);//邮件发送失败
		}else if(e instanceof EmailRexInfoNotException){
			jr.setState(202);//用户注册信息获取失败

		}else if(e instanceof EmailRexAleadException){
			jr.setState(203);//邮箱已经注册过

		}else if(e instanceof MailCodeFailException){
			jr.setState(204);//用户输入的验证码错误

		}else if(e instanceof FileEmptyException){
			jr.setState(205);//用户输入的验证码错误

		}
		else if(e instanceof FileSizeException){
			jr.setState(206);//用户输入的验证码错误

		}
		else if(e instanceof FileStateException){
			jr.setState(207);//用户输入的验证码错误

		}
		else if(e instanceof FileTypeException){
			jr.setState(208);//用户输入的验证码错误

		}
		else if(e instanceof FileUploadIOException){
			jr.setState(209);//用户输入的验证码错误

		}
		else if(e instanceof InsertFailException){
			jr.setState(210);//用户输入的验证码错误

		}
		else if(e instanceof UserNotFoundException){
			jr.setState(211);//用户输入的验证码错误

		}
		else if(e instanceof CheckNotFoundException){
			jr.setState(212);//用户输入的验证码错误

		}


		return jr;
	}
}