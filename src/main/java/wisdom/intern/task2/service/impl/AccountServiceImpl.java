package wisdom.intern.task2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wisdom.intern.task2.dto.request.AccountRequestDto;
import wisdom.intern.task2.dto.response.AccountResponseDto;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.mapper.Mapper;
import wisdom.intern.task2.repository.AccountRepository;
import wisdom.intern.task2.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Để mã hóa mật khẩu

    // Tạo mới tài khoản (Create Account)
    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        Account account = mapper.toEntity(accountRequestDto);
        account.setPassword(passwordEncoder.encode(accountRequestDto.getPassword()));  // Mã hóa mật khẩu
        Account savedAccount = accountRepository.save(account);
        return mapper.toResponseDto(savedAccount);  // Trả về DTO không chứa mật khẩu
    }

    // Lấy danh sách tất cả tài khoản (Get All Accounts)
    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }

    // Lấy chi tiết tài khoản theo ID (Get Account By ID)
    @Override
    public AccountResponseDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        return mapper.toResponseDto(account);
    }

    // Cập nhật tài khoản theo ID (Update Account)
    @Override
    public AccountResponseDto updateAccount(Long accountId, AccountRequestDto accountRequestDto) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Nếu người dùng là ROLE_USER, chỉ cho phép cập nhật tài khoản của chính họ
        if (!isAdmin && !existingAccount.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("User không được phép cập nhật tài khoản của người khác.");
        }

        // Cập nhật tài khoản
        existingAccount.setUsername(accountRequestDto.getUsername());

        // Nếu người dùng gửi mật khẩu mới, mã hóa mật khẩu trước khi cập nhật
        if (accountRequestDto.getPassword() != null && !accountRequestDto.getPassword().isEmpty()) {
            existingAccount.setPassword(passwordEncoder.encode(accountRequestDto.getPassword()));
        }

        // Chỉ ADMIN mới có thể thay đổi vai trò (role) của tài khoản
        if (isAdmin) {
            existingAccount.setRole(accountRequestDto.getRole());
        }

        // Lưu thông tin tài khoản đã được cập nhật
        Account updatedAccount = accountRepository.save(existingAccount);

        // Trả về đối tượng DTO, không bao gồm mật khẩu
        return mapper.toResponseDto(updatedAccount);
    }


    // Xóa tài khoản (Delete Account)
    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        accountRepository.delete(account);
    }
}
