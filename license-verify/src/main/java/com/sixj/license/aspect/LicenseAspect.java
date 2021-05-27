package com.sixj.license.aspect;

import com.sixj.license.verify.LicenseVerify;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author sixiaojie
 * @date 2021-05-25-15:22
 */
@Aspect
@Order(1)
public class LicenseAspect {
    /**
     * AOP 需要判断共享组的判断点 @License
     */
    @Pointcut("@annotation(com.sixj.license.aspect.License)")
    public void isLicensePointcut() {}

    /**
     * AOP点之前就开始判断
     */
    @Before("isLicensePointcut()")
    public void beforeIsLicensePointcutCheck(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        License license = method.getAnnotation(License.class);
        if (!Objects.isNull(license)) {
            LicenseVerify licenseVerify = new LicenseVerify();
            //1. 校验证书是否有效
            boolean verifyResult = licenseVerify.verify();
            if(!verifyResult){
                //抛异常
                throw new RuntimeException("您的证书无效，请核查服务器是否取得授权或重新申请证书");
            }
        }
    }
}
