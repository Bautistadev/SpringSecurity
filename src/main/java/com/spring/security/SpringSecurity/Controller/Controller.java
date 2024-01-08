package com.spring.security.SpringSecurity.Controller;

import com.spring.security.SpringSecurity.Entity.User;
import com.spring.security.SpringSecurity.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class Controller {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserService userService;

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


    @GetMapping("/hole")
    public ResponseEntity<?> hola(){
        return ResponseEntity.ok("hola mundo");
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user){
        return ResponseEntity.ok().body(this.userService.save(user));
    }

}
