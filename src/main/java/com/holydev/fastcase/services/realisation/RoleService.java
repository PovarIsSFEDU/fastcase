package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.repos.RoleRepo;
import com.holydev.fastcase.services.interfaces.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {

    private final RoleRepo roleRepo;

    @Override
    public void init() {
        var boss_role = new Role(1L, "BOSS");
        var tech_role = new Role(2L, "TECHNICIAN");
        var user_role = new Role(3L, "USER");
        roleRepo.save(boss_role);
        roleRepo.save(tech_role);
        roleRepo.save(user_role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findByName(name).orElseThrow();
    }

    @Override
    public void addRole(Role role) {
        roleRepo.save(role);
    }

    @Override
    public void changeRole(Long id, Role new_role) {
        new_role.setId(id);
        roleRepo.save(new_role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        roleRepo.deleteAll();
    }
}
