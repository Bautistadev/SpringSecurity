package com.spring.security.SpringSecurity.Service;

import com.spring.security.SpringSecurity.Entity.Rol;
import com.spring.security.SpringSecurity.Entity.User;
import com.spring.security.SpringSecurity.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {



    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
    * INITIALIZATION ALL MOCKITO SENTENCES BEFORE THE FUNCTIONS TESTS
    * */
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUser(){

        User user = User.builder()
                .username("bautista")
                .password("master")
                .rol(Rol.USER)
                .build();



        when(this.userService.save(user)).thenReturn(user);
        assertInstanceOf(User.class,userService.save(user));
        assertNotNull(userService.save(user));
        assertEquals(userService.save(user).getUsername(),"bautista");
        assertEquals(userService.save(user).getPassword(),"master");
        assertNotEquals(userService.save(user).getRol(),Rol.ADMIN);
    }

    @Test
    public void findAllUser(){
        List<User> userList = this.userService.findAll();

        /** RETURN TRUE ANS SUCCESS TEST, IF THE USER LIST THAT
         *  userService RETURN IS A INSTANCE OF LIST CLASS
         * */
        assertTrue(userList instanceof List<?>);

        /** RETURN TRUE ANS SUCCESS TEST, IF THE USER LIST THAT
         *  userService RETURN IS A CONCRETE INSTANCE OF USER LIST CLASS
         * */
        assertTrue(userList instanceof List<User>);

    }

    @Test
    public void findUserByUserName(){
        User user = User.builder()
                .username("bautista")
                .password("master")
                .rol(Rol.USER)
                .build();
        this.userService.save(user);
        when(this.userService.findUserByUserName(user.getUsername())).thenReturn(user);
        assertTrue(this.userService.findUserByUserName(user.getUsername()) instanceof  User);

    }
}