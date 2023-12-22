package com.spring.security.SpringSecurity.Repository;

import com.spring.security.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user u WHERE u.username = :username",nativeQuery = true)
    public User findUserByUserName(String username);


}
