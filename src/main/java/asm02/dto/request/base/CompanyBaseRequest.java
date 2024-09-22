package asm02.dto.request.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class CompanyBaseRequest {
    @NotBlank(message = "{label.company-name} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{label.company-name} {validation.size}")
    protected String name;
    @NotBlank(message = "{label.address} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{label.address} {validation.size}")
    protected String address;
    @Pattern(regexp = "(((\\+|00|0)84)|0)([35789])+([0-9]{8})\\b|^$",
            message = "{validation.invalid.phone}")
    protected String phone;
    @NotBlank(message = "{label.email} {validation.not-null}")
    @Email(message = "{label.email} {validation.invalid}")
    protected String email;
    @Size(max = 255,message = "{label.description} {validation.size.max-only")
    protected String description;
}
