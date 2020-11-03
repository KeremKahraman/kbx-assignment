package kbx.controller;

import kbx.model.ProfileView;
import kbx.service.UserProfileService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserProfileControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    void listViewsShouldReturnHttp400WhenPathVariableIsNotNumeric() throws Exception {
        this.mockMvc.perform(get("/api/profile/123A")).andExpect(status().isBadRequest());
    }

    @Test
    void listViewsShouldReturnHttp200() throws Exception {
        Long userId = 123L;
        when(userProfileService.listProfileViews(userId)).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/profile/123")).andExpect(status().isOk());
    }

    @Test
    void listViewsShouldReturnAllProfileViews() throws Exception {
        Long userId = 123L;
        List<ProfileView> result =
                Arrays.asList(new ProfileView(5555L, new Date()),
                        new ProfileView(6666L, new Date())
                );

        when(userProfileService.listProfileViews(userId)).thenReturn(result);
        this.mockMvc.perform(get("/api/profile/123"))
                .andDo(print())
                .andExpect(content().string(containsString("5555")))
                .andExpect(content().string(containsString("6666")));
    }

    @Test
    void listViewsShouldReturnEmptyListIfNoProfileViews() throws Exception {
        Long userId = 123L;
        when(userProfileService.listProfileViews(userId)).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/profile/123"))
                .andDo(print())
                .andExpect(content().string(Matchers.equalTo("[]")));
    }

    @Test
    void recordViewShouldReturnHttp200() throws Exception {
        this.mockMvc.perform(post("/api/profile/123?viewerUserId=5555"))
                .andExpect(status().isOk());
    }

    @Test
    void recordViewShouldReturnHttp400WhenPathVariableIsNotNumeric() throws Exception {
        this.mockMvc.perform(post("/api/profile/123A?viewerUserId=2134")).andExpect(status().isBadRequest());
    }

    @Test
    void recordViewShouldReturnHttp400WhenViewerUserIdIsNotNumeric() throws Exception {
        this.mockMvc.perform(post("/api/profile/123?viewerUserId=2134A")).andExpect(status().isBadRequest());
    }

    @Test
    void recordViewShouldReturnHttp400WhenViewerUserIdIsNotProvided() throws Exception {
        this.mockMvc.perform(post("/api/profile/123")).andExpect(status().isBadRequest());
    }

}
