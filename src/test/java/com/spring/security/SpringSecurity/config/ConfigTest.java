package com.spring.security.SpringSecurity.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SessionRegistry sessionRegistry;
    @Test
    public void LoginValidTest() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection()); /**STATUS 302 en HTTP indica una redirección temporal**/

    }

    @Test
    public void RegistrySession(){
        /**SIMULATE A USER ACTIVE SESSION*/
        Mockito.when(this.sessionRegistry.getAllPrincipals())
                .thenReturn(List.of(new Object()));

    }

}