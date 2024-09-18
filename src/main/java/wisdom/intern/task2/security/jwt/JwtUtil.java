package wisdom.intern.task2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Lấy secret từ file application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Thời gian hết hạn của token
    @Value("${jwt.expiration}")
    private long expiration;

    // Tạo SecretKey từ secret (phải đảm bảo secret đủ dài)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());  // Tạo khóa bí mật từ chuỗi secret
    }

    // Tạo JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // Thiết lập đối tượng (username)
                .setIssuedAt(new Date())  // Thiết lập thời gian tạo token
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // Thời gian hết hạn của token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Ký với thuật toán HMAC-SHA256 và secret key
                .compact();
    }

    // Trích xuất thông tin username từ JWT
    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();  // Lấy username từ claims
    }

    // Xác thực token và kiểm tra tính hợp lệ
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);  // Lấy username từ token
        return (extractedUsername.equals(username) && !isTokenExpired(token));  // Kiểm tra tính hợp lệ của token
    }

    // Kiểm tra token đã hết hạn chưa
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getClaimsFromToken(token).getExpiration();  // Lấy thời gian hết hạn từ token
        return expirationDate.before(new Date());  // Kiểm tra token có hết hạn hay chưa
    }

    // Lấy Claims từ JWT
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())  // Dùng khóa bí mật để kiểm tra token
                .build()
                .parseClaimsJws(token)  // Giải mã token để lấy claims
                .getBody();  // Lấy phần claims của token
    }
}
