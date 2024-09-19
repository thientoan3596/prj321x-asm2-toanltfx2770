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

    @NotBlank(message = "Tên không được bỏ trống")
    @Size(min = 5, max = 255, message = "Vui lòng nhập tên thật")
    private String fullName;
    private String address="";
    private String description="";
    @NotBlank(message = "Email không được bỏ trống")
    @Email(message = "Email không hợp lệ")
    private String email ;
    private eUserRole role=eUserRole.JOB_SEEKER;
    @NotBlank(message = "Mật khẩu không được bỏ trống")
    @Pattern(regexp = ".{3,255}", message = "Mật khẩu phải từ 3-255 ký tự")
    private String password;
    @NotBlank(message = "Mật khẩu không được bỏ trống")
    @Pattern(regexp = ".{3,255}", message = "Mật khẩu phải từ 3-255 ký tự")
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

