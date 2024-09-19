package wisdom.intern.task2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wisdom.intern.task2.dto.AccountInfoDto;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.mapper.account.AccountMapper;
import wisdom.intern.task2.mapper.common.impl.MapperImpl;
import wisdom.intern.task2.repository.AccountRepository;
import wisdom.intern.task2.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MapperImpl mapper;


    @Autowired
    private PasswordEncoder passwordEncoder;  // Để mã hóa mật khẩu

    // Tạo mới hoặc cập nhật tài khoản (gộp chức năng)
    @Override
    public AccountInfoDto saveOrUpdateAccount(Long accountId, Account account) {
        Account existingAccount;

        if (accountId == null) {
            // Nếu không có ID, tức là tạo tài khoản mới
            account.setPassword(passwordEncoder.encode(account.getPassword()));  // Mã hóa mật khẩu
            existingAccount = accountRepository.save(account);
        } else {
            // Nếu có ID, tức là đang cập nhật tài khoản
            existingAccount = accountRepository.findById(accountId)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

            // Lấy thông tin người dùng hiện tại từ MapperImpl
            Integer currentUserId = mapper.getUserIdByToken();
            boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            // Nếu người dùng không phải ADMIN và không phải chủ tài khoản, ngăn chặn cập nhật
            if (!isAdmin && !existingAccount.getId().equals(Long.valueOf(currentUserId))) {
                throw new AccessDeniedException("User không được phép cập nhật tài khoản của người khác.");
            }

            // Cập nhật thông tin tài khoản
            existingAccount.setUsername(account.getUsername());

            // Nếu người dùng gửi mật khẩu mới, mã hóa mật khẩu trước khi cập nhật
            if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                existingAccount.setPassword(passwordEncoder.encode(account.getPassword()));
            }

            // Chỉ ADMIN mới có thể thay đổi vai trò (role) của tài khoản
            if (isAdmin) {
                existingAccount.setRole(account.getRole());
            }

            // Lưu thông tin tài khoản đã được cập nhật
            existingAccount = accountRepository.save(existingAccount);
        }

        // Trả về đối tượng DTO, không bao gồm mật khẩu
        return accountMapper.toResponseDto(existingAccount);
    }


    // Lấy danh sách tất cả tài khoản (Get All Accounts)
    @Override
    public List<AccountInfoDto> getAllAccounts(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Sort.Direction direction = sortType.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return accountRepository.findAll(pageable)
                .stream()
                .map(accountMapper::convertToAccountInfoDto) // Chuyển đổi Account sang AccountInfoDto
                .collect(Collectors.toList());
    }

    // Lấy chi tiết tài khoản theo ID (Get Account By ID)
    @Override
    public AccountInfoDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        return accountMapper.toResponseDto(account);
    }


    // Xóa tài khoản (Delete Account)
    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        accountRepository.delete(account);
    }
}
