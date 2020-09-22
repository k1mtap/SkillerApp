package projekti.service;

import projekti.domain.Message;
import projekti.domain.Comment;
import projekti.domain.Account;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.repository.CommentRepository;
import projekti.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AccountService accountService;

//  ====== MESSAGES ======   
    
    public Message getOne(Long id) {
        return messageRepository.getOne(id);
    }

    public Page<Message> getAllContactMessages(Account account, Integer page) {

        Pageable pageable = PageRequest.of(page, 10);

        return messageRepository.findAllContactMessages(account.getId(), pageable);
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

    public Integer getLikesForMessages(@PathVariable Long id) {

        return messageRepository.getLikesForMessage(id);
    }

    @Transactional
    public void deleteMessage(Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Message message = messageRepository.getOne(id);

        if (currentAccount == message.getAccount()) {

            commentRepository.deleteMessageCommentsLikes(id);
            commentRepository.deleteMessageComments(id);
            messageRepository.delete(message);
        }
    }

//  ====== COMMENTS ======
    
    public List<Comment> getComments(@PathVariable Long id) {

        return commentRepository.findByMessageId(id);
    }

    public void addComment(Long id, String content) {

        Message message = messageRepository.getOne(id);

        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setContent(content);
        comment.setAccount(accountService.getCurrentAccount());
        comment.setDate(LocalDateTime.now());
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
    
    public Integer getLikesForComments(@PathVariable Long id) {

        return commentRepository.getLikesForComments(id);
    }


    public void deleteComment(Long id, Long commentId) {

        Account currentAccount = accountService.getCurrentAccount();
        Comment comment = commentRepository.getOne(commentId);
        Message message = messageRepository.getOne(id);

        if (currentAccount == comment.getAccount()) {
            message.getComments().remove(comment);
            commentRepository.delete(comment);
        }
    }
}
