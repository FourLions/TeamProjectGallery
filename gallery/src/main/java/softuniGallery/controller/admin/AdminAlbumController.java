package softuniGallery.controller.admin;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import softuniGallery.bindingModel.AlbumBindingModel;
import softuniGallery.controller.AlbumController;
import softuniGallery.entity.Album;
import softuniGallery.entity.ImageAlbum;
import softuniGallery.entity.User;
import softuniGallery.repository.AlbumRepository;
import softuniGallery.repository.ImageRepository;
import softuniGallery.repository.UserRepository;

import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin/albums")
public class AdminAlbumController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/")
    public String listAlbums(Model model){
        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("albums", albums);
        model.addAttribute("view", "admin/album/list");

        return "base-layout";
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable Integer id, Model model) {

        ImageAlbum image = this.imageRepository.findOne(id);
        Album album = image.getAlbum();

        if (!this.imageRepository.exists(id) || !isUserAuthorOrAdmin(album)) {
            return "redirect:/admin/albums/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        model.addAttribute("view", "admin/album/details");
        model.addAttribute("image", image);
        model.addAttribute("album", album);

        return "base-layout";
    }

    private boolean isUserAuthorOrAdmin(Album album) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(album);
    }

    @GetMapping("/editName/{id}")
    public String editName(@PathVariable Integer id, Model model) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        model.addAttribute("album", album);
        model.addAttribute("view", "admin/album/editName");

        return "base-layout";
    }

    @PostMapping("/editName/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editNameProcess(@PathVariable Integer id,
                              AlbumBindingModel albumBindingModel) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/admin/albums/";
        }

        Album album = this.albumRepository.findOne(id);

        String name = albumBindingModel.getName();

        album.setName(name);

        this.albumRepository.saveAndFlush(album);

        return "redirect:/admin/albums/";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
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

        List<ImageAlbum> imageAlbumList = album.getImageAlbums();

        for (int i = 0; i < imageAlbumList.size(); i++) {

            ImageAlbum imageAlbum = imageAlbumList.get(i);
            String originalNameAndFolder = imageAlbum.getPath();

            this.imageRepository.delete(imageAlbumList.get(i));

            AlbumController albumController = new AlbumController();

            albumController.deleteFile(originalNameAndFolder);
        }

        this.albumRepository.delete(album);

        return  "redirect:/admin/albums/";
    }

    @GetMapping("/editPicture/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editPicture(@PathVariable Integer id, Model model) {
        if (!this.imageRepository.exists(id)) {
            return "redirect:/admin/details";
        }

        ImageAlbum image = this.imageRepository.findOne(id);
        Album album = image.getAlbum();

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/details";
        }

        model.addAttribute("view", "admin/album/editPicture");
        model.addAttribute("image", image);
        model.addAttribute("album", album);

        return "base-layout";
    }

    @PostMapping("/editPicture/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editPictureProcess(@PathVariable Integer id, AlbumBindingModel albumBindingModel) {
        if (!this.imageRepository.exists(id)) {
            return "redirect:/admin/details";
        }

        ImageAlbum imageAlbum = this.imageRepository.findOne(id);
        Album album = imageAlbum.getAlbum();
        String originalNameAndFolder = imageAlbum.getPath();
        String albumPicturePath = album.getAlbumPicture();

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/details";
        }

        List<MultipartFile> files = albumBindingModel.getPictures();

        AlbumController albumController = new AlbumController();

        albumController.editImage(imageAlbum, album, albumPicturePath, files);

        albumController.deleteFile(originalNameAndFolder);

        this.imageRepository.saveAndFlush(imageAlbum);

        return "redirect:/admin/details";
    }

    @GetMapping("/deletePicture/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deletePicture(@PathVariable Integer id, Model model) {
        if (!this.imageRepository.exists(id)) {
            return "redirect:/admin/details";
        }

        ImageAlbum image = this.imageRepository.findOne(id);
        Album album = image.getAlbum();

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/details";
        }

        model.addAttribute("view", "admin/album/deletePicture");
        model.addAttribute("image", image);
        model.addAttribute("album", album);

        return "base-layout";
    }

    @PostMapping("/deletePicture/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deletePictureProcess(@PathVariable Integer id, AlbumBindingModel albumBindingModel) {
        if (!this.imageRepository.exists(id)) {
            return "redirect:/admin/details";
        }

        ImageAlbum image = this.imageRepository.findOne(id);
        Album album = image.getAlbum();
        String originalNameAndFolder = image.getPath();

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/details";
        }

        AlbumController albumController = new AlbumController();

        albumController.deleteFile(originalNameAndFolder);

        this.imageRepository.delete(image);

        return "redirect:/album/" + album.getId();
    }
}