package asm02.dto.request.update;

import asm02.dto.request.base.UserBaseRequest;
import asm02.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest extends UserBaseRequest {
    @NotNull
    private Long id;
    /**
     * NB! NO password in the newly created entity
     */
    public User toEntity(){
        return User.builder()
                .fullName(fullName)
                .address(address)
                .description(description)
                .email(email)
                .build();
    }
}
