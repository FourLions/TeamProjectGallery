package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.bindingModel.AlbumBindingModel;
import softuniGallery.entity.Album;
import softuniGallery.repository.AlbumRepository;
import softuniGallery.repository.UserRepository;

import javax.persistence.Transient;
import java.util.List;

@Controller
@RequestMapping("/admin/albums")
public class AdminAlbumController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/")
    public String listAlbums(Model model){
        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("albums", albums);
        model.addAttribute("view", "admin/album/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        model.addAttribute("album", album);
        model.addAttribute("view", "admin/album/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id,
                              AlbumBindingModel albumBindingModel) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        album.setName(albumBindingModel.getName());

        this.albumRepository.saveAndFlush(album);

        return "redirect:/admin/albums/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        if (!this.albumRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        model.addAttribute("album", album);
        model.addAttribute("view", "admin/album/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        this.albumRepository.delete(album);

        return "redirect:/admin/albums/";
    }

}