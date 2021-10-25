package com.chen;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.lang.System;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
class BlogApplicationTests {
    //    @Autowired
//    RedisTemplate redisTemplate;
    private long tt=1000*60*60*24;
    private String signature = "AKatsuki";
    /*jwt*/
    @Test
    void contextLoads() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //信息
                .claim("username", "Akatsuki")
                .claim("role","othersUser")
                .claim("userId","3")
                //主题
                .setSubject("user-test")
                .setExpiration(new Date(System.currentTimeMillis()+tt))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();

        System.out.println(jwtToken);
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(jwtToken);
        Claims body = claimsJws.getBody();
        System.out.println(body.get("userId"));
//        System.out.println(body);
    }
}
