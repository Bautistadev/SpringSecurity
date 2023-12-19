package com.spring.security.SpringSecurity.Repository;

import com.spring.security.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {

}
