package asm02.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class FollowResponse {
    Long id;
    Long userId;
    Boolean active;
}
