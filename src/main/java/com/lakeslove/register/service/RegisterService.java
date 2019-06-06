package com.lakeslove.register.service;

import com.lakeslove.register.model.Register;

public interface RegisterService extends AbstractService<Register, Long>{
	Boolean existEmail(String email);
	Boolean validateEmailAndVerificationCode(String email, String code);
	void sendEmail(String email);
}
