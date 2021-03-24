package uz.pdp.task1.payload;

import lombok.Data;
import uz.pdp.task1.entity.Company;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "name bo`sh bo`lmasligi kerak")
    private String name;

    private Integer companyId;

}
