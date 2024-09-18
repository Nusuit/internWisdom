package wisdom.intern.task2.mapper;

import org.springframework.stereotype.Component;
import wisdom.intern.task2.dto.request.AccountRequestDto;
import wisdom.intern.task2.dto.response.AccountResponseDto;
import wisdom.intern.task2.entity.Account;

@Component
public class AccountMapper {

    // Chuyển từ AccountRequestDto sang Account Entity
    public Account toEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setUsername(accountRequestDto.getUsername());
        account.setPassword(accountRequestDto.getPassword());
        account.setRole(accountRequestDto.getRole());
        return account;
    }

    // Chuyển từ Account Entity sang AccountResponseDto
    public AccountResponseDto toResponseDto(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setUsername(account.getUsername());
        accountResponseDto.setRole(account.getRole());
        return accountResponseDto;
    }
}
