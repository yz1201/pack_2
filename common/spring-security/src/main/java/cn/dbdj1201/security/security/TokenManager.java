package cn.dbdj1201.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token管理
 * @Author: dbdj1201
 * @Date: 2020-09-09
 */
@Component
public class TokenManager {

    private final String tokenSignKey = "gol&dbdj1201";

    public String createToken(String username) {
        long tokenExpiration = 24 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
    }

    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

//    public void removeToken(String token) {
//        //jwttoken无需删除，客户端扔掉即可。
//    }

}
