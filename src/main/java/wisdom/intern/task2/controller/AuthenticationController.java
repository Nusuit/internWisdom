package wisdom.intern.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import wisdom.intern.task2.dto.AuthenticationRequest;
import wisdom.intern.task2.dto.AuthenticationResponse;
import wisdom.intern.task2.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Xác thực người dùng
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        // Lấy thông tin người dùng sau khi xác thực thành công
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Lấy vai trò của người dùng từ danh sách authorities
        String role = userDetails.getAuthorities().stream()
                .findFirst()  // Lấy vai trò đầu tiên
                .map(grantedAuthority -> grantedAuthority.getAuthority())  // Lấy tên quyền (vai trò)
                .orElse("USER");  // Nếu không có quyền, mặc định là USER

        // Tạo JWT với username và vai trò
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
