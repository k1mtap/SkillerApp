
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());
        return currentUser;
    }
    
    public String getProfile(String profile) {
        return accountRepository.findByProfile(profile).getProfile();
    }
    
    public void addSkillAndSave(Skill skill) {
        Account user = getCurrentUser();
        user.getSkills().add(skill);
        accountRepository.save(user);
    }
}
