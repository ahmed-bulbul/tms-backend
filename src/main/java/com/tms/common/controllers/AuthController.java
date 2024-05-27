package com.tms.common.controllers;

import java.util.*;

import com.tms.configuaration.payload.response.ModuleViewModel;
import com.tms.configuaration.service.IModuleService;
import com.tms.common.payload.request.LoginRequest;
import com.tms.common.payload.request.SignupRequest;
import com.tms.common.payload.response.FeatureRoleViewModel;
import com.tms.common.payload.response.JwtResponse;
import com.tms.common.payload.response.MessageResponse;
import com.tms.common.repository.RoleRepository;
import com.tms.common.repository.UserRepository;
import com.tms.common.security.jwt.JwtUtils;
import com.tms.common.security.services.UserDetailsImpl;
import com.tms.common.service.FeatureRoleService;
import com.tms.common.service.RoleService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.common.constant.ERole;
import com.tms.common.models.Role;
import com.tms.common.models.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;


  @Autowired
  private IModuleService configModuleService;


  @Autowired
  private RoleService roleService;

  @Autowired
  private FeatureRoleService featureRoleService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//    List<String> roles = userDetails.getAuthorities().stream()
//        .map(GrantedAuthority::getAuthority)
//        .toList();

    List<ModuleViewModel> moduleViewModelList = configModuleService.getAllModule();
    Map<String, Integer> userAccessPermissions = new HashMap<>();
    List<FeatureRoleViewModel> featureRoleViewModelList = new ArrayList<>();

    for (Integer roleId : userDetails.getRoleIds()) {
      Map<String, Integer> roleAccessPermission = roleService.getRoleAccessPermission(roleId);
      userAccessPermissions.putAll(roleAccessPermission);
      featureRoleViewModelList.add(featureRoleService.featuresByRoleId(Long.valueOf(roleId)));
    }

    Map<Integer,String> roleMap = new HashMap<>();
    userDetails.getRoleIds().forEach(roleId->{
      Role r = roleService.findById(roleId);
      roleMap.put(roleId,r.getName().name());
    });

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roleMap));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
