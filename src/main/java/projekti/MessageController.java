package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired private MessageService messageService;
    @Autowired private AccountService accountService;

    @GetMapping("/messages")
    public String getMessagePage() {
        
        return "redirect:/1/messages";
    }
    
    @GetMapping("/{currentPage}/messages")
    public String showMessages(Model model, @PathVariable("currentPage") Integer currentPage) {
        
        Account currentAccount = accountService.getCurrentAccount();
        Page<Message> p = messageService.getAllContactMessages(currentAccount, currentPage);
        int totalPages = p.getTotalPages();
        List<Message> allMessages = p.getContent();
        
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("messages", allMessages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        
        return "forum";
    }

//    TODO - onko parempi toteuttaa ModelAttribute Message message?
    @PostMapping("/messages")
    public String addMessage(@RequestParam String content) {
        
        messageService.addMessage(content);
        return "redirect:/messages";
    }
    
    @GetMapping("/{currentPage}/messages/{id}/like")
    public String addLikeToMessage(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage) {
        
        messageService.addLikeToMessage(id);
        return "redirect:/" + currentPage + "/messages";
    }
    
//    TODO - käytä deletemapping? ja hoida forum.html:stä form blockline tms css:stä
    @GetMapping("/{currentPage}/messages/{id}") 
    public String deleteMessage(@PathVariable("currentPage") Integer currentPage, @PathVariable Long id) {
        
        messageService.deleteMessage(id);
        return "redirect:/" + currentPage + "/messages";
    }
    
    @PostMapping("/{currentPage}/messages/{id}/comments")
    public String addComment(@PathVariable("currentPage") Integer currentPage, @PathVariable Long id, @RequestParam String content) {
        
        messageService.addComment(id, content);
        return "redirect:/" + currentPage + "/messages";
    }
    
    @GetMapping("/{currentPage}/messages/{id}/comments/{commentId}")
    public String addLikeToComment(@PathVariable("currentPage") Integer currentPage, @PathVariable("commentid") Long commentId) {
        
        return "redirect:/" + currentPage + "/messages";
    }
    
    @DeleteMapping("/{currentPage}/messages/{id}/comments/{commentId}")
    public String deleteComment(@PathVariable("currentPage") Integer currentPage, @PathVariable("commentid") Long commentId) {
        
        return "redirect:/" + currentPage + "/messages";
    }

}
