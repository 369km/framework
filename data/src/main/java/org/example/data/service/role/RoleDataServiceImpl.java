package org.example.data.service.role;

import org.example.data.model.role.Role;
import org.example.data.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleDataServiceImpl implements RoleDataService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> saveAll(List<Role> list) {
        return roleRepository.saveAll(list);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> findAll(Example<Role> example, Pageable pageable) {
        return roleRepository.findAll(example, pageable);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
