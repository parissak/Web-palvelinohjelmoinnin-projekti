package projekti.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
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
public class AccountPhoto extends AbstractPersistable<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    
    @OneToOne
    private Account account;
}
