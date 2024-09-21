package asm02.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "{label.fullname} {validation.not-blank}")
    @Size(min = 5, max = 255, message = "{validation.size.name}")
    private String fullName;
    private String address;
    private String description;
    @NotBlank(message = "{label.email} {validation.not-blank}")
    @Email(message = "{label.email} {validation.invalid}")
    private String email;
    // TODO: 9/20/2024 Update with số điện thoại cố định
    // https://fozg.net/blog/validate-vietnamese-phone-number
    @Pattern(regexp = "(((\\+|00|0)84)|0)([35789])+([0-9]{8})\\b|^$",
            message = "{validation.invalid.phone}")
    private String phone;
}
