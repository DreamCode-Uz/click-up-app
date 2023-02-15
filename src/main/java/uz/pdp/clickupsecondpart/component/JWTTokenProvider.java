package uz.pdp.clickupsecondpart.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${spring.app.jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (expiration * 24 * 1000)))
                .setSubject(username)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            System.out.println("Qo'llab quvvatlanmaydigan JWT token");
        } catch (MalformedJwtException e) {
            System.out.println("Haqiqiy bo'lmagan JWS token");
        } catch (SignatureException e) {
            System.out.println("JWS tizim imzosi tekshiruvidan muvaffaqiyatsiz o'tdi");
        } catch (ExpiredJwtException e) {
            System.out.println("Foydalanish muddati tugagan token");
        } catch (IllegalArgumentException e) {
            System.out.println("Token bo'sh yoki null");
        }
        return false;
    }
}
