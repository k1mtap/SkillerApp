package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Comment extends AbstractPersistable<Long>{
    
    @Column(columnDefinition = "TEXT", length = 2500)
    private String content;
    
    @ManyToOne
    private Account account;
    
    private LocalDateTime dateAndTime;
    
    @OneToMany
    @JoinTable(
        name="comment_like",
        joinColumns=
            @JoinColumn(name="comment_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="liked_by_account_id", referencedColumnName="id"))
    private List<Account> likes;
}
