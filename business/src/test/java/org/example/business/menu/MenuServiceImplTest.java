package org.example.business.menu;

import org.example.business.BaseJunit;
import org.example.data.model.menu.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class MenuServiceImplTest extends BaseJunit {
    @Autowired
    private MenuService menuService;

    @Test
    public void save() {
        Menu menu = new Menu();
        menu.setParenId(0L);
        menu.setName("登录");
        menu.setUrl("/api/framework/login");
        BaseJunit.toPrintJson(menuService.save(menu));
    }
}