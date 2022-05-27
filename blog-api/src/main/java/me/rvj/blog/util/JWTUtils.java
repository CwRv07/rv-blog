package me.rvj.blog.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: JWTUtils
 * @author: Rv_Jiang
 * @date: 2022/5/26 13:53
 */
public class JWTUtils {

    private static final String JWT_TOKEN = "123456RvJiang!@#$$";
    public static final String MAP_Key = "userId";

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(MAP_Key, userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 签发算法，秘钥为jwtToken
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN)
                // body数据，要唯一，自行设置
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 一天的有效时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(JWT_TOKEN).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

//    public static void main(String[] args) {
//        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        //加密所需的salt
//        textEncryptor.setPassword("mszlu_blog_$#@wzb_&^%$#");
//        //要加密的数据（数据库的用户名或密码）
//        String username = textEncryptor.encrypt("root");
//        String password = textEncryptor.encrypt("root");
//        System.out.println("username:" + username);
//        System.out.println("password:" + password);
//        System.out.println(textEncryptor.decrypt("29cZ+X9cNmECjbLXT2P/BBZWReVl30NS"));
//    }
}
