package projekti.service;

import projekti.domain.Image;
import projekti.domain.Account;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageRepository imageRepository;

    public void addImage(MultipartFile file, String profile) throws IOException {

        Image i = new Image();
        i.setContent(file.getBytes());
        imageRepository.save(i);

        Account a = accountService.getUser(profile);
        a.setImage(i);
        accountService.save(a);
    }
    
    public void deleteImage(String profile) {
        
        Account a = accountService.getUser(profile);
        Image i = a.getImage();
        a.setImage(null);
        imageRepository.delete(i);
        accountService.save(a);
    }
}
