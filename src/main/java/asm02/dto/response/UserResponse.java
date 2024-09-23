package asm02.dto.response;

import asm02.entity.eUserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private long id;
    private String fullName;
    private String address;
    private String description;
    private String email;
    private String phone;
    private eUserRole role;
    private Set<CVResponse> cvList;
    private String avatar;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;
    private CompanyResponse company;

    public CVResponse getDefaultCV() {
        if (cvList.size() != 0)
            return cvList.stream().filter(CVResponse::getIsDefault).findFirst().orElse(cvList.iterator().next());
        return null;
    }
}
