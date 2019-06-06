package com.lakeslove.register.dao;

import com.lakeslove.register.model.Register;
import java.util.HashMap;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterDao {

	Integer existEmail(String email);

	HashMap<String, Integer> validateEmailAndVerificationCode(Register register);

	void insertRegister(Register register);

	int deleteRegister(Long id);
}
