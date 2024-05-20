package com.tms.common.models;

import com.tms.common.generics.AbstractDomainBasedEntity;
import com.tms.common.generics.IDto;
import com.tms.configuaration.entity.ConfigModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization extends AbstractDomainBasedEntity implements IDto {

    @OrderBy
    @Column(name = "org_ref", nullable = false)
    private String reference;

    @Column(name = "org_code", nullable = false)
    private String orgCode;

    @Column(name = "org_type", nullable = false)
    private OrganizationTypeEnum organizationType;


    @Column(name = "org_name", nullable = false)
    private String name;


    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_number")
    private String contactNumber;


    @Email(message = "{error.email.invalid}")
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "active_status")
    private boolean activeStatus = true;

    @Column(name = "IS_ADMIN_CONSOLE")
    private boolean adminConsole;

    @Column(name = "ORG_CATEGORY")
    private String orgCategory;

    @Column(name = "licence_key")
    private String licencekey;

    @Column(name = "secret_key")
    private String secretkey;


    public static Organization withId(long id) {
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }





}
