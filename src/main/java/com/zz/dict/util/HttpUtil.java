package com.zz.dict.util;

import com.zz.framework.support.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http工具类
 * @author yanjunhao
 * @date 2017年10月31日
 */
public class HttpUtil {
    /**
     * 获取请求中的参数
     * @param request HttpServletRequest
     * @return 参数map
     */
    public static Map<String, Object> getParameterFromRequest(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String,Object> paramMap = new HashMap<>(16);
        while (names.hasMoreElements()){
            String name = names.nextElement();
            paramMap.put(name,request.getParameter(name));
        }
        return paramMap;
    }

    /**
     * 获取真实的ip
     * @param request HttpServletRequest
     * @return ip字符串
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
