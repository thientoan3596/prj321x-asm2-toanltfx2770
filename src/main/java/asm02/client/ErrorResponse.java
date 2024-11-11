package asm02.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String name;
    private String root;
    private String defaultMessage;
    private String rejectedValue;
    private String field;
}
