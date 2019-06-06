package com.lakeslove.register.config;

import java.io.File;
import java.io.FileNotFoundException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ResourceUtils;

@Configuration
@MapperScan("com.lakeslove.register.dao")
public class BaseConfig {

  /**
   * 配置tomcat的根目录，
   * 因为tomcat默认的跟目录是src下面的webapp/WEB-INF
   * @return
   */
  @Bean
  public AbstractServletWebServerFactory embeddedServletContainerFactory() throws FileNotFoundException {
    TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    //ResourceUtils.getFile("classpath:")获取的是classes的目录
    File resourcesFile = ResourceUtils.getFile("classpath:xml").getParentFile();
    tomcatServletWebServerFactory.setDocumentRoot(resourcesFile);
    return tomcatServletWebServerFactory;
  }

  @Bean
  public ResourceBundleMessageSource messageSource(){
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames(
        "message.message_error",
        "message.message_generic",
        "message.message_validate"
    );
    messageSource.setUseCodeAsDefaultMessage(false);
    messageSource.setCacheSeconds(600);
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

}
