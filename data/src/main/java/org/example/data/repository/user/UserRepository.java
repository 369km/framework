package org.example.data.repository.user;

import org.example.data.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLoginAccountAndLoginPassword(String loginAccount,String loginPassword);

}
