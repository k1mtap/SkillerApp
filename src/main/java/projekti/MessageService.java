
package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AccountService accountService;
    
    public Page<Message> getAllContactMessages(Account account, Integer page){
        
//        TODO - muuta 5 -> 25
        Pageable sortedByDateDesc = PageRequest.of(page - 1, 5, Sort.by("date").descending());
        
        List<Long> ids = new ArrayList<>();
        for (Account a : account.getContacts()) {
            ids.add(a.getId());
        }
        
        return messageRepository.findAllContactMessages(account.getId(), ids, sortedByDateDesc);
    }
    
    public void addMessage(String content) {
        Message message = new Message();
        message.setContent(content);
        message.setDate(LocalDateTime.now());
        message.setAccount(accountService.getCurrentAccount());
        messageRepository.save(message);
    }
    
    public void addLikeToMessage(Long id) {
        
        Account currentAccount = accountService.getCurrentAccount();
        Message message = messageRepository.getOne(id);
        
        if (message.getLikes().contains(currentAccount)) {
            message.getLikes().remove(currentAccount);
            messageRepository.save(message);
            return;
        }

        message.getLikes().add(currentAccount);
        messageRepository.save(message);
    }
    
    public void deleteMessage(Long id) {
        
        Account currentAccount = accountService.getCurrentAccount();
        Message message = messageRepository.getOne(id);
        
        if (currentAccount == message.getAccount()) {
            messageRepository.delete(message);
        }
    }
    
    
//  ====== COMMENTS ======
    
    public void addComment(Long id, String content) {
        
        Message message = messageRepository.getOne(id);
        
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAccount(accountService.getCurrentAccount());
        comment.setDateAndTime(LocalDateTime.now());
        commentRepository.save(comment);
        
        message.getComments().add(comment);
        messageRepository.save(message);
    }
    
    public void addLikeToComment(Long id) {
        
        Account currentAccount = accountService.getCurrentAccount();
        Comment comment = commentRepository.getOne(id);
        
        if (comment.getLikes().contains(currentAccount)) {
            comment.getLikes().remove(currentAccount);
            commentRepository.save(comment);
            return;
        }

        comment.getLikes().add(currentAccount);
        commentRepository.save(comment);
    }
}
