package kbx.service;

import kbx.model.ProfileView;

import java.util.List;

public interface UserProfileService {
    List<ProfileView> listProfileViews(Long userId);

    void recordProfileView(Long userId, ProfileView profileView);
}

