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

        uploadFiles(albumEntity, files, listImages);

        this.albumRepository.saveAndFlush(albumEntity);
        return "redirect:/album/viewAlbums";
    }

    private void uploadFiles(Album albumEntity, List<MultipartFile> files, List<String> listImages) {
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
    }

    @GetMapping("/album/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/album/viewAlbums";
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

    @GetMapping("/album/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/album/viewAlbums";
        }

        Album album = this.albumRepository.findOne(id);

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/" + id;
        }

        model.addAttribute("view", "album/edit");
        model.addAttribute("album", album);

        return "base-layout";
    }

    @PostMapping("/album/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, AlbumBindingModel albumBindingModel) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/album/viewAlbums";
        }

        Album album = this.albumRepository.findOne(id);

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/" + id;
        }

        album.setName(albumBindingModel.getName());

        List<String> imagesPath = album.getImagePathList();

        deleteFiles(imagesPath);

        List<MultipartFile> files = albumBindingModel.getPictures();
        List<String> listImages = new LinkedList<>();

        uploadFiles(album, files, listImages);

        this.albumRepository.saveAndFlush(album);

        return "redirect:/album/" + album.getId();
    }

    private void deleteFiles(List<String> imagesPath) {
        if (imagesPath != null && imagesPath.size() > 0) {

            for (int i = 0; i < imagesPath.size(); i++) {

                if (imagesPath.get(i) != null) {
                    String originalName = imagesPath.get(i);

                    try {
                        File imageFile = new File("C:\\Users\\User\\IdeaProjects\\TeamProjectGallery\\gallery\\src\\main\\resources\\static" + originalName);
                        if (imageFile.delete()) {
                            System.out.println(imageFile.getName() + " is deleted!");
                        } else {
                            System.out.println("Delete operation is failed!");
                        }
                    } catch (Exception ex) {
                        System.out.println("Failed to delete image!");
                    }
                }
            }
        }
    }

    @GetMapping("/album/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/album/viewAlbums";
        }

        Album album = this.albumRepository.findOne(id);

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album/" + id;
        }

        model.addAttribute("album", album);
        model.addAttribute("view", "album/delete");

        return "base-layout";
    }

    @PostMapping("/album/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id, AlbumBindingModel albumBindingModel) {
        if (!this.albumRepository.exists(id)) {
            return "redirect:/album/viewAlbums";
        }

        Album album = this.albumRepository.findOne(id);

        if (!isUserAuthorOrAdmin(album)) {
            return "redirect:/album" + id;
        }

        List<String> imagesPath = album.getImagePathList();

        deleteFiles(imagesPath);

        this.albumRepository.delete(album);

        return "redirect:/album/viewAlbums";
    }

    private boolean isUserAuthorOrAdmin(Album album) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(album);
    }
}
