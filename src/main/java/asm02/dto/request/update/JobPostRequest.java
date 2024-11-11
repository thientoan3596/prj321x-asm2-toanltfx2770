package asm02.dto.request.update;

import asm02.dto.request.base.JobPostBaseRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRequest  extends JobPostBaseRequest {
    @NotNull
    Long id;
}
