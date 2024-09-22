package asm02.dto.request.insert;

import asm02.dto.request.base.CompanyBaseRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyInsertRequest extends CompanyBaseRequest {
    @NotBlank(message = "{label.recruiter} {validation.not-null}")
    private Long recruiter_id;
}

