package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

//@NamedEntityGraph(
//        name = "message-entity-graph-with-comment-users-likes",
//        attributeNodes = {
//            @NamedAttributeNode("content"),
//            @NamedAttributeNode("date"),
//            @NamedAttributeNode("account"),
//            @NamedAttributeNode("likes"),
//            @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),},
//        subgraphs = {
//            @NamedSubgraph(
//                    name = "comments-subgraph",
//                    attributeNodes = {
//                        @NamedAttributeNode("account"),
//                        @NamedAttributeNode("likes")
//                    }
//            )
//        }
//)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long> {

    private String content;

    private LocalDateTime date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "message_like",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "liked_by_account_id", referencedColumnName = "id"))
    private List<Account> likes = new ArrayList<>();

//    @JoinTable(
//            name = "Message_Comment",
//            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id")
//    )
    @OneToMany(mappedBy = "message")
    private List<Comment> comments = new ArrayList<>();

}
