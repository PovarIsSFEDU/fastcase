package com.holydev.fastcase.services.interfaces;

import com.holydev.fastcase.entities.Role;

public interface RoleServiceInterface {
    void init();

    Role getRoleByName(String name);

    void addRole(Role role);

    void changeRole(Long id, Role new_role);

    void deleteRole(Long id);

    void deleteAll();
}
