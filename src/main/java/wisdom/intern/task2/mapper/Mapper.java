package wisdom.intern.task2.mapper;

import org.springframework.stereotype.Component;
import wisdom.intern.task2.dto.request.AccountRequestDto;
import wisdom.intern.task2.dto.response.AccountResponseDto;
import wisdom.intern.task2.entity.Account;

@Component
public abstract class Mapper {

    // Chuyển từ AccountRequestDto sang Account Entity (cho việc tạo và cập nhật)
    public Account toEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setUsername(accountRequestDto.getUsername());
        account.setPassword(accountRequestDto.getPassword());  // Password sẽ được mã hóa ở service
        account.setRole(accountRequestDto.getRole());
        return account;
    }

    // Chuyển từ Account Entity sang AccountResponseDto (trả về cho client)
    public AccountResponseDto toResponseDto(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setUsername(account.getUsername());
        accountResponseDto.setRole(account.getRole());
        return accountResponseDto;
    }

    public abstract Integer getUserIdByToken();
}
