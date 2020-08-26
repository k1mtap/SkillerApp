
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ContactRepository contactRepository;
    
    public Contact getContact(Long id) {
        return contactRepository.getOne(id);
    }
    
    public void sendContactRequest(Account askingAccount, Account targetAccount) {
        
        if (contactAlreadyDoneOrPending(askingAccount, targetAccount)) {
            return;
        }
        
        Contact contact = new Contact();
        contact.setAskingAccount(askingAccount);
        contact.setTargetAccount(targetAccount);
//        contact.setApproved(false);
        contactRepository.save(contact);
        
        askingAccount.getSentContacts().add(contact);
        targetAccount.getReceivedContacts().add(contact);
        
        accountService.saveAll(askingAccount, targetAccount);
        
    }
    
    public void approveContact(Long id) {
        
        Contact contact = getContact(id);
        Account targetAccount = contact.getTargetAccount();
        Account askingAccount = contact.getAskingAccount();
        
        targetAccount.getContacts().add(askingAccount);
        targetAccount.getReceivedContacts().remove(contact);
        
        askingAccount.getContacts().add(targetAccount);
        askingAccount.getSentContacts().remove(contact);
        
        accountService.saveAll(askingAccount, targetAccount);
        
        contactRepository.delete(contact);
        
    }
    
    public void declineContact(Long id) {
        
        Contact contact = getContact(id);
        Account targetAccount = contact.getTargetAccount();
        Account askingAccount = contact.getAskingAccount();
        
        targetAccount.getReceivedContacts().remove(contact);
        askingAccount.getSentContacts().remove(contact);
        
        accountService.saveAll(askingAccount, targetAccount);
        
        contactRepository.delete(contact);
    }
    
    public void deleteContact(Long id) {
        
        Account currentAccount = accountService.getCurrentAccount();
        Account contactAccount = accountService.getUser(id);
        
        currentAccount.getContacts().remove(contactAccount);
        contactAccount.getContacts().remove(currentAccount);
        
        accountService.saveAll(currentAccount, contactAccount);
    }
    
    public Boolean contactAlreadyDoneOrPending(Account account1, Account account2) {
        
//        TODO - tarvitaanko molempia?
        if (account1.getContacts().contains(account2) || account2.getContacts().contains(account1)) {
            return true;
        }
        
        Long account1_id = account1.getId();
        Long account2_id = account2.getId();
        
        if (contactRepository.findByAccounts(account1_id, account2_id) != null) {
            return true;
        }
        
        return false;
    }
}
