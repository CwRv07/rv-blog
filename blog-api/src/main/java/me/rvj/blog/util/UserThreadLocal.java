package me.rvj.blog.util;

/**
 * @program: rv-blog
 * @description: UserThreadLocal
 * @author: Rv_Jiang
 * @date: 2022/5/27 10:38
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static final ThreadLocal<Long> USER_THRED_LOCAL = new ThreadLocal<>();

    public static void set(Long UserId) {
        USER_THRED_LOCAL.set(UserId);
    }

    public static Long get() {
        return USER_THRED_LOCAL.get();
    }

    public static void remove() {
        USER_THRED_LOCAL.remove();
    }
}
