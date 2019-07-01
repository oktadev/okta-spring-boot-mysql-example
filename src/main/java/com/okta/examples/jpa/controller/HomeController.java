package com.okta.examples.jpa.controller;

import com.okta.examples.jpa.model.UserEvent;
import com.okta.examples.jpa.repository.UserEventRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class HomeController {

    private final UserEventRepository userEventRepository;

    public HomeController(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }
    
    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) {
        String token = user.getIdToken().getTokenValue();
        
        //check if first time with this token, if so record new auth event
        List<UserEvent> userEventsForToken = userEventRepository.findByToken(token);
        UserEvent event;
        if (userEventsForToken.size() == 0) {
            //add new event
            event = new UserEvent(
                user.getSubject(), user.getClaims().get("name").toString(),
                token, Date.from(user.getAuthenticatedAt()), Date.from(user.getIssuedAt())
            );
        } else {
            //edit existing event
            event = userEventsForToken.get(0); //there will only ever be one because we update it if it exists already
            event.setLastViewedAt(Date.from(Instant.now()));
        }
        userEventRepository.save(event);
        
        List<UserEvent> eventsToShow;
        boolean isAdmin = user.getUserInfo().getClaimAsStringList("groups").contains("Admin");
        if (isAdmin) {
            eventsToShow = userEventRepository.findAll();
        } else {
            eventsToShow = userEventRepository.findByUserId(user.getSubject());
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
