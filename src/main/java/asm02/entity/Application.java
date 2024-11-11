package asm02.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="application")
@JsonIgnoreProperties(value = {"createdAt"},allowGetters = true)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private eApplicationStatus status;
    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @JoinColumn(name="job_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private JobPost jobPost;
    @JoinColumn(name="cv_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private CV cv;
    String text;
}
