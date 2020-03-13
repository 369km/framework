package org.example.data.service.menu;

import org.example.data.model.menu.Menu;
import org.example.data.repository.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuDataServiceImpl implements MenuDataService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> saveAll(List<Menu> list) {
        return menuRepository.saveAll(list);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Page<Menu> findAll(Example<Menu> example, Pageable pageable) {
        return menuRepository.findAll(example, pageable);
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }
}
