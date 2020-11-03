package kbx;

import kbx.controller.UserProfileController;
import kbx.repository.UserProfileRepository;
import kbx.service.UserProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KbxAssignmentApplicationTests {

    @Autowired
    private UserProfileController userProfileController;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Test
    void testContextLoads() {
        Assertions.assertNotNull(userProfileController);
        Assertions.assertNotNull(userProfileService);
        Assertions.assertNotNull(userProfileRepository);
    }
}
