package lk.ijse.dto;


import jakarta.persistence.Id;
import lk.ijse.entity.User;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private String studentID;
    private String studentName;
    private String address;
    private String phoneNumber;
    private String email;
    private User userId;
/*
    public StudentDTO(String studentID, String studentName, String address, String phoneNumber, String email, User user){
            this.studentID = studentID;
            this.studentName = studentName;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.userId = user;


    }*/
}
