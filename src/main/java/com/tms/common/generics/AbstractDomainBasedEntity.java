package com.tms.common.generics;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Data
@MappedSuperclass
public abstract class AbstractDomainBasedEntity implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", columnDefinition="boolean default true", nullable = false)
    private Boolean isActive = true;
}
