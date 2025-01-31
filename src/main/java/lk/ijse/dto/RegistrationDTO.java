package lk.ijse.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationDTO {

    private String regiId;

    private LocalDate enrollmentDate;

    private Double Payment;

    private Double DueAmount;

    private String studentName;

    private String ProgramName;

    private String duration;

}
