package com.chen.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {
    private static long overTime = 1000 * 60 * 60 * 24;
    private static String signature = "xIjeFo7I8Am0unha";


    public static String createToken(String userId, String userName) {
//        System.out.println("=======create Token==========");
//        System.out.println(userId);
//        System.out.println(userName);
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
//                //信息
                .claim("userName", userName)
                .claim("userId", userId)
//                .claim("role","user")
                //主题
                .setSubject("user-sub")
                .setExpiration(new Date(System.currentTimeMillis() + overTime))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();

        return token;
    }

    public static Boolean checkToken(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = jwtParser.setSigningKey(JwtUtils.signature).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }
    public static Boolean isMyBlog(String token,String userId){
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        }
        Claims claim = claimsJws.getBody();
        String id = (String) claim.get("userId");
        if (id.equals(userId)){
            return true;
        }else {
            return false;
        }
    }
}
