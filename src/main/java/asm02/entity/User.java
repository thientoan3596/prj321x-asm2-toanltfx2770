package asm02.entity;

import asm02.dto.request.update.UserRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

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
    public void merge(UserRequest request){
        if(!request.getId().equals(id)) throw new IllegalStateException("Merging different entities");
        if(request.getAddress()!= null) address=request.getAddress();
        if(request.getPhone()!= null) phone=request.getPhone();
        if(request.getEmail()!= null)  email=request.getEmail();
        if(request.getFullName()!=null) fullName=request.getFullName();
        if(request.getDescription()!= null) description=request.getDescription();
    }
}
