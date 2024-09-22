package asm02.entity;

import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.LazyInitializationException;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "company")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@EqualsAndHashCode(exclude = {"recruiter"})
@ToString(exclude = {"recruiter"})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String description;
    private String email;
    private String phone;
    private String logo;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "deleted_at")
    @JsonIgnore
    private Timestamp deletedAt;
    @JoinColumn(name = "recruiter_id")
    @OneToOne
    private User recruiter;

    /**
     * @throws IllegalStateException if being called outside Transaction
     */
    public CompanyResponse toResponse() {
        try {
            return CompanyResponse.builder()
                    .id(this.id)
                    .name(this.name)
                    .address(this.address)
                    .description(this.description)
                    .email(this.email)
                    .phone(this.phone)
                    .logo(this.logo)
                    .recruiter_id(recruiter.getId())
                    .build();
        } catch (Exception e) {
            if (e instanceof LazyInitializationException)
                throw new IllegalStateException("Lazily loading outside Tx!");
            throw e;
        }
    }

    /**
     * @throws IllegalStateException The entity and request having different id
     */
    public void merge(CompanyRequest request) {
        if (!request.getId().equals(id)) throw new IllegalStateException("Merging different entities");
        if (request.getName() != null) name = request.getName();
        if (request.getPhone() != null) phone = request.getPhone();
        if (request.getAddress() != null) address = request.getAddress();
        if (request.getEmail() != null) email = request.getEmail();
        if (request.getDescription() != null) description = request.getDescription();
    }
}
