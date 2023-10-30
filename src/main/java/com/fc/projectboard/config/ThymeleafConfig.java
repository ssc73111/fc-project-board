package com.fc.projectboard.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

  @Bean
  public SpringResourceTemplateResolver thymeleafTemplateResolver(
      SpringResourceTemplateResolver defaultTemplateResolver,
      Thymeleaf3Properties thymeleaf3Properties
  ) {
    defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

    return defaultTemplateResolver;
  }


  @RequiredArgsConstructor
  @Getter
  @ConstructorBinding
  @ConfigurationProperties("spring.thymeleaf3")
  public static class Thymeleaf3Properties {
    /**
     * Use Thymeleaf 3 Decoupled Logic
     */
    private final boolean decoupledLogic;

    //  @RequiredArgsConstructor
    //  @Getter 를 추가 하면 아래 코드가 필요없음
    //    public Thymeleaf3Properties(boolean decoupledLogic) {
    //      this.decoupledLogic = decoupledLogic;
    //    }
    //    public boolean isDecoupledLogic() {
    //      return decoupledLogic;
    //    }
  }

}