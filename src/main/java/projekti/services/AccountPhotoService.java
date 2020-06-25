package projekti.services;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.models.Account;
import projekti.models.AccountPhoto;
import projekti.repositories.AccountPhotoRepository;

@Service
public class AccountPhotoService {

    @Autowired
    private AccountPhotoRepository accountPhotoRepository;

    public void deletePhoto(Account account) {
        accountPhotoRepository.delete(account.getAccountPhoto());
    }

    public void savePhoto(Account account, MultipartFile file) throws IOException {
        AccountPhoto photo = new AccountPhoto();
        photo.setPhoto(file.getBytes());
        photo.setAccount(account);
        accountPhotoRepository.save(photo);
    }

    public byte[] getPhoto(Long id) {
        return accountPhotoRepository.getOne(id).getPhoto();
    }
}
