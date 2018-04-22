package project.project_new.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project_new.dao.AttachmentRepository;
import project.project_new.model.Attachment;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public Attachment getAttachment(long biddingId, String name) {
        return attachmentRepo.findByTicketIdAndName(biddingId, name);
    }
}
