package asm02.dto.request.insert;


import asm02.dto.request.base.UserBaseRequest;
import asm02.entity.eUserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterRequest  extends UserBaseRequest {
    private eUserRole role=eUserRole.JOB_SEEKER;
    @NotBlank(message = "{label.password} {validation.not-blank}")
//    /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.* ).{8,16}$/
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.* ).{3,16}$", message = "{label.password} {validation.password.regex}")
    @Pattern(regexp = ".{3,255}", message = "{label.password} {validation.size}")
    private String password;
    @NotBlank(message = "{label.password} {validation.not-blank}")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.* ).{3,16}$", message = "{label.password} {validation.password.regex}")
    @Pattern(regexp = ".{3,255}", message = "{label.password} {validation.size}")
    private String passwordConfirm;
}

