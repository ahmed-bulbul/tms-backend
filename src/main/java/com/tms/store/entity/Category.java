package com.tms.store.entity;

import com.tms.common.generics.AbstractDomainBasedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category  extends AbstractDomainBasedEntity {
    private long id;
    private String name;
    private String Description;
}
