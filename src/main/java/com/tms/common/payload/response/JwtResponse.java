package com.tms.common.payload.response;

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
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private Set<Integer> roles;

  private List<ModuleViewModel> defaultAccessRight;
  private Map<String, Integer> userAccessPermissions;
  private List<FeatureRoleViewModel> featureRoleViewModelList;

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

}
