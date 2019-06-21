package com.example.joy.BeyondAuthentication.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.joy.beyond.UserEvent;
import com.example.joy.beyond.UserEventRepository;

@RestController
public class HomeController {

    private final UserEventRepository userEventRepository;

    @Autowired
    public HomeController(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }
    
    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) {
        String token = user.getIdToken().getTokenValue();
        
        //check if first time with this token, if so record new auth event
        List<UserEvent> userEventsForToken = userEventRepository.findByToken(token);
        if (userEventsForToken.size()==0) {
            //add new event
            UserEvent newEvent = new UserEvent(user.getSubject(), user.getClaims().get("name").toString(),token,Date.from(user.getAuthenticatedAt()),Date.from(user.getIssuedAt()));
            userEventRepository.save(newEvent);
        }else {
            //edit existing event
            UserEvent event = userEventsForToken.get(0); //there will only ever be one because we update it if it exists already
            event.setLastViewedAt(Date.from(Instant.now()));
        }
        
        Iterable<UserEvent> userEvents = userEventRepository.findAll();
        
        List<UserEvent> eventsToShow = new ArrayList<UserEvent>();
        boolean isAdmin = user.getUserInfo().getClaimAsStringList("groups").contains("Admin");
        for (UserEvent event : userEvents) {
            if (isAdmin || event.getUserId().equals(user.getSubject())) {
                eventsToShow.add(event);
            }
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user.getUserInfo());
        mav.addObject("idToken", user.getIdToken().getTokenValue());
        mav.addObject("userEvents",eventsToShow);
        mav.addObject("isAdmin",isAdmin);
        mav.setViewName("home");
        return mav;
    }
    
    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@AuthenticationPrincipal OidcUser user,@PathVariable("id") long id, Model model) {
        UserEvent userEvent = userEventRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        userEventRepository.delete(userEvent);
        return new RedirectView("/");
    }
}
