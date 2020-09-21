package projekti.controller;

import projekti.service.ContactrequestService;
import projekti.service.SkillService;
import projekti.service.AccountService;
import projekti.domain.Account;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private ContactrequestService contactService;

    @GetMapping("/login")
    public String showCustomLoginForm() {

        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute Account account) {

        return "signup";
    }

    @PostMapping("/signup")
    public String addNewAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult, HttpServletRequest req) {

        if (bindingResult.hasErrors()
                || accountService.getByUsername(account.getUsername()) != null
                || accountService.getUser(account.getProfile()) != null) {

            if (accountService.getByUsername(account.getUsername()) != null) {
                bindingResult.rejectValue("username", "username.exist", "Username already in use");
            }

            if (accountService.getUser(account.getProfile()) != null) {
                bindingResult.rejectValue("profile", "profile.exist", "Profile already in use");
            }

            return "signup";
        }

        accountService.modifyGivenNameAndProfile(account);
        
        String password = account.getPassword();
        account.setPassword(passwordEncoder.encode(password));
        accountService.save(account);

        // AUTOMATICALLY LOG IN THE USER AFTER SIGN UP. PASSWORD IS UNENCRYPTED HERE.
        try {
            req.login(account.getUsername(), password);
        } catch (ServletException e) {
            System.out.println("Error: " + e);
            return "redirect:/login";
        }

        return "redirect:/profiles";
    }

    @GetMapping("/profiles")
    public String authentication() {

        return "redirect:/profiles/" + accountService.getCurrentAccount().getProfile();
    }

    @GetMapping("/profiles/{profile}")
    public String getProfile(Model model, @PathVariable String profile) {

        Account accountByProfile = accountService.getUser(profile);
        Account currentAccount = accountService.getCurrentAccount();

        model.addAttribute("account", accountByProfile);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("skills", skillService.getSkills(accountByProfile));
        model.addAttribute("contactDoneOrPending", contactService.contactAlreadyDoneOrPending(currentAccount, accountByProfile));

        return "profile";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyword) {

        keyword = keyword.toLowerCase();

        if (keyword != null) {
            model.addAttribute("accounts", accountService.findByKeyword(keyword));
        } else {
            model.addAttribute("accounts", accountService.getAllAccounts());
        }

        model.addAttribute("currentAccount", accountService.getCurrentAccount());

        return "search";
    }

    @GetMapping("/searchpage")
    public String searchPage(Model model) {

        model.addAttribute("currentAccount", accountService.getCurrentAccount());

        return "search";
    }

}
