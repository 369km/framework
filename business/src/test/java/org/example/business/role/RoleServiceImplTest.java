package org.example.business.role;

import org.assertj.core.util.Lists;
import org.example.business.BaseJunit;
import org.example.data.model.menu.Menu;
import org.example.data.model.role.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImplTest extends BaseJunit {
    @Autowired
    private RoleService roleService;

    @Test
    public void save() {
        Role role = new Role();
        role.setId(3L);
        role.setName("系统管理员");
        Menu menu = new Menu();
        menu.setId(1L);
        role.setMenuList(Lists.newArrayList(menu));
        BaseJunit.toPrintJson(roleService.save(role));
    }

    @Test
    public void findById() {
        BaseJunit.toPrintJson(roleService.findById(3L));
    }

}