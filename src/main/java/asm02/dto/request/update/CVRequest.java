package asm02.dto.request.update;

import asm02.entity.CV;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: 9/22/2024 Consider to remove this! If no use
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CVRequest {
    private String fileName;
    private Boolean isDefault = Boolean.FALSE;
    private Long userId;

    /**
     * NB! NO USER
     */
    public CV toEntity(){
        return CV.builder()
                .fileName(fileName)
                .isDefault(isDefault)
                .build();
    }
}
