package projekti.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequest extends AbstractPersistable<Long> {

    @ManyToOne
    private Account accountFrom;

    @ManyToOne
    private Account accountTo;
}
