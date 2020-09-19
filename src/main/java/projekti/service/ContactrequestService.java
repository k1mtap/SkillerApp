package projekti.service;

import projekti.domain.Contactrequest;
import projekti.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.repository.ContactrequestRepository;

@Service
public class ContactrequestService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ContactrequestRepository contactrequestRepository;

    public Contactrequest getContact(Long id) {
        return contactrequestRepository.getOne(id);
    }

    public void sendContactRequest(Account askingAccount, Account targetAccount) {

        if (contactAlreadyDoneOrPending(askingAccount, targetAccount)) {
            return;
        }

        Contactrequest contactRequest = new Contactrequest();
        contactRequest.setAskingAccount(askingAccount);
        contactRequest.setTargetAccount(targetAccount);
        contactrequestRepository.save(contactRequest);

        askingAccount.getSentContactRequests().add(contactRequest);
        targetAccount.getReceivedContactRequests().add(contactRequest);

        accountService.saveAll(askingAccount, targetAccount);

    }

    public void approveContact(Long id) {

        Contactrequest contactRequest = getContact(id);
        Account targetAccount = contactRequest.getTargetAccount();
        Account askingAccount = contactRequest.getAskingAccount();

        targetAccount.getContacts().add(askingAccount);
        targetAccount.getReceivedContactRequests().remove(contactRequest);

        askingAccount.getContacts().add(targetAccount);
        askingAccount.getSentContactRequests().remove(contactRequest);

        accountService.saveAll(askingAccount, targetAccount);

        contactrequestRepository.delete(contactRequest);

    }

    public void declineContact(Long id) {

        Contactrequest contactRequest = getContact(id);
        Account targetAccount = contactRequest.getTargetAccount();
        Account askingAccount = contactRequest.getAskingAccount();

        targetAccount.getReceivedContactRequests().remove(contactRequest);
        askingAccount.getSentContactRequests().remove(contactRequest);

        accountService.saveAll(askingAccount, targetAccount);

        contactrequestRepository.delete(contactRequest);
    }

    public void deleteContact(Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Account contactAccount = accountService.getUser(id);

        currentAccount.getContacts().remove(contactAccount);
        contactAccount.getContacts().remove(currentAccount);

        accountService.saveAll(currentAccount, contactAccount);
    }

    public Boolean contactAlreadyDoneOrPending(Account account1, Account account2) {

        if (account1.getContacts().contains(account2)) {
            return true;
        }

        Long account1_id = account1.getId();
        Long account2_id = account2.getId();

        if (contactrequestRepository.findByAccounts(account1_id, account2_id) != null) {
            return true;
        }

        return false;
    }
}
