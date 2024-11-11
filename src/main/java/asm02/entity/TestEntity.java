package asm02.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestEntity {
    @Size(min = 5, max = 255, message = "{label.name} {validation.size}")
   String name;
}
