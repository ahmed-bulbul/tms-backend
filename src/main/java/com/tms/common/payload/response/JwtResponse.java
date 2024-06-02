package com.tms.common.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tms.common.models.Organization;
import com.tms.configuaration.payload.response.ModuleViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private Set<Integer> roles;
  private Map<Integer,String> roleMap;

  private List<ModuleViewModel> defaultAccessRight;
  private Map<String, Integer> userAccessPermissions;
  private List<FeatureRoleViewModel> featureRoleViewModelList;
  private OrganizationResponseDto organization;

  public JwtResponse(String accessToken, Long id, String username, String email, Set<Integer> roles,
                     List<ModuleViewModel> defaultAccessRight,Map<String, Integer> userAccessPermissions,
                     List<FeatureRoleViewModel> featureRoleViewModelList) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.defaultAccessRight = defaultAccessRight;
    this.userAccessPermissions = userAccessPermissions;
    this.featureRoleViewModelList = featureRoleViewModelList;
  }

  public JwtResponse(String accessToken, Long id, String username, String email, Map<Integer,String> roleMap) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roleMap = roleMap;
  }

  public JwtResponse(String jwt, Long id, String username, String email, OrganizationResponseDto organization, Map<Integer, String> roleMap) {
    this.token = jwt;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roleMap = roleMap;
    this.organization = organization;
  }
}
