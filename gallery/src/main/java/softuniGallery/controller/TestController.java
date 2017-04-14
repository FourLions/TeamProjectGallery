package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniGallery.entity.Album;
import softuniGallery.entity.User;
import softuniGallery.repository.AlbumRepository;
import softuniGallery.repository.UserRepository;

import java.util.List;

/**
 * Created by George-Lenovo on 4/14/2017.
 */
@Controller
public class TestController {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public String testMap(Model model) {
        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("view", "album/indexAlbum1");
        model.addAttribute("albums", albums);

        return "base-layout";
    }

    /*
        @PostMapping("/test")
        @PreAuthorize("isAuthenticated()")
        public String testPost(){

        }*/
    @GetMapping("/test/album/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/test";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Album album = this.albumRepository.findOne(id);

        model.addAttribute("album", album);
        model.addAttribute("view", "album/details");

        return "base-layout";
    }
}
