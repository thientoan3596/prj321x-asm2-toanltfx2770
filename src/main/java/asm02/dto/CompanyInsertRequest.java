package asm02.dto;

import asm02.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyInsertRequest {
    @NotBlank(message = "{label.company-name} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{label.company-name} {validation.size}")
    private String name;
    @NotBlank(message = "{label.address} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{label.address} {validation.size}")
    private String address;
    @Pattern(regexp = "(((\\+|00|0)84)|0)([35789])+([0-9]{8})\\b|^$",
            message = "{validation.invalid.phone}")
    private String phone;
    @NotBlank(message = "{label.email} {validation.not-null}")
    @Email(message = "{label.email} {validation.invalid}")
    private String email;
    @Size(max = 255,message = "{label.description} {validation.size.max-only")
    private String description;
    @NotBlank(message = "{label.recruiter} {validation.not-null}")
    private Long recruiter_id;
    public Company toEntity() {
        return Company.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .email(email)
                .description(description)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}

