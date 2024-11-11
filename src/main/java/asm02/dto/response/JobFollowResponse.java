package asm02.dto.response;

import asm02.entity.eJobType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobFollowResponse extends FollowResponse{
    Long companyId;
    String companyName;
    String companyLogo;
    Long jobId;
    String jobTitle;
    eJobType jobType;
    Integer jobVacancies;
    String jobSalaryRange;
    String jobAddress;
    Date jobDeadline;
    ApplicationResponse application;
}
