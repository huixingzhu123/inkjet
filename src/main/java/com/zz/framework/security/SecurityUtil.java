package com.zz.framework.security;

/**
 * 用户安全
 * @author yanjunhao
 * @date 2017年10月30日
 */
public class SecurityUtil {

    /**
     * 获取当前登录的id，暂时写死
     * @return 用户id
     */
    public static String getCurrentUserId() {
        return  "userid";
    }

    public static String getCurrentUserName() {
        return "username";
    }

    public static String getCurrentJH() {
        return "jh";
    }

    public static String getCurrentDept() {
        return "dept";
    }

    public static String getCurrentUser() {

        return "user" ;
    }

}
