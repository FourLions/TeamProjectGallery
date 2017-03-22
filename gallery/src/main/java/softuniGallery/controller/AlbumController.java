package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import softuniGallery.bindingModel.AlbumBindingModel;
import softuniGallery.entity.Album;
import softuniGallery.repository.AlbumRepository;
import softuniGallery.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by George-Lenovo on 3/22/2017.
 */

@Controller
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    /*@Autowired
    private UserRepository userRepository;*/

    @GetMapping("/profile/createAlbum")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        model.addAttribute("view", "album/createAlbum");
        return "base-layout";
    }


    @PostMapping("/profile/createAlbum")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(AlbumBindingModel albumBindingModel, HttpServletRequest servletRequest) {
       // System.out.print("Printing from createProcces method in albContr");
        /*UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());*/

        Album albumEntity = new Album(
                "testerchee12"/*,
                userEntity*/
        );

        this.albumRepository.saveAndFlush(albumEntity);
        return "redirect:/profile";
    }


}
