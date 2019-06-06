package com.lakeslove.register.controller;

import com.lakeslove.register.service.RegisterService;
import com.lakeslove.register.util.JSONData;
import com.lakeslove.register.util.JSONUtil;
import com.lakeslove.register.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private RegisterService registerService;

	@PostMapping("sendVerificationCode")
	public String validateEmail(String email) throws Exception {
		String message;
		if (StringUtils.isEmpty(email) || !ValidateUtils.isEmail(email)) {
			message = getI18nMessage("error.email.format");
			return getJsonData(false, message);
		}
		boolean existEmail = registerService.existEmail(email);
		if (existEmail) {
			message = getI18nMessage("error.email.has.existed");
			return getJsonData(false, message);
		}
		registerService.sendEmail(email);
		message = getI18nMessage("email.verification.code.has.send");
		return getJsonData(true, message);
	}

	public String getJsonData(Boolean mark, String message) throws Exception {
		JSONData jsonData = new JSONData(mark, message);
		return JSONUtil.writeValueAsString(jsonData);
	}

	@PostMapping("valid")
	public Boolean valid(String email, String code) {
		return registerService.validateEmailAndVerificationCode(email, code);
	}

}
