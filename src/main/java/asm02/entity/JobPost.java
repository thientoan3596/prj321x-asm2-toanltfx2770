package asm02.entity;

import asm02.dto.request.update.JobPostRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@Table(name = "job_post")
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "description")
    private String description;
    private String yoe;
    private Integer vacancies;
    private String address;
    @Column(name = "application_deadline")
    private Date deadline;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;
    @Column(name = "deleted_at")
    private Date deletedAt;
    @Column(name = "salary_range")
    private String salaryRange;
    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private JobCategory jobCategory;
    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
    @Enumerated(EnumType.STRING)
    private eJobType type;

    /**
     * @throws IllegalStateException The entity and request having different id
     */
    public void merge(JobPostRequest request) {
        if (!request.getId().equals(id)) throw new IllegalStateException("Merging different entities");
        if (request.getTitle() != null) setTitle(request.getTitle());
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty())
            setDescription(request.getDescription());
        if (request.getYoe() != null && !request.getYoe().trim().isEmpty()) setYoe(request.getYoe());
        if (request.getVacancies() != null) setVacancies(request.getVacancies());
        if (request.getAddress() != null && !request.getAddress().trim().isEmpty()) setAddress(request.getAddress());
        if (request.getDeadline() != null) setDeadline(request.getDeadline());
        if (request.getSalaryRange() != null && !request.getSalaryRange().trim().isEmpty())
            setSalaryRange(request.getSalaryRange());
        if (request.getType() != null) setType(request.getType());
    }
}
