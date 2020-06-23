package projekti.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long> {

    private String message;
    private LocalDateTime timestamp;

    @ManyToOne
    private Account poster;

    @ManyToMany
    private Set<Account> liker;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "message")
    private List<MessageComment> comments;

    public List<MessageComment> getComments() {
        if (comments.size() > 5) {
            return comments.subList(comments.size() - 5, comments.size());
        }
        return comments;
    }
}
