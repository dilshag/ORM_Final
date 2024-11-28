package lk.ijse.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgrammeDTO {
@Id
    private String programmeId;
    private String programmeName;
    private String duration;
    private double fee;
}
