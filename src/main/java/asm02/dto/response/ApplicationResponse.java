package asm02.dto.response;

import asm02.entity.eApplicationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ApplicationResponse {
    Long id;
    Long userId;
    String user;
    String userAvatar;
    String userEmail;
    Long cvId;
    String cv;
    Long jobId;
    String text;
    eApplicationStatus status;
    Date createdAt;
}
