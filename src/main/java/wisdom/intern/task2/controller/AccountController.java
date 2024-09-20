package wisdom.intern.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import wisdom.intern.task2.dto.AccountInfoDto;
import wisdom.intern.task2.entity.Account;
import wisdom.intern.task2.service.AccountService;
import wisdom.intern.task2.mapper.common.impl.MapperImpl;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MapperImpl mapper;

    // Tạo tài khoản với điều kiện đặc biệt:
    // - Chỉ admin mới có thể tạo tài khoản với vai trò admin
    @PostMapping
    public ResponseEntity<AccountInfoDto> createOrUpdateAccount(@RequestParam(required = false) Long id,
                                                                @RequestBody Account account) {
        // Nếu là cập nhật hoặc tạo tài khoản mới
        // Kiểm tra nếu role là ADMIN thì yêu cầu phải có quyền ADMIN
        if (account.getRole().equals("ADMIN")) {
            // Kiểm tra quyền ADMIN
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

            if (!isAdmin) {
                throw new AccessDeniedException("Bạn không có quyền tạo tài khoản admin.");
            }
        }

        // Gọi service để lưu hoặc cập nhật tài khoản
        return ResponseEntity.ok(accountService.saveOrUpdateAccount(id, account));
    }


    // Cho phép USER hoặc ADMIN xem danh sách tài khoản
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountInfoDto>> getAllAccounts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortType) {

        List<AccountInfoDto> accounts = accountService.getAllAccounts(pageNo, pageSize, sortBy, sortType);
        return ResponseEntity.ok(accounts);
    }

    // Cho phép USER hoặc ADMIN lấy thông tin tài khoản theo ID
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("byId")
    public ResponseEntity<AccountInfoDto> getAccountById(@RequestParam Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // Chỉ cho phép ADMIN xóa tài khoản
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteAccount(@RequestParam Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
