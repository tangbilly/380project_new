package project.project_new.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.project_new.dao.AttachmentRepository;
import project.project_new.dao.BiddingRepository;
import project.project_new.exception.AttachmentNotFound;
import project.project_new.exception.BiddingItemNotFound;
import project.project_new.model.Attachment;
import project.project_new.model.Bidding;

@Service
public class BiddingServiceImpl implements BiddingService {

    @Resource
    private BiddingRepository biddingRepo;

    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Bidding> getBidding() {
        return biddingRepo.findAll();
    }

    @Override
    @Transactional
    public Bidding getBidding(long id) {
        return biddingRepo.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = BiddingItemNotFound.class)
    public void delete(long id) throws BiddingItemNotFound {
        Bidding deletedBiddingItem = biddingRepo.findOne(id);
        if (deletedBiddingItem == null) {
            throw new BiddingItemNotFound();
        }
        biddingRepo.delete(deletedBiddingItem);
    }

    @Override
    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long itemId, String name) throws AttachmentNotFound {
        Bidding item = biddingRepo.findOne(itemId);
        for (Attachment attachment : item.getAttachments()) {
            if (attachment.getName().equals(name)) {
                item.deleteAttachment(attachment);
                biddingRepo.save(item);
                return;
            }
        }
        throw new AttachmentNotFound();
    }

    @Override
    @Transactional
    public long createBidding(String ownerName, String itemsubject,
            String body, List<MultipartFile> attachments,String price) throws IOException {
        Bidding bidding = new Bidding();
        bidding.setCustomerName(ownerName);
        bidding.setItemsubject(itemsubject);
        bidding.setBody(body);
        bidding.setPrice(price);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBidding(bidding);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                bidding.getAttachments().add(attachment);
            }
        }
        Bidding savedBidding = biddingRepo.save(bidding);
        return savedBidding.getId();
    }

    @Override
    @Transactional(rollbackFor = BiddingItemNotFound.class)
    public void updateBidding(long id, String subject,
            String body, List<MultipartFile> attachments)
            throws IOException, BiddingItemNotFound {
        Bidding updatedBidding = biddingRepo.findOne(id);
        if (updatedBidding == null) {
            throw new BiddingItemNotFound();
        }

        updatedBidding.setItemsubject(subject);
        updatedBidding.setBody(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBidding(updatedBidding);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedBidding.getAttachments().add(attachment);
            }
        }
        biddingRepo.save(updatedBidding);
    }
    
    @Override
    @Transactional(rollbackFor = BiddingItemNotFound.class)
    public void updateNumBidAndPrice(long id, int numbid , String price){
      Bidding updatedBidding = biddingRepo.findOne(id);
      updatedBidding.setNumbid(numbid+1);
      updatedBidding.setPrice(price);
      biddingRepo.save(updatedBidding);
    }

}
