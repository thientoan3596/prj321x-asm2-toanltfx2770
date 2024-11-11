package asm02.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "follows")
public class CompanyFollow extends Follow {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id")
    Company company;
    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    final eFollowedEntity type=eFollowedEntity.COMPANY;
}
