package project.project_new.service;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import project.project_new.exception.AttachmentNotFound;
import project.project_new.exception.BiddingItemNotFound;
import project.project_new.model.Bidding;

public interface BiddingService {

    public long createBidding(String customerName, String subject,
            String body, List<MultipartFile> attachments, String price) throws IOException;

    public List<Bidding> getBidding();

    public Bidding getBidding(long id);

    public void updateBidding(long id, String subject,
            String body, List<MultipartFile> attachments)
            throws IOException, BiddingItemNotFound;

    public void delete(long id) throws BiddingItemNotFound;

    public void deleteAttachment(long biddingId, String name)
            throws AttachmentNotFound;
}
