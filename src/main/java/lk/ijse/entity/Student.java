package lk.ijse.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    @Id
    private String studentID;
    private String studentName;
    private String address;
    private String phoneNumber;
    private String email;

    @ManyToOne
    private User user;



}
