package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.repos.UserRepo;
import com.holydev.fastcase.utilities.primitives.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("There is no user with such username!");
        });
    }

    public User getUserById(Long user_id) {
        return userRepo.findFriendlistById(user_id).orElseThrow();
    }

    public User findAllInfoByUsername(String username) {
        return userRepo.findFirstByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("There is no user with such username!");
        });
    }

    public void save(User user) {
        try {
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User register(RegistrationRequest request) {
        var new_user = new User(request);
        userRepo.save(new_user);
        return null;
    }

    public void createDefault() {
        var coded = new BCryptPasswordEncoder().encode("admin");
        var reg_req = new RegistrationRequest("admin", coded, "Лукаш Павел Андреевич", "1789191@sfedu.ru", "+790498716574");
        var admin = new User(reg_req);
        admin.setRoles(Collections.singleton(roleService.getRoleByName("BOSS")));
        save(admin);
    }

    public User getFriendListById(Long id) {
        return userRepo.findFriendlistById(id).orElseThrow();
    }
}
