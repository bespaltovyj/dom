package com.rnd.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class HibernateConfig implements HibernatePropertiesCustomizer {

    private final MessageSource messageSource;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("javax.persistence.validation.factory", customValidator());
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(Validator.class)
    public LocalValidatorFactoryBean customValidator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }

}
