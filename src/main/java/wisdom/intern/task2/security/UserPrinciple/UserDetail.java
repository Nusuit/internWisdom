package wisdom.intern.task2.security.UserPrinciple;

import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.repository.AccountRepository;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.repository.AccountRepository;

@Component
public class UserDetail {
    @Autowired
    private AccountRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tồn tại user");
        }
        return new UserPrincipal.build(user);
    }
}
