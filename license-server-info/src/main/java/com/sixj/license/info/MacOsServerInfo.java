package com.sixj.license.info;

import cn.hutool.system.oshi.OshiUtil;
import oshi.hardware.ComputerSystem;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取Mac系统服务器的基本信息
 * @author sixiaojie
 * @date 2021-05-25-13:46
 */
public class MacOsServerInfo extends AbstractServerInfo {
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if(inetAddresses != null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;

        //1\. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if(inetAddresses != null && inetAddresses.size() > 0){
            //2\. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected String getCPUSerial() throws Exception {

        ComputerSystem system = OshiUtil.getSystem();
        return system.getHardwareUUID();

    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        ComputerSystem system = OshiUtil.getSystem();
        return system.getBaseboard().getSerialNumber();
    }

}
