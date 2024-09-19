package wisdom.intern.task2.initializer;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.repository.AccountRepository;

@Component
public class DataInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Kiểm tra xem có bất kỳ tài khoản admin nào chưa
        if (accountRepository.findByRole("ADMIN").isEmpty()) {
            // Nếu không có, tạo admin mặc định
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));  // Sử dụng mật khẩu mã hóa
            admin.setRole("ADMIN");

            accountRepository.save(admin);
        }
    }
}

