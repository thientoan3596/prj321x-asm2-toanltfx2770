package asm02.dto;

import asm02.dto.request.base.CompanyBaseRequest;
import asm02.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyRequest extends CompanyBaseRequest {
    @NotNull(message = "id {validation.not-null}")
    private Long id;
    public Company toEntity() {
        return Company.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .email(email)
                .description(description)
                .build();
    }
}
