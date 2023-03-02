package com.example.pytest.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String SECRET_KEY = "7A25432646294A404E635266556A586E3272357538782F413F4428472D4B6150";

    /**
     * 解析token內的使用者
     * @param token
     * @return
     */
    public String extractUsername(String token)  {
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * 跟據使用者建立token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    /**
     * 建立token
     * @param extractClaims
     * @param userDetails
     * @return
     */
    public String generateToken(
            Map<String , Object> extractClaims
            , UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 *24)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 檢查token是否有效
     * 檢查包含使者是否正確與是否過期
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpire(token);

    }

    /**
     * 檢查token是否過期
     * @param token
     * @return
     */
    private boolean isTokenExpire(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 解析token過期時間
     * @param token
     * @return
     */
    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    /**
     * 解析token內指定內容
     * @param token
     * @param claimResolver
     * @return
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * 解析jwt
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 獲得簽名
     * @return
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
