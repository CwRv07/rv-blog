package me.rvj.blog.vo;

/**
 * @program: rv-blog
 * @description: 错误码
 * @author: Rv_Jiang
 * @date: 2022/5/26 12:41
 */
public enum ErrorCode {

    //    通用
    PARAMS_ERROR(400, "参数错误"),
    SERVER_BUSY(500, "服务器繁忙"),

    //    登录注册 && 权限
    ACCOUNT_PWD_ERROR(400, "用户名或密码错误"),
    ACCOUNT_EXIST(400, "账号已存在"),
    NO_LOGIN(401, "未登录"),
    SESSION_TIME_OUT(404, "会话超时"),

    TOKEN_ERROR(401, "token不合法"),
    NO_PERMISSION(401, "无访问权限"),

    //    文章操作
    ARTICLE_NOT_EXIST(400, "当前文章可能不存在"),

    //    标签操作
    TAG_HAS_EXIST(400, "当前标签可能已存在"),
    TAG_NOT_EXIST(400, "当前标签可能不存在"),

    //    游客操作
    NICKNAME_HAS_EXIST(400,"当前昵称已被使用"),
    EMAIL_INVALID(400,"当前邮箱无效"),
    USER_NOT_EXIXT(400,"当前用户可能不存在"),

    //    图片操作
    INVALID_IMAGE_TYPES(400,"不支持当前图片格式"),
    INVAILD_IMAGE_SITE(400,"当前图片内存过大")

    ;

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
