package com.lakeslove.register.service.Impl;

import com.lakeslove.register.dao.RegisterDao;
import com.lakeslove.register.model.Register;
import com.lakeslove.register.service.RegisterService;
import com.lakeslove.register.util.Email;
import com.lakeslove.register.util.PropertyUtil;
import com.lakeslove.register.util.RandomCodeUtil;
import com.lakeslove.register.util.SpringEmailUtil;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


@Service
public class RegisterServiceImpl extends AbstractServiceImpl<Register, Long> implements RegisterService {

  private static final Logger log = LogManager.getLogger(RegisterServiceImpl.class);

  @Autowired
  private RegisterDao registerDao;

//  @Autowired
//  private UserDao userDao;

  @Autowired
  FreeMarkerConfigurer freeMarkerConfigurer;

  @Value("${spring.mail.username}")
  private String fromMail;//发送人

  @Override
  public Boolean existEmail(String email) {
    int emailCount = registerDao.existEmail(email);
    return emailCount > 0 ? true : false;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void sendEmail(String emailAddress) {
    String verificationCode = RandomCodeUtil.createNumberAndLetterCode(4);
    Register register = new Register();
    register.setEmail(emailAddress);
    register.setVerificationCode(verificationCode);
    registerDao.insertRegister(register);
    Email email = new Email();
    email.setFromEmailAddress(fromMail);
    email.setFromPersonName(fromMail);
    email.setToEmailAddresses(new String[] {emailAddress});
    email.setSubject("微博客验证码");
    Map<String, Object> freeMarkerContext = new HashMap<>();
    freeMarkerContext.put("code", verificationCode);
    freeMarkerContext.put("webAddress", PropertyUtil.getPropertyValue("web.address"));
    try {
      Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("email_register.ftl");
      String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, freeMarkerContext);
      email.setContent(content);
      SpringEmailUtil.sendEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Boolean validateEmailAndVerificationCode(String email, String code) {
    Register register = new Register();
    register.setEmail(email);
    register.setVerificationCode(code);
    HashMap<String, Integer> resultMap = registerDao.validateEmailAndVerificationCode(register);
    if (resultMap.size() > 0) {
      registerDao.deleteRegister((long) resultMap.get("id"));
//      userDao.insertUser(user);
      return true;
    }
    return false;
  }


}