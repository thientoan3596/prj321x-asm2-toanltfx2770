package asm02.dto;


import asm02.entity.User;
import asm02.entity.eUserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterRequest {

    @NotBlank(message = "{label.fullname} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{validation.size.fullname}")
    private String fullName;
    private String address="";
    private String description="";
    @NotBlank(message = "{label.email} {validation.not-null}")
    @Email(message = "{label.email} {validation.invalid}")
    private String email ;
    private eUserRole role=eUserRole.JOB_SEEKER;
    @NotBlank(message = "{label.password} {validation.not-blank}")
    @Pattern(regexp = ".{3,255}", message = "{label.password} {validation.size}")
    private String password;
    @NotBlank(message = "{label.password} {validation.not-blank}")
    @Pattern(regexp = ".{3,255}", message = "{label.password} {validation.size}")
    private String passwordConfirm;

    /**
     * NB! NO password in the newly created entity
     */
    public User toEntity(){
        return User.builder()
                .fullName(fullName)
                .address(address)
                .description(description)
                .email(email)
                .role(role)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

}

