package project.project_new.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project_new.model.BiddingUser;

public interface BiddingUserRepository extends JpaRepository<BiddingUser, String> {
}
