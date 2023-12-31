package com.spring.security.SpringSecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class Controller {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/DetailsSession")
    public ResponseEntity<?> getUsersDetails(){

        String sessionId = null;
        User userObject = null;

        /**RETURN ALL USER OF THE SESSION*/
        List<Object> sessions = this.sessionRegistry.getAllPrincipals();
        for(Object session : sessions){
            if(session instanceof User){
                /**GIVE USER*/
                userObject = (User) session;
            }
            /**RETURN ALL SESSION INFORMATION*/
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(userObject,false);
            for(SessionInformation sessionI : sessionInformations){
               /** GIVE THE ID OF THE USER*/
               sessionId = sessionI.getSessionId();
            }
        }
        Map<String,Object> response = new HashMap<>();
        response.put("response","hello word");
        response.put("sessionId",sessionId);
        response.put("session user",userObject);

        return ResponseEntity.ok(response);
    }
}
