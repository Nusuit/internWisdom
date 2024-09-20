package wisdom.intern.task2.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ request
        String token = getTokenFromRequest(request);

        // Kiểm tra nếu token tồn tại và hợp lệ
        if (token != null) {
            try {
                // Lấy username từ token
                String username = jwtUtil.extractUsername(token);

                // Nếu token hợp lệ và chưa có authentication, tiến hành xác thực
                if (username != null && jwtUtil.validateToken(token, username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Tạo đối tượng authentication dựa trên thông tin userDetails
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Đặt thông tin xác thực vào SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu token không hợp lệ (Optional)
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token không hợp lệ hoặc hết hạn.");
                return;
            }
        }

        // Tiếp tục thực hiện filter chain
        filterChain.doFilter(request, response);
    }

    // Phương thức lấy token từ request
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bỏ "Bearer " ra khỏi token
        }
        return null;
    }
}
