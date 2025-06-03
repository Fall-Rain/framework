package com.framework.token.Impl;

import com.framework.properties.TokenProperties;
import com.framework.token.TokenService;
import com.framework.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TokenServiceImpl implements TokenService {
    private TokenProperties tokenProperties;
    private SecretKey secretKey;
    private RedisTemplate<Object, Object> redisTemplate;

    public TokenServiceImpl(TokenProperties tokenProperties, RedisTemplate<Object, Object> redisTemplate) {
        this.tokenProperties = tokenProperties;
        this.secretKey = Keys.hmacShaKeyFor(tokenProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
    }

    public String generateSystemToken(String uid) {
        String token = Jwts.builder()
                .subject("system")
                .claim("uid", uid)
                .claim("random", Math.random())
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
        redisTemplate.opsForValue().set("%s::%s".formatted(tokenProperties.getUserPos(), uid), token, tokenProperties.getTokenTime(), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("%s::%s".formatted(tokenProperties.getTokenPos(), token), "", tokenProperties.getTokenTime(), TimeUnit.MINUTES);
        return token;
    }

    public String getUid() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader(tokenProperties.getTokenHeader());
        return getUid(token);
    }

    public String getUid(String token) {
        if (Objects.isNull(token)) {
            return null;
        }
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

            if (!Objects.equals(claims.getSubject(), tokenProperties.getSubject())) {
                return null;
            }
            String uid = claims.get("uid", String.class);

            String redisToken = (String) redisTemplate.opsForValue().getAndExpire("%s::%s".formatted(tokenProperties.getUserPos(), uid), tokenProperties.getTokenTime(), TimeUnit.MINUTES);
            if (!Objects.equals(token, redisToken)) {
                redisTemplate.delete("%s::%s".formatted(tokenProperties.getTokenPos(), token));
                return null;
            } else {
                redisTemplate.expire("%s::%s".formatted(tokenProperties.getTokenPos(), token), tokenProperties.getTokenTime(), TimeUnit.MINUTES);
            }
            return uid;
        } catch (Exception e) {
            return null;
        }
    }


}
