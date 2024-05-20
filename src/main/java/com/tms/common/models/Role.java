package com.tms.common.models;

import com.tms.common.constant.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

  private Boolean isDeleted = Boolean.FALSE;

//  @JsonIgnore
//  @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
//          orphanRemoval = true, fetch = FetchType.LAZY)
//  private List<User> userSet;

  public Role() {

  }

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
          name = "role_accesses",
          joinColumns = {@JoinColumn(name = "role_id")},
          inverseJoinColumns = {@JoinColumn(name = "access_right_id")}
  )
  private Set<AccessRight> accessRightSet;

  public void addAccessRight(AccessRight accessRight) {
    if (accessRightSet == null) {
      accessRightSet = new HashSet<>();
    }
    if (!accessRightSet.contains(accessRight)) {
      accessRightSet.add(accessRight);
      accessRight.addRole(this);
    }
  }


//  public void addUser(User user) {
//    if (userSet == null) {
//      userSet = new ArrayList<>();
//    }
//    if (!userSet.contains(user)) {
//      userSet.add(user);
//      user.getRoles().add(this);  // Add the current role to the user's roles set
//    }
//  }


  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Role)) return false;
    return this.getId() != 0 && this.getId().equals(((Role) object).getId());
  }

  @Override
  public int hashCode() {
    if (Objects.isNull(this.getId())) {
      return this.getClass().hashCode();
    }
    return this.getId().hashCode();
  }

  public Role(ERole name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
}