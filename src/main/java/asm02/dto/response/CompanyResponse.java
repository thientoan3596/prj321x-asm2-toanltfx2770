package asm02.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CompanyResponse {
     Long id;
     String companyName;
     String address;
     String description;
     String email;
     String phone;
     String logo;
     Long recruiter_id;
}
