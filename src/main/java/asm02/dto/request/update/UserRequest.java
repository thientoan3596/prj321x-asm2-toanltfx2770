package asm02.dto.request.update;

import asm02.dto.request.base.UserBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest extends UserBaseRequest {
    @NotNull
    private Long id;
}