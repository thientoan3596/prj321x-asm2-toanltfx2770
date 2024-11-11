package asm02.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class JobCategoryResponse {
    Long id;
    String name;
}
