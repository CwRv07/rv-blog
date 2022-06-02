package me.rvj.blog.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: UserThreadLocal
 * @author: Rv_Jiang
 * @date: 2022/5/27 10:38
 */
public class UserThreadLocal {

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_IP_ADDR="userIpAddr";

    private UserThreadLocal() {
    }

    private static final ThreadLocal<Map<String, String>> USER_THRED_LOCAL = new ThreadLocal<>();

    public static void set(String key, String value) {
        Map<String, String> local = USER_THRED_LOCAL.get();
        if(ObjectUtils.isEmpty(local)){
            local=new HashMap<>();
        }
        local.put(key, value);
        USER_THRED_LOCAL.set(local);
    }

    public static String get(String key) {
        return USER_THRED_LOCAL.get().get(key);
    }

    public static void remove() {
        USER_THRED_LOCAL.remove();
    }
}
