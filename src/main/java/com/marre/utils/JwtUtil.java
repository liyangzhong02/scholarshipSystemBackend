package com.marre.utils;

import com.marre.constant.JwtClaimsConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: JwtUtil
 * @author: Marre
 * @creat: 2024/9/12 14:34
 * Jwt工具类
 * JWT =
 * Header(描述 JWT 的元数据，定义了生成签名的算法以及 Token 的类型)
 * +
 * Payload(用来存放实际需要传递的数据，包含声明(Claims))
 * +
 * Signature(服务器通过 Payload、Header 和一个密钥(Secret)使用 Header 里面指定的签名算法（默认是 HMAC SHA256）生成)
 */
public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法 充当Header
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 过期时间 = 生成JWT的时间 + 设定的TTL
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);
        // JWT的Signature
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置签名使用的签名算法signatureAlgorithm 和 签名使用的秘钥secretKey
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public static String createToken(String secretKey, long ttlMillis, Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, id);
        return createJWT(secretKey, ttlMillis, claims);
    }



}

