package kbx.controller;

import kbx.model.ProfileView;
import kbx.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/api/profile/{userId}")
    public List<ProfileView> listProfileViews(@PathVariable Long userId) {
        return userProfileService.listProfileViews(userId);
    }

    @PostMapping("/api/profile/{userId}")
    public void viewProfile(@PathVariable Long userId, @RequestParam Long viewerUserId) {
        userProfileService.recordProfileView(userId, new ProfileView(viewerUserId, new Date()));
    }

}
