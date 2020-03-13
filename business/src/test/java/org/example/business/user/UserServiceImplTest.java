package org.example.business.user;

import org.assertj.core.util.Lists;
import org.example.business.BaseJunit;
import org.example.data.model.department.Department;
import org.example.data.model.role.Role;
import org.example.data.model.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class UserServiceImplTest extends BaseJunit {
    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setId(1L);
        user.setName("杨福东");
        user.setLoginAccount("yangfudong");
        user.setLoginPassword("123456");
        Role role = new Role();
        role.setId(3L);
        user.setRoleList(Lists.newArrayList(role));
        Department department = new Department();
        department.setId(1L);
        user.setDepartmentList(Lists.newArrayList(department));
        BaseJunit.toPrintJson(userService.save(user));
    }

    @Test
    public void findAll() {
        User user = new User();
        user.setName("杨");
        BaseJunit.toPrintJson(userService.findAll(user, PageRequest.of(0, 10)));
    }

    @Test
    public void findById() {
        BaseJunit.toPrintJson(userService.findById(1L));
    }
}
