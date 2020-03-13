package org.example.business.user;

import org.example.data.model.user.User;
import org.example.data.service.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDataService userDataService;

    public User save(User user) {
        return userDataService.save(user);
    }

    @Override
    public Page<User> findAll(User user, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        return userDataService.findAll(Example.of(user,exampleMatcher), pageable);
    }

    @Override
    public List<User> findAll() {
        return userDataService.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDataService.findById(id).orElseGet(User::new);
    }
}
