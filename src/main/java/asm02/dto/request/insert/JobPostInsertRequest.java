package asm02.dto.request.insert;


import asm02.dto.request.base.JobPostBaseRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class JobPostInsertRequest extends JobPostBaseRequest {
    @NotNull
    private Long companyId;
}
