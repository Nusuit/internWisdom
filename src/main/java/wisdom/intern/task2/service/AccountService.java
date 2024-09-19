package wisdom.intern.task2.service;

import wisdom.intern.task2.dto.AccountInfoDto;
import wisdom.intern.task2.entity.Account;

import java.util.List;

public interface AccountService {
    AccountInfoDto saveOrUpdateAccount(Long accountId, Account account);
    List<AccountInfoDto> getAllAccounts(Integer pageNo, Integer pageSize, String sortBy, String sortType);
    AccountInfoDto getAccountById(Long accountId);
    void deleteAccount(Long accountId);
}
