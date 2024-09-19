package wisdom.intern.task2.mapper.account;

import org.springframework.stereotype.Component;
import wisdom.intern.task2.dto.AccountInfoDto;
import wisdom.intern.task2.entity.Account;

@Component
public class AccountMapper {

    // Chuyển từ Account Entity sang AccountInfoDto (trả về cho client)
    public AccountInfoDto toResponseDto(Account account) {
        AccountInfoDto accountInfoDto = new AccountInfoDto();
        accountInfoDto.setUsername(account.getUsername());
        accountInfoDto.setRole(account.getRole());
        return accountInfoDto;
    }

    public AccountInfoDto convertToAccountInfoDto(Account account) {
        AccountInfoDto dto = new AccountInfoDto();
        dto.setUsername(account.getUsername());
        dto.setRole(account.getRole());
        // Bổ sung các trường khác nếu cần
        return dto;
    }

}
