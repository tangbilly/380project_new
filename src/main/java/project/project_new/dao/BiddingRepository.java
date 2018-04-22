package project.project_new.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project_new.model.Bidding;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
}
