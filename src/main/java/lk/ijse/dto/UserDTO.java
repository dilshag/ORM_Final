package lk.ijse.dto;

import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private String userId;
    private String password;
    private String userName;
    private String role;
}
