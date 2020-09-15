package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    public Account getUser(String profile) {
        
        return accountRepository.findByProfile(profile);
    }
    
    public Account getUser(Long id) {
        
        return accountRepository.getContactById(id);
    }
    
    public Account getCurrentAccount() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());
        return currentUser;
    }

    public String getProfile(String profile) {
        
        return accountRepository.findByProfile(profile).getProfile();
    }
    

    public String toTitleCase(String name) {
        
        String[] array = name.trim().split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(Character.toUpperCase(array[i].charAt(0)))
                    .append(array[i].substring(1)).append(" ");
        }
        
        return sb.toString().trim();
    }
    
    public List<Account> getAllAccounts() {
        
        return accountRepository.findAll();
    }
    
    public List<Account> findByKeyword(String keyword) {
        
        return accountRepository.findByKeyword(keyword);
    }
    
    public void saveAll(Account account1, Account account2) {
        
        accountRepository.save(account1);
        accountRepository.save(account2);
    }
}
