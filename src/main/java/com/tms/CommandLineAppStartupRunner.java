package com.tms;

import com.tms.common.constant.ERole;
import com.tms.common.models.Role;
import com.tms.common.models.User;
import com.tms.common.repository.RoleRepository;
import com.tms.common.repository.UserRepository;
import com.tms.configuaration.entity.WorkFlowAction;
import com.tms.configuaration.repository.WorkFlowActionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;
    private final WorkFlowActionRepository workFlowActionRepository;

    private final PasswordEncoder encoder;



    public CommandLineAppStartupRunner(RoleRepository roleRepository, UserRepository userRepository, WorkFlowActionRepository workFlowActionRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.workFlowActionRepository = workFlowActionRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        createRole(ERole.ROLE_SUPER_ADMIN,1);
        createRole(ERole.ROLE_USER,2);
        createUser("bulbul","bulbul@gmail.com","123456",ERole.ROLE_USER);
        createUser("admin","admin@gmail.com","1234567",ERole.ROLE_SUPER_ADMIN);
        createWorkFlowActions("CREATE",-32768,true,"Created By",true);
        createWorkFlowActions("REVIEW",1,true,"Reviewed By",true);
        createWorkFlowActions("APPROVED",32768,false,"Created By",true);

    }



    private void createUser(String username, String email, String password,ERole roleName) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(encoder.encode(password));
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(roleName).orElse(null);
            roles.add(userRole);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    private void createRole(ERole roleName,Integer id) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isEmpty()){
            Role entity = new Role();
            entity.setId(id);
            entity.setName(roleName);
            roleRepository.save(entity);
        }
    }

    private void createWorkFlowActions(String name, Integer orderNo, boolean isShow, String label, boolean isActive) {
        Optional<WorkFlowAction> workFlowAction = workFlowActionRepository.findByName(name);
        if(workFlowAction.isEmpty()){
            WorkFlowAction entity = new WorkFlowAction();
            entity.setName(name);
            entity.setOrderNumber(orderNo);
            entity.setIsShow(isShow);
            entity.setLabel(label);
            entity.setIsActive(isActive);
            workFlowActionRepository.save(entity);
        }
    }


}
