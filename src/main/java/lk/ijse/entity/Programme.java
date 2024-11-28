package lk.ijse.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Programme {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String programmeId;
    private String programmeName;
    private String duration;
    private double fee;
}
