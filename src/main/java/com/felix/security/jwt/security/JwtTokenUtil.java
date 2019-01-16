package com.felix.security.jwt.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.felix.security.jwt.entity.UserAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.security.cert.X509Certificate;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;
    //加载jwt.jks文件
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.keystore.filename}")
    private String keyStoreFileName;

    @Value("${jwt.keystore.password}")
    private String keyStorePassword;

    @Value("${jwt.key.aliase}")
    private String keyAliase;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Resource
    private ResourceLoader resourceLoader;

    private Clock clock = DefaultClock.INSTANCE;

    @PostConstruct
    private void init() {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:" + keyStoreFileName).getInputStream();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, keyStorePassword.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(keyAliase, keyStorePassword.toCharArray());
            inputStream.close();
            inputStream = resourceLoader.getResource("classpath:" + "cert.pem").getInputStream();
            X509Certificate cert = X509Certificate.getInstance(inputStream);
            publicKey = cert.getPublicKey();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<String> getAuthorityFromToken(String token) {
        String authorityJson = getClaimFromToken(token, Claims::getAudience);
        return JSONObject.parseArray(
                authorityJson, UserAuthority.class).stream().map(UserAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
//                .setSigningKey(secret)
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), JSONObject.toJSONString(userDetails.getAuthorities()));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String aduience) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(aduience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512, secret)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, secret)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (user.isEnabled() &&
                username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getUpdateTime())
        );
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
