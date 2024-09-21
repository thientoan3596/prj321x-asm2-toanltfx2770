package asm02.entity;

import asm02.dto.UserRequest;
import asm02.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.LazyInitializationException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    private String address;
    private String description;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;
    private String avatar;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private eUserRole role;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<CV> cvList;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "deleted_at")
    @JsonIgnore
    private Timestamp deletedAt;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    /**
     * @throws IllegalStateException if being called outside Transaction
     */
    public UserResponse toResponse() {
        try {
            return UserResponse.builder()
                    .id(id)
                    .fullName(fullName)
                    .description(description)
                    .address(address)
                    .email(email)
                    .phone(phone)
                    .role(role)
                    .createdAt(createdAt)
                    .avatar(avatar)
                    .cvList(cvList.stream().map(CV::toResponse).collect(Collectors.toSet()))
                    .build();
        } catch (Exception e) {
            if (e instanceof LazyInitializationException)
                throw new IllegalStateException("Lazily loading outside Tx!");
            throw e;
        }
    }
    public void merge(UserRequest request){
        if(request.getId()!= id) throw new IllegalStateException("Merging different entities");
        if(request.getAddress()!= null) address=request.getAddress();
        if(request.getPhone()!= null) phone=request.getPhone();
        if(request.getEmail()!= null)  email=request.getEmail();
        if(request.getFullName()!=null) fullName=request.getFullName();
        if(request.getDescription()!= null) description=request.getDescription();

    }

}
