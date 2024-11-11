package asm02.entity;

import asm02.dto.request.update.CompanyRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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
    @Column(name = "company_name")
    private String companyName;
    private String address;
    @Lob
    private String description;
    private String email;
    private String phone;
    private String logo;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
    @Column(name = "deleted_at")
    @JsonIgnore
    private Timestamp deletedAt;
    @JoinColumn(name = "recruiter_id")
    @OneToOne
    private User recruiter;

    @JoinColumn(name = "company_id",insertable = false,updatable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<JobPost> jobPostList;
    public static Company defaultCompany(User user) {
        if(user ==null || user.getId() == null) throw new IllegalStateException("User is not persistent! Method defaultCompany() accept only persistent user!");
        return Company.builder()
                .companyName("Cập nhật tên công ty!")
                .email(user.getEmail()!=null?user.getEmail():("user"+user.getId()+"company"+ UUID.randomUUID() +"@email"))
                .address(user.getAddress()!=null?user.getAddress():("NO ADDRESS"))
                .phone(user.getPhone()!=null?user.getPhone():("NO PHONE"))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .recruiter(user)
                .build();
    }

    /**
     * @throws IllegalStateException The entity and request having different id
     */
    public void merge(CompanyRequest request) {
        if (!request.getId().equals(id)) throw new IllegalStateException("Merging different entities");
        if (request.getCompanyName() != null) companyName = request.getCompanyName();
        if (request.getPhone() != null) phone = request.getPhone();
        if (request.getAddress() != null) address = request.getAddress();
        if (request.getEmail() != null) email = request.getEmail();
        if (request.getDescription() != null) description = request.getDescription();
    }
}
