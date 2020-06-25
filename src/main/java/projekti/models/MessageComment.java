package projekti.models;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
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
public class MessageComment extends AbstractPersistable<Long> {

    @NotEmpty
    private String comment;
    
    private LocalDateTime stamp;

    @ManyToOne
    private Account poster;

    @ManyToOne
    private Message message;
}
