package projekti.repository;

import projekti.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    
    @Query(value = "SELECT * FROM Comment C WHERE C.message_id = :message_id ORDER BY C.date DESC LIMIT 10", nativeQuery = true)
    List<Comment> findByMessageId(Long message_id);
    
    @Query(value = 
            "SELECT COUNT(liked_by_account_id ) FROM Comment_Like WHERE comment_id = :id", nativeQuery = true)
    Integer getLikesForComments(Long id);
    
    @Modifying
    @Query(value = "DELETE FROM Comment C WHERE C.message_id = :id", nativeQuery = true)
    void deleteMessageComments(Long id);
    
    @Modifying
    @Query(value = "DELETE FROM Comment_Like CL WHERE CL.comment_id IN (SELECT C.id FROM Comment C WHERE C.message_id = :id)", nativeQuery = true)
    void deleteMessageCommentsLikes(Long id);
}
