package lk.ijse.tdm;

import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTM {
    private String studentID;
    private String studentName;
    private String address;
    private String phoneNumber;
    private String email;
    private User useId;
}
