package asm02.dto.request.update;

import asm02.dto.request.base.CompanyBaseRequest;
import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyRequest extends CompanyBaseRequest {
    @NotNull(message = "id {validation.not-null}")
    private Long id;
}
