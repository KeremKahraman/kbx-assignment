package kbx.repository;

import kbx.model.ProfileView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileRepositoryJDBCImpl implements UserProfileRepository {
    private static final String INSERT_QUERY = "INSERT INTO PROFILE_VIEWS VALUES (?, ?, ?)";
    private static final String SELECT_QUERY =
            "SELECT * FROM (SELECT viewer_user_id, viewedAt " +
                    "FROM PROFILE_VIEWS WHERE viewee_user_id = ? " +
                    "AND viewedAt > current_date - CAST (? AS INTEGER) " +
                    "ORDER BY viewedAt DESC) " +
                    "LIMIT ?";

    private final JdbcTemplate jdbcTemplate;

    public UserProfileRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ProfileView> listProfileViewsOfUser(Long userId, int withInLastNDays, int maxRecords) {
        return jdbcTemplate.query(SELECT_QUERY,
                new Object[]{userId, withInLastNDays, maxRecords},
                (rs, rowNum) ->
                        new ProfileView(
                                rs.getLong(1),
                                rs.getTimestamp(2)
                        )
        );
    }

    @Override
    public void recordProfileView(Long userId, ProfileView profileView) {
        jdbcTemplate.update(INSERT_QUERY, userId, profileView.getViewedBy(), profileView.getViewedAt());
    }
}
