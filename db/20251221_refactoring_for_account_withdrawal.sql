

ALTER TABLE account_titles
  DROP FOREIGN KEY fk_account_titles_account,
  ADD CONSTRAINT fk_account_titles_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE challenge_participants
  DROP FOREIGN KEY fk_challenge_participants_account,
  ADD CONSTRAINT fk_challenge_participants_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE diet_records
  DROP FOREIGN KEY fk_diet_records_account,
  ADD CONSTRAINT fk_diet_records_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE exercise_records
  DROP FOREIGN KEY fk_exercise_records_account,
  ADD CONSTRAINT fk_exercise_records_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE follows
  DROP FOREIGN KEY fk_follows_followee,
  DROP FOREIGN KEY fk_follows_follower,
  ADD CONSTRAINT fk_follows_followee
    FOREIGN KEY (followee_email) REFERENCES accounts(email)
    ON DELETE CASCADE,
  ADD CONSTRAINT fk_follows_follower
      FOREIGN KEY (follower_email) REFERENCES accounts(email)
      ON DELETE CASCADE;


ALTER TABLE post_likes
  DROP FOREIGN KEY fk_post_likes_account,
  ADD CONSTRAINT fk_post_likes_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE profiles
  DROP FOREIGN KEY fk_profiles_account,
  ADD CONSTRAINT fk_profiles_account
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


ALTER TABLE refresh_tokens
  DROP FOREIGN KEY fk_refresh_email,
  ADD CONSTRAINT fk_refresh_email
    FOREIGN KEY (email) REFERENCES accounts(email)
    ON DELETE CASCADE;


INSERT INTO accounts (email, username, password)
VALUES ('deleted@system','dummed', 'dummy')
ON DUPLICATE KEY UPDATE updated_at= NOW();

INSERT INTO profiles (email, introduction)
VALUES ('deleted@system', '탈퇴한 회원입니다.')
ON DUPLICATE KEY UPDATE username = '탈퇴회원', updated_at = NOW();
