package kbx.repository;

import kbx.model.ProfileView;

import java.util.List;

public interface UserProfileRepository {
    List<ProfileView> listProfileViewsOfUser(Long userId, int withInLastNDays, int maxRecords);

    void recordProfileView(Long userId, ProfileView profileView);
}
