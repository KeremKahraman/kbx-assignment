package kbx.model;

import java.util.Date;

public class ProfileView {
    private Long viewedBy;
    private Date viewedAt;

    public ProfileView(Long viewedBy, Date viewedAt) {
        this.viewedBy = viewedBy;
        this.viewedAt = viewedAt;
    }

    public Long getViewedBy() {
        return viewedBy;
    }

    public Date getViewedAt() {
        return viewedAt;
    }

}
