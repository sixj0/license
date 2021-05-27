package com.sixj.license.verify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author sixiaojie
 * @date 2021-05-25-15:42
 */
@Slf4j
@Component
public class LicenseCheckListener implements CommandLineRunner {
    /**
     * 证书subject
     */
    @Value("${license.subject}")
    private String subject;

    /**
     * 公钥别称
     */
    @Value("${license.publicAlias}")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @Value("${license.storePass}")
    private String storePass;

    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @Value("${license.publicKeysStorePath}")
    private String publicKeysStorePath;



    @Override
    public void run(String... args) throws Exception {
        if(!StringUtils.isEmpty(licensePath)){
            log.info("++++++++ 开始安装证书 ++++++++");

            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(subject);
            param.setPublicAlias(publicAlias);
            param.setStorePass(storePass);
            param.setLicensePath(licensePath);
            param.setPublicKeysStorePath(publicKeysStorePath);

            LicenseVerify licenseVerify = new LicenseVerify();
            //安装证书
            licenseVerify.install(param);

            log.info("++++++++ 证书安装结束 ++++++++");
        }else {
            throw new RuntimeException("++++++++ 未配置License证书 ++++++++");
        }
    }
}
