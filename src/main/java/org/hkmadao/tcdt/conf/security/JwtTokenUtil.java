package org.hkmadao.tcdt.conf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.rsa.privateKey}")
    private String privateKeyFileName;

    @Value("${jwt.rsa.publicKey}")
    private String publicKeyFileName;

    public long getExpiration() {
        return expiration;
    }

    /**
     * 生成token
     *
     * @param claims claims
     * @return token
     */
    private String generateToken(Map<String, Object> claims) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(KeyUtil.getPrivateKeyFromPem(privateKeyFileName), SignatureAlgorithm.RS256)
//                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取负载
     *
     * @param token token
     * @return claims
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
//            claims = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
            claims = Jwts.parserBuilder()
//                    .requireAudience("string")
                    .setSigningKey(KeyUtil.getPublicKeyFromPem(publicKeyFileName))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JWT格式验证失败:{}");
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 生成过期时间
     *
     * @return expiration
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param token 带tokenHead的token
     */
    public String refreshHeadToken(String token) throws Exception {
        if (!StringUtils.hasLength(token)) {
            return null;
        }
//        String token = oldToken.substring(tokenHead.length());
//        if (!StringUtils.hasLength(token)) {
//            return null;
//        }
        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //如果token已经过期，不支持刷新
        if (isTokenExpired(token)) {
            return null;
        } else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

}
