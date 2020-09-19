package projekti.domain;

import projekti.domain.Account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractPersistable<Long>{
    
    @Column(columnDefinition = "TEXT", length = 2500)
    private String content;
    
    private LocalDateTime date;
    
    @ManyToOne
    private Account account;
    
    @ManyToMany
    @JoinTable(
        name = "comment_like",
        joinColumns = @JoinColumn(name="comment_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="liked_by_account_id", referencedColumnName="id"))
    private List<Account> likes = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;
}
