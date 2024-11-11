package asm02.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
public abstract class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    eFollowedEntity type;
    @Column(name="active",nullable = true, insertable = false,updatable = true)
    Boolean active = Boolean.TRUE;
}
