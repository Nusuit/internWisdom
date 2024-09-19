package wisdom.intern.task2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
@Data
public class AccountInfoDto {
    private String username;
    private String role;
}
