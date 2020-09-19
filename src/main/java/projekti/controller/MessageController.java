package projekti.controller;

import projekti.service.MessageService;
import projekti.service.AccountService;
import projekti.domain.Message;
import projekti.domain.Comment;
import projekti.domain.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

//  ====== MESSAGES ======
    
    @GetMapping("/messages")
    public String showMessagesAndComments(Model model, @RequestParam(defaultValue = "0") Integer msgPage) {
        
        Account currentAccount = accountService.getCurrentAccount();

        Page<Message> messagePage = messageService.getAllContactMessages(currentAccount, msgPage);
        List<Page<Comment>> commentPagesForMessages = messageService.getAllMessageComments();
        
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("messagePage", messagePage);
        model.addAttribute("commentPages", commentPagesForMessages);

        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(@RequestParam String content) {

        messageService.addMessage(content);
        return "redirect:/messages";
    }

    @PutMapping("/messages/{id}")
    public String addLikeToMessage(@PathVariable("id") Long id, @RequestParam(defaultValue = "0") Integer msgPage) {

        messageService.addLikeToMessage(id);
        return "redirect:/messages?msgPage=" + msgPage + "#" + id;
    }

    @DeleteMapping("/messages/{id}")
    public String deleteMessage(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer msgPage) {

        Account currentAccount = accountService.getCurrentAccount();
        Page<Message> messagePage = messageService.getAllContactMessages(currentAccount, msgPage);
        if (messagePage.getContent().size() == 1) {
            msgPage = 0;
        }
        
        messageService.deleteMessage(id);
        return "redirect:/messages?msgPage=" + msgPage;
    }

//  ====== COMMENTS ======
    
    @PostMapping("/messages/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String content, @RequestParam(defaultValue = "0") Integer msgPage) {

        messageService.addComment(id, content);

        return "redirect:/messages?msgPage=" + msgPage + "#" + id;
    }

    @PutMapping("/messages/{id}/comments/{commentId}")
    public String addLikeToComment(@PathVariable("commentId") Long commentId, @RequestParam(defaultValue = "0") Integer msgPage) {

        messageService.addLikeToComment(commentId);

        return "redirect:/messages?msgPage=" + msgPage + "#" + commentId;
    }

    @DeleteMapping("/messages/{id}/comments/{commentId}")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId, @RequestParam(defaultValue = "0") Integer msgPage) {

        messageService.deleteComment(id, commentId);

        return "redirect:/messages?msgPage=" + msgPage + "#" + id;
    }

}
