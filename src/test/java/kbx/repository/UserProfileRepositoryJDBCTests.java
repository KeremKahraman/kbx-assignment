package kbx.repository;

import kbx.model.ProfileView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

@SpringBootTest
class UserProfileRepositoryJDBCTests {
    private final static long DAY_IN_MS = 1000 * 60 * 60 * 24;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void recordProfileViewShouldInsertNewRow() {
        final var countBefore = this.jdbcTemplate.queryForObject("SELECT COUNT (*) FROM PROFILE_VIEWS", Integer.class);
        userProfileRepository.recordProfileView(123L, new ProfileView(4444L, new Date()));
        final var countAfter = this.jdbcTemplate.queryForObject("SELECT COUNT (*) FROM PROFILE_VIEWS", Integer.class);
        Assertions.assertEquals(countBefore + 1, countAfter);
    }

    @Test
    void listProfileViewsOfUsersShouldNotReturnMoreThanGivenLimit() {
        int limit = 20;
        for (int i = 0; i < limit + 1; i++) {
            userProfileRepository.recordProfileView(123L, new ProfileView(4444L, new Date()));
        }
        final var count = this.jdbcTemplate.queryForObject("SELECT COUNT (*) FROM PROFILE_VIEWS", Integer.class);
        Assertions.assertEquals(limit + 1, count);
        final var profileViews = userProfileRepository.listProfileViewsOfUser(123L, 1, limit);
        Assertions.assertEquals(limit, profileViews.size());
    }

    @Test
    void listProfileViewsOfUsersShouldNotReturnOlderViews() {
        int limit = 20;
        int withInLastNDays = 2;
        userProfileRepository.recordProfileView(123L, new ProfileView(4444L, new Date()));
        userProfileRepository
                .recordProfileView(123L,
                        new ProfileView(3333L,
                                new Date(System.currentTimeMillis() - ((withInLastNDays + 1) * DAY_IN_MS))
                        )
                );
        final var count = this.jdbcTemplate.queryForObject("SELECT COUNT (*) FROM PROFILE_VIEWS", Integer.class);
        Assertions.assertEquals(2, count);
        final var profileViews = userProfileRepository.listProfileViewsOfUser(123L, withInLastNDays, limit);
        Assertions.assertEquals(1, profileViews.size());
    }

    @Test
    void listProfileViewsOfUsersShouldReturnAllViewsWithInLastNDays() {
        int limit = 20;
        int withInLastNDays = 2;
        userProfileRepository.recordProfileView(123L, new ProfileView(4444L, new Date()));
        userProfileRepository
                .recordProfileView(123L,
                        new ProfileView(3333L,
                                new Date(System.currentTimeMillis() - (withInLastNDays * DAY_IN_MS))
                        )
                );
        userProfileRepository
                .recordProfileView(123L,
                        new ProfileView(3333L,
                                new Date(System.currentTimeMillis() - (withInLastNDays * DAY_IN_MS))
                        )
                );
        userProfileRepository
                .recordProfileView(123L,
                        new ProfileView(3333L,
                                new Date(System.currentTimeMillis() - (withInLastNDays * DAY_IN_MS))
                        )
                );
        final var count = this.jdbcTemplate.queryForObject("SELECT COUNT (*) FROM PROFILE_VIEWS", Integer.class);
        Assertions.assertEquals(4, count);
        final var profileViews = userProfileRepository.listProfileViewsOfUser(123L, withInLastNDays, limit);
        Assertions.assertEquals(4, profileViews.size());
    }

    @BeforeEach
    void clearData() {
        jdbcTemplate.update("DELETE FROM PROFILE_VIEWS");
    }

}
