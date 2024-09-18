package wisdom.intern.task2.dto.request;

import lombok.Data;

@Data
public class AccountRequestDto {
    private String username;
    private String password;
    private String role;
}
