package com.spring.security.SpringSecurity.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.SpringSecurity.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;



    /**TEST USER PERFORM*/
    @Test
    public void TestEndPointSessionRegistry() throws Exception {

        /**SIMULATE THE INIT SESSION*/
        MvcResult loginResult = mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(authenticated())
                .andReturn();

        /**GIVE THE SESSION DATA*/
        HttpSession session = loginResult.getRequest().getSession();


        /**TEST, IF DATA REQUEST ARE VALID*/
        /**
         * @PARAMS: -URL
         *          -SESSION
         *          -TYPE OF RESPONSE
         *          -STATUS
         * */
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/DetailsSession").session((MockHttpSession) session)
                        .contentType(MediaType.APPLICATION_JSON)) /**TEST OF RESPONSE TYPE DATA*/
                .andExpect(status().isOk());
    }

}