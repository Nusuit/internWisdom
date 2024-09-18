package wisdom.intern.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wisdom.intern.task2.dto.request.AccountRequestDto;
import wisdom.intern.task2.dto.response.AccountResponseDto;
import wisdom.intern.task2.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Chỉ cho phép ADMIN tạo tài khoản mới
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(accountService.createAccount(accountRequestDto));
    }

    // Cho phép USER hoặc ADMIN xem danh sách tài khoản
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Cho phép USER hoặc ADMIN lấy thông tin tài khoản theo ID
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // Cho phép USER cập nhật tài khoản của chính mình, ADMIN có thể cập nhật tài khoản bất kỳ
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountRequestDto));
    }

    // Chỉ cho phép ADMIN xóa tài khoản
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
