package org.example.data.service.user;

import org.example.data.model.user.User;
import org.example.data.service.BaseDataService;

import java.util.Optional;

public interface UserDataService extends BaseDataService<User> {
    Optional<User> findByLoginAccountAndLoginPassword(String loginAccount, String loginPassword);
}
