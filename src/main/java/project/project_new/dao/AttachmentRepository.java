package project.project_new.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project_new.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    public Attachment findByBiddingIdAndName(long biddingId, String name);
}
