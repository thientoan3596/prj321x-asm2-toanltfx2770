package asm02.dto.request.update;

import asm02.dto.request.base.CompanyBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
public class CompanyRequest extends CompanyBaseRequest {
    @NotNull(message = "id {validation.not-null}")
    private Long id;
}
