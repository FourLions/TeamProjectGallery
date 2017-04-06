package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import softuniGallery.bindingModel.AlbumBindingModel;
import softuniGallery.entity.Album;
import softuniGallery.entity.User;
import softuniGallery.repository.AlbumRepository;
import softuniGallery.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@Controller
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/album/createAlbum")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        model.addAttribute("view", "album/createAlbum");
        return "base-layout";
    }


    @PostMapping("/album/createAlbum")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(AlbumBindingModel albumBindingModel, HttpServletRequest servletRequest) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());


        Album albumEntity = new Album(
                albumBindingModel.getName(),
                userEntity
        );

        List<MultipartFile> files = albumBindingModel.getPictures();
        List<String> listImages = new LinkedList<>();

        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {

                if (files.get(i) != null) {
                    String originalName = files.get(i).getOriginalFilename();
                    File imageFile = new File("C:\\Users\\User\\IdeaProjects\\TeamProjectGallery\\gallery\\src\\main\\resources\\static\\images", originalName);
                    try {
                        files.get(i).transferTo(imageFile);
                        String image = "/images/" + originalName;
                        listImages.add(image);
                        albumEntity.setImagePathList(listImages);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        this.albumRepository.saveAndFlush(albumEntity);
        return "redirect:/album/viewAlbums";
    }

   /* private boolean isUserAuthorOrAdmin(Album album) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(album);
    }*/
}
