package com.sixj.license.controller;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.sixj.license.info.AbstractServerInfo;
import com.sixj.license.info.LinuxServerInfo;
import com.sixj.license.info.MacOsServerInfo;
import com.sixj.license.info.WindowsServerInfo;
import com.sixj.license.model.LicenseCheckModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 生成证书的Controller
 * @author sixiaojie
 * @date 2021-05-25-14:01
 */
@RestController
@RequestMapping("/license")
public class CreatorLicenseController {

    /**
     * 获取服务器硬件信息
     * @return
     */
    @GetMapping(value = "/getServerInfo")
    public LicenseCheckModel getServerInfo() {

        AbstractServerInfo abstractServerInfo;

        //根据不同操作系统类型选择不同的数据获取方法
        OsInfo osInfo = SystemUtil.getOsInfo();
        if (osInfo.isWindows()) {
            abstractServerInfo = new WindowsServerInfo();
        } else if (osInfo.isMac()) {
            abstractServerInfo = new MacOsServerInfo();
        }else{//其他服务器类型
            abstractServerInfo = new LinuxServerInfo();
        }

        return abstractServerInfo.getServerInfos();
    }
}
