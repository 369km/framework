package org.example.business.role;

import org.example.data.model.role.Role;
import org.example.data.service.role.RoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDataService roleDataService;
    @Override
    public Role save(Role role) {
        return roleDataService.save(role);
    }

    @Override
    public Role findById(Long id) {
        return roleDataService.findById(id).orElseGet(Role::new);
    }

    @Override
    public Page<Role> findAll(Role role, Pageable pageable) {
        return roleDataService.findAll(Example.of(role),pageable);
    }

    @Override
    public List<Role> findAll() {
        return roleDataService.findAll();
    }
}
