package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long>{
    
    private String content;
    
    @ManyToOne
    private Account account;
    
    private LocalDateTime date;
    
    @ManyToMany
    @JoinTable(
        name = "message_like",
        joinColumns = @JoinColumn(name="message_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="liked_by_account_id", referencedColumnName="id"))
    private List<Account> likes = new ArrayList<>();
    
    
    @JoinTable(
            name = "Message_Comment",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id")
    )
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
