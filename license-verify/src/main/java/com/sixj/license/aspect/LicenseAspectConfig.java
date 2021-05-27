package com.sixj.license.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sixiaojie
 * @date 2021-05-25-15:26
 */
@Configuration
public class LicenseAspectConfig {
    @Bean
    public LicenseAspect getLicenseAspect(){
        return new LicenseAspect();
    }

}
