package wisdom.intern.task2.service;

import wisdom.intern.task2.dto.request.AccountRequestDto;
import wisdom.intern.task2.dto.response.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto accountRequestDto);
    List<AccountResponseDto> getAllAccounts();
    AccountResponseDto getAccountById(Long accountId);
    AccountResponseDto updateAccount(Long accountId, AccountRequestDto accountRequestDto);
    void deleteAccount(Long accountId);
}
