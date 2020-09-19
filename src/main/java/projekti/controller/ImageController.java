package projekti.controller;

import projekti.service.ImageService;
import projekti.service.AccountService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageService imageService;

    @PostMapping("/profiles/{profile}/image")
    public String addImage(@RequestParam("image") MultipartFile file, @PathVariable String profile) throws IOException {

        if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {

            imageService.addImage(file, profile);

            return "redirect:/profiles/" + profile + "/skills";
        }

        return "redirect:/profiles/" + profile + "/skills";
    }

    @DeleteMapping("/profiles/{profile}/image")
    public String deleteImage(@PathVariable String profile) throws IOException {

        imageService.deleteImage(profile);

        return "redirect:/profiles/" + profile + "/skills";
    }

    @GetMapping(path = "/profiles/{profile}/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getContent(@PathVariable String profile) {

        return accountService.getImage(profile);
    }
}
