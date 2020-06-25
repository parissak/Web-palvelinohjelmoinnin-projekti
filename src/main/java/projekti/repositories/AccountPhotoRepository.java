 
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Account;
import projekti.models.AccountPhoto;

public interface AccountPhotoRepository extends JpaRepository<AccountPhoto, Long>{
    
}
