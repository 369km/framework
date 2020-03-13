package org.example.business.menu;

import org.example.data.model.menu.Menu;
import org.example.data.service.menu.MenuDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDataService menuDataService;

    @Override
    public Menu save(Menu menu) {
        return menuDataService.save(menu);
    }

    @Override
    public Menu findById(Long id) {
        return menuDataService.findById(id).orElseGet(Menu::new);
    }

    @Override
    public Page<Menu> findAll(Menu menu, Pageable pageable) {
        return menuDataService.findAll(Example.of(menu),pageable);
    }

    @Override
    public List<Menu> findAll() {
        return menuDataService.findAll();
    }
}
