package com.felix.security.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
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
import java.security.PublicKey;
import java.util.Date;
import java.util.function.Function;

/**
 * @author felix-ma
 * @create 2019/1/17 13:45
 **/
@Component
public class JwtTokenResolve implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;

    private static PublicKey publicKey = null;


    @Value("${jwt.cert.filename}")
    private String certFileName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.keystore.password}")
    private String keyStorePassword;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Resource
    private ResourceLoader resourceLoader;

    private Clock clock = DefaultClock.INSTANCE;

    @PostConstruct
    private void init() {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:" + certFileName).getInputStream();
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

    public String getAuthorityFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
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

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
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
