package asm02.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CVResponse {
    private Long id;
    private String fileName;
    private String fileNameOnServer;
    private Boolean isDefault;
    private Long userId;
}
