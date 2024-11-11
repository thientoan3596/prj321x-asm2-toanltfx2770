package asm02.dto.request.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@FileRequiredOnCondition(source = "cvId", target = "file", expression = "== -1",message = "{message.error.empty-file}")
public class ApplicationBaseRequest {
    @Size(min= 5,max= 255,message = "{label.self-introduction} {validation.size}")
    String text;
    @NotNull(message = "userId {validation.not-blank}")
    Long userId;
    @NotNull(message = "jobId {validation.not-blank}")
    Long jobId;
    @NotNull(message = "jobId {validation.not-blank}")
    Long cvId;
    /* This field is SOLELY used as a metaphors for validation only */
    Boolean file;
}

