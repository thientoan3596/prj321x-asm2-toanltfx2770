package asm02.entity;

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
    @Column(name = "email",nullable = false,unique = true)
    private String email ;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private eUserRole role;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "password",nullable = false)
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
                    .role(role)
                    .createdAt(createdAt)
                    .build();
        } catch (Exception e) {
            if (e instanceof LazyInitializationException)
                throw new IllegalStateException("Lazily loading outside Tx!");
            throw e;
        }
    }

}
