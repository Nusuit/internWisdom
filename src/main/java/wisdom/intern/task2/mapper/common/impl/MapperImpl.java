package wisdom.intern.task2.mapper.common.impl;

import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.mapper.common.Mapper;
import wisdom.intern.task2.repository.AccountRepository;
import wisdom.intern.task2.security.UserPrinciple.UserPrinciple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapperImpl implements Mapper {

    private final AccountRepository usersRepository;

    @Override
    public Integer getUserIdByToken() {
        // Logic như bạn đã định nghĩa
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("Can't get info of user current!");
            throw new MessageDescriptorFormatException("Can't get info of user current!");
        }
        UserPrinciple userDetails = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var userBy = userDetails.getId();
        Optional<Account> usersOptional = usersRepository.findById(Long.valueOf(userBy));
        if (usersOptional.isEmpty()) {
            log.error("User with username {} not found", usersOptional);
            throw new MessageDescriptorFormatException("Current user not found");
        }
        return userBy;
    }
}

