
package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ImageRepository imageRepository;
    
    @PostMapping("/profiles/{profile}/image")
    public String addImage(@RequestParam("image") MultipartFile file, @PathVariable String profile) throws IOException {

        // TODO - useampi tiedostotyyppi?
        if (!file.getContentType().equals("image/png")) {
            // TODO - viesti käyttäjälle, että kuva on väärää tyyppiä
            return "redirect:/profiles/" + profile + "/edit";
        }

        Image i = new Image();
        i.setContent(file.getBytes());
        imageRepository.save(i);

        Account a = accountRepository.findByProfile(profile);
        a.setImage(i);
        accountRepository.save(a);

        return "redirect:/profiles/" + profile + "/edit";
    }
    
    @GetMapping("/profiles/{profile}/image/delete")
    public String deleteImage(@PathVariable String profile) throws IOException {

        Account a = accountRepository.findByProfile(profile);
        Image i = a.getImage();
        a.setImage(null);
        imageRepository.delete(i);
        accountRepository.save(a);

        return "redirect:/profiles/" + profile + "/edit";
    }

    @GetMapping(path = "/profiles/{profile}/image/content", produces = "image/png")
    @ResponseBody
    public byte[] getContent(@PathVariable String profile) {
        return accountRepository.findByProfile(profile).getImage().getContent();
    }
}
