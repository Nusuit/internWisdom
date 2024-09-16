package wisdom.intern.task2.securityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.security.core.context.SecurityContextHolder;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.repository.AccountRepository;
import wisdom.intern.task2.security.UserPrinciple.UserPrinciple;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapperImpl {
    private final AccountRepository usersRepository;
    @Override
    public Long getUserIdByToken() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("Can't get info of user current!");
            throw new MessageDescriptorFormatException("Can't get info of user current!");
        }
        UserPrinciple userDetails = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var userBy = userDetails.getId();
        Optional<Account> usersOptional = usersRepository.findById(userBy);
        if (usersOptional.isEmpty()) {
            log.error("User with username {} not found", usersOptional);
            throw new MessageDescriptorFormatException("Current user not found");
        }
        return userBy;
    }
}
