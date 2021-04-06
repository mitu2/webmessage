package com.brageast.project.webmessage.util;

import com.brageast.project.webmessage.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class JwtUtils {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = TimeUnit.DAYS.toMillis(30);

    public static final Key SIGN_KEY = Keys.hmacShaKeyFor("C292E9833E772C31EE51C0686FFDDA34".getBytes(StandardCharsets.UTF_8));

    private static final Gson GSON = new GsonBuilder().create();

    private JwtUtils() {

    }

    public static String createToken(User user) {
        return createToken(user, TOKEN_EXPIRED_TIME);
    }

    public static String createToken(User user, long time) {
        final Map<String, String> map = Collections.singletonMap("username", user.getUsername());
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(GSON.toJson(map))
                .signWith(SIGN_KEY)
                .setIssuedAt(Date.from(Instant.now()));
        if (time > 0) {
            final Calendar instance = Calendar.getInstance();
//            instance.add(Calendar.MILLISECOND, time);
            instance.setTimeInMillis(instance.getTimeInMillis() + time);
            final Date exp = instance.getTime();
            jwtBuilder.setExpiration(exp);
        }
        return jwtBuilder.compact();
    }

    public static String getUsername(String token) throws Exception {
        Objects.requireNonNull(token, "token 不能为NULL");
        final Jwt parse = Jwts.parserBuilder()
                .setSigningKey(SIGN_KEY)
                .build()
                .parse(token);
        String body = parse.getBody().toString();
        return GSON.fromJson(body, JsonElement.class)
                .getAsJsonObject()
                .get("sub")
                .getAsJsonObject()
                .get("username")
                .getAsString();
    }
}
