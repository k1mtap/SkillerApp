package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/profiles/{profile}/contacts")
    public String getAllContacts(Model model, @PathVariable String profile) {

        Account user = accountService.getUser(profile);
        Account currentAccount = accountService.getCurrentAccount();

        if (user == currentAccount) {
            model.addAttribute("currentAccount", currentAccount);
//            model.addAttribute("contactDoneOrPending", contactService.contactAlreadyDoneOrPending(currentUser, user));
            return "contacts";
        }

        return "redirect:/profiles/" + profile;
    }

    @GetMapping("/profiles/{profile}/contacts/send_contact_request")
    public String sendContactRequest(@PathVariable String profile) {

        Account askingAccount = accountService.getCurrentAccount();
        Account targetAccount = accountService.getUser(profile);

        contactService.sendContactRequest(askingAccount, targetAccount);

        return "redirect:/profiles/" + profile;
    }

    @GetMapping("/profiles/{profile}/contacts/{id}/approve_contact")
    public String approveContact(@PathVariable String profile, @PathVariable Long id) {

        contactService.approveContact(id);

        return "redirect:/profiles/" + profile + "/contacts";
    }

    @GetMapping("/profiles/{profile}/contacts/{id}/decline_contact")
    public String declineContact(@PathVariable String profile, @PathVariable Long id) {
        
        contactService.declineContact(id);
        
        return "redirect:/profiles/" + profile + "/contacts";
    }

    @DeleteMapping("/profiles/{profile}/contacts/{id}/delete")
    public String deleteContact(@PathVariable String profile, @PathVariable Long id) {
        
        contactService.deleteContact(id);
        
        return "redirect:/profiles/" + profile + "/contacts";
    }
}
