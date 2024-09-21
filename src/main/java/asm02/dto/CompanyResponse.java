package asm02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private String email;
    private String phone;
    private String logo;
    private Long recruiter_id;
}
