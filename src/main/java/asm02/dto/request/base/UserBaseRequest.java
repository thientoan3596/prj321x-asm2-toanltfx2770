package asm02.dto.request.base;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)

public abstract class UserBaseRequest {
    @NotBlank(message = "{label.fullname} {validation.not-blank}")
    @Size(min = 5, max = 255, message = "{label.fullname} {validation.size}")
    String fullName;
    @Size( max = 255, message = "{label.address} {validation.size.max-only}")
    String address = "";
    @Size(max = 65_535, message = "{label.description} {validation.size.max-only}")
    String description = "";
    @NotBlank(message = "{label.email} {validation.not-blank}")
    @Email(message = "{label.email} {validation.invalid}")
    String email;
    // TODO: 9/20/2024 Update with số điện thoại cố định
    // https://fozg.net/blog/validate-vietnamese-phone-number
    @Pattern(regexp = "(((\\+|00|0)84)|0)([35789])+([0-9]{8})\\b|^$",
            message = "{validation.invalid.phone}")
    String phone;
}
