package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuniGallery.entity.Album;
import softuniGallery.repository.AlbumRepository;

import java.util.List;

@Controller
public class AlbumHomeController {

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/album/viewAlbums")
    public String indexAlbum(Model model) {

        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("view", "album/indexAlbum");
        model.addAttribute("albums", albums);

        return "base-layout";
    }
}
