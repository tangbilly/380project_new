package project.project_new.service;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import project.project_new.exception.AttachmentNotFound;
import project.project_new.exception.BiddingItemNotFound;
import project.project_new.model.Bidding;

public interface BiddingService {

    public long createBidding(String customerName, String subject,
            String body, List<MultipartFile> attachments, String price, String comment) throws IOException;

    public List<Bidding> getBidding();

    public Bidding getBidding(long id);
    
    public void updateNumBidAndPrice(long id,int numbid , String price);
    public void updateBidding(long id, String subject,
            String body, List<MultipartFile> attachments)
            throws IOException, BiddingItemNotFound;

    public void delete(long id) throws BiddingItemNotFound;

    public void deleteAttachment(long biddingId, String name)
            throws AttachmentNotFound;

    public void addComment(long id, String msg)throws NullPointerException;
    
    
    public void changeStatus(long id);
 
    
    public void updateWinner(long id, String customerName);
}