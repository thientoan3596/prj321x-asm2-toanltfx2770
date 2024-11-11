package asm02.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyFollowResponse extends FollowResponse {
    String companyName;
    String companyLogo;
    String companyAddress;
    String companyEmail;
    Long companyId;
}
