package asm02.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "company")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@EqualsAndHashCode(exclude = { "recruiter"})
@ToString(exclude = { "recruiter"})
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
}
