package com.spring.security.SpringSecurity.Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void createUserTest(){
        //User date
        Integer id = 1;
        String username = "bautista";
        String password = "123654";

        //USER CREATED
        User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .rol(Rol.ADMIN)
                .build();

        //THE USER OBJECT IS NOT NULL
        assertNotNull(user);

        assertEquals(id,user.getId());
        assertEquals(username,user.getUsername());
        assertEquals(password,user.getPassword());

    }

    @Test
    public void instanceofTest(){
        Integer id = 1;
        String username = "bautista";
        String password = "123654";

        //USER CREATED
        User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .rol(Rol.ADMIN)
                .build();

        //USER IS A INSTANCE OF USER CLASS
        assertInstanceOf(User.class,user);
        assertInstanceOf(String.class, user.toString());
    }

    @Test
    public void userEqualsTest(){

        User user1 = User.builder()
                .id(1)
                .username("Bautista")
                .password("1234")
                .rol(Rol.USER)
                .build();

        User user2 = User.builder()
                .id(1)
                .username("Bautista")
                .password("1234")
                .rol(Rol.USER)
                .build();


        assertEquals(user1,user2);

    }

}