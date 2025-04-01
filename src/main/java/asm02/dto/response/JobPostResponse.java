package asm02.dto.response;

import asm02.entity.eJobType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class JobPostResponse {
     Long id;
     String title;
     String description;
     String yoe;
     Integer vacancies;
     String address;
     Date deadline;
     String salaryRange;
     Long category;
     String company;
     Long companyId;
     eJobType type;
     Date createdAt;
     Date deletedAt;
}
