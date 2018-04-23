package project.project_new.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import project.project_new.exception.BiddingItemNotFound;
import project.project_new.exception.AttachmentNotFound;
import project.project_new.model.Attachment;
import project.project_new.model.Bidding;
import project.project_new.service.AttachmentService;
import project.project_new.service.BiddingService;
import project.project_new.view.DownloadingView;


@Controller
@RequestMapping("bidding")
public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("biddingDatabase", biddingService.getBidding());
        return "list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("add", "biddingForm", new Form());
    }
    @RequestMapping(value = "bid", method = RequestMethod.GET)
    public ModelAndView bid() {
        return new ModelAndView("bid", "biddingForm", new Form());
    }

    public static class Form {

        private String itemsubject;
        private String body;
        private List<MultipartFile> attachments;
        private String price;
        private int numbid;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getItemsubject() {
            return itemsubject;
        }

        public void setItemsubject(String itemsubject) {
            this.itemsubject = itemsubject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    public int getNumbid() {
      return numbid;
    }

    public void setNumbid(int numbid) {
      this.numbid = numbid;
    }
        
        

    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Form form, Principal principal) throws IOException {
        long biddingId = biddingService.createBidding(principal.getName(),
                form.getItemsubject(), form.getBody(), form.getAttachments(),form.getPrice());
        return "redirect:/bidding/view/" + biddingId;
    }

    @RequestMapping(value = "view/{biddingId}", method = RequestMethod.GET)
    public String view(@PathVariable("biddingId") long biddingId,
            ModelMap model) {
        Bidding bidding = biddingService.getBidding(biddingId);
        if (bidding == null) {
            return "redirect:/bidding/list";
        }
        model.addAttribute("bidding", bidding);
        return "view";
    }

    @RequestMapping(
            value = "/{biddingId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("biddingId") long biddingId,
            @PathVariable("attachment") String name) {

        Attachment attachment = attachmentService.getAttachment(biddingId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/bidding/list", true);
    }

    @RequestMapping(value = "delete/{biddingId}", method = RequestMethod.GET)
    public String deleteBidding(@PathVariable("biddingId") long biddingId)
            throws BiddingItemNotFound {
        biddingService.delete(biddingId);
        return "redirect:/bidding/list";
    }

    @RequestMapping(value = "edit/{biddingId}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable("biddingId") long biddingId,
            Principal principal, HttpServletRequest request) {
        Bidding bidding = biddingService.getBidding(biddingId);
        if (bidding == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(bidding.getCustomerName()))) {
            return new ModelAndView(new RedirectView("/bidding/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("bidding", bidding);

        Form biddingForm = new Form();
        biddingForm.setItemsubject(bidding.getItemsubject());
        biddingForm.setPrice(bidding.getPrice());
        biddingForm.setBody(bidding.getBody());
        modelAndView.addObject("biddingForm", biddingForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{biddingId}", method = RequestMethod.POST)
    public View edit(@PathVariable("biddingId") long biddingId, Form form,
            Principal principal, HttpServletRequest request)
            throws IOException, BiddingItemNotFound {
        Bidding bidding = biddingService.getBidding(biddingId);
        if (bidding == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(bidding.getCustomerName()))) {
            return new RedirectView("/bidding/list", true);
        }

        biddingService.updateBidding(biddingId, form.getItemsubject(),
                form.getBody(), form.getAttachments());
        return new RedirectView("/bidding/view/" + biddingId, true);
    }

    @RequestMapping(
            value = "/{biddingId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public String deleteAttachment(@PathVariable("biddingId") long biddingId,
            @PathVariable("attachment") String name) throws AttachmentNotFound {
        biddingService.deleteAttachment(biddingId, name);
        return "redirect:/bidding/edit/" + biddingId;
    }

    

    
    @RequestMapping(value = "bid/{biddingId}", method = RequestMethod.GET)
    public ModelAndView bid(@PathVariable("biddingId") long biddingId
       ) {
        Bidding bidding = biddingService.getBidding(biddingId);
        
        ModelAndView modelAndView = new ModelAndView("bid");
        modelAndView.addObject("bidding", bidding);

        Form biddingForm = new Form();
        biddingForm.setPrice(bidding.getPrice());
        biddingForm.setNumbid(bidding.getNumbid());
        modelAndView.addObject("biddingForm", biddingForm);

        return modelAndView;
    }

    @RequestMapping(value = "bid/{biddingId}", method = RequestMethod.POST)
    public View bid(@PathVariable("biddingId") long biddingId, Form form ){
        Bidding bidding = biddingService.getBidding(biddingId);

        biddingService.updateNumBidAndPrice(biddingId, bidding.getNumbid(),
                form.getPrice());
        return new RedirectView("/bidding/view/" + biddingId, true);
    }
    
}
