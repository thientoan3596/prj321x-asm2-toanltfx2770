package asm02.dto.request.insert;

import asm02.dto.request.base.CompanyBaseRequest;
import asm02.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyInsertRequest extends CompanyBaseRequest {
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

