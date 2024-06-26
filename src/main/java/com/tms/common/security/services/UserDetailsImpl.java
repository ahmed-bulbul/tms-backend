package com.tms.common.security.services;

import java.util.*;
import java.util.stream.Collectors;

import com.tms.common.models.Organization;
import com.tms.common.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tms.common.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private Set<Integer> roleIds;

  private Collection<? extends GrantedAuthority> authorities;

  private Organization organization;

  public UserDetailsImpl(Long id, String username, String email, String password,Set<Integer> roleIds,
      Collection<? extends GrantedAuthority> authorities,Organization organization) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roleIds = roleIds;
    this.authorities = authorities;
    this.organization = organization;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    Set<Integer> roleIds = user.getRoles().stream()
            .map(Role::getId)
            .collect(Collectors.toSet());

    return new UserDetailsImpl(
        user.getId(), 
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(),
        roleIds,
        authorities,
        user.getOrganization());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Set<Integer> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Integer> roleIds) {
    this.roleIds = roleIds;
  }

  public Organization getOrganization() {
    return organization;
  }

  public UserDetailsImpl setOrganization(Organization organization) {
    this.organization = organization;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
