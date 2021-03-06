DROP TABLE IF EXISTS PROFILE_VIEWS;

CREATE TABLE PROFILE_VIEWS (
  viewee_user_id INT NOT NULL,
  viewer_user_id INT NOT NULL,
  viewedAt TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_viewee_user_id_and_time ON PROFILE_VIEWS(viewee_user_id, viewedAt);