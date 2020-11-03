package kbx.service;

import kbx.model.ProfileView;
import kbx.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<ProfileView> listProfileViews(Long userId) {
        return userProfileRepository.listProfileViewsOfUser(userId, 30, 20);
    }

    @Override
    @Transactional
    public void recordProfileView(Long userId, ProfileView profileView) {
        userProfileRepository.recordProfileView(userId, profileView);
    }

}
