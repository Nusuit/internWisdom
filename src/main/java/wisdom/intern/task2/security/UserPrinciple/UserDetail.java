package wisdom.intern.task2.security.UserPrinciple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wisdom.intern.task2.repository.AccountRepository;
import wisdom.intern.task2.entity.Account;

@Component
public class UserDetail implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm tài khoản bằng username thay vì email
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Chuyển đổi từ Account sang UserDetails
        return UserPrinciple.build(account);
    }
}
