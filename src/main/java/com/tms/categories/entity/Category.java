package com.tms.categories.entity;


import com.tms.common.generics.AbstractDomainBasedEntity;
import com.tms.common.generics.AbstractEntity;
import com.tms.common.models.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends AbstractDomainBasedEntity {

    private String name;
    private String description;
    @ManyToOne
    private Organization organization;

}
