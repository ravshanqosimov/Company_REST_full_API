package uz.pdp.task1.payload;


import lombok.Data;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Department;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
@NotNull(message = "NAME bo`sh bo`lmasligi kerak")
    private String name;

    @NotNull(message = "phoneNumber bo`sh bo`lmasligi kerak")
    private String phoneNumber;

    @NotNull(message = "street bo`sh bo`lmasligi kerak")
    private String street;

    @NotNull(message = "homeNumber bo`sh bo`lmasligi kerak")
    private String homeNumber;


    private Integer departmentId;


}
