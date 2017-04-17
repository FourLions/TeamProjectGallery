package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import softuniGallery.bindingModel.AlbumBindingModel;
import softuniGallery.entity.Album;
import softuniGallery.entity.User;
import softuniGallery.repository.AlbumRepository;
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

    @GetMapping("/")
    public String listAlbums(Model model){
        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("albums", albums);
        model.addAttribute("view", "admin/album/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.albumRepository.exists(id)) {
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
        int userId = album.getAuthor().getId();

        album.setName(albumBindingModel.getName());

        List<String> imagesPath = album.getImagePathList();

        List<MultipartFile> files = albumBindingModel.getPictures();
        List<String> listImages = new LinkedList<>();

        uploadFiles(album, files, listImages);


        if (imagesPath != null && imagesPath.size() > 0) {

            for (int i = 0; i < imagesPath.size(); i++) {

                if (imagesPath.get(i) != null) {
                    String originalName = imagesPath.get(i);

                    if (!listImages.contains(originalName)) {
                        deleteFiles(originalName);
                    }
                }
            }
        }

        this.albumRepository.saveAndFlush(album);

        return "redirect:/admin/users/userAlbums/" + userId;
    }

    private void deleteFiles(String originalName) {
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

    private void uploadFiles(Album albumEntity, List<MultipartFile> files, List<String> listImages) {
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                boolean setAlbumPicture = false;
                if (i == 0) {
                    setAlbumPicture = true;
                }
                if (files.get(i) != null) {
                    String originalName = files.get(i).getOriginalFilename();
                    File imageFile = new File("C:\\Users\\User\\IdeaProjects\\TeamProjectGallery\\gallery\\src\\main\\resources\\static\\images", originalName);
                    try {
                        files.get(i).transferTo(imageFile);
                        String image = "/images/" + originalName;
                        listImages.add(image);
                        albumEntity.setImagePathList(listImages);
                        if (setAlbumPicture) {
                            albumEntity.setAlbumPicture(image);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

        List<String> imagesPath = album.getImagePathList();

        if (imagesPath != null && imagesPath.size() > 0) {

            for (int i = 0; i < imagesPath.size(); i++) {

                if (imagesPath.get(i) != null) {
                    String originalName = imagesPath.get(i);
                    deleteFiles(originalName);
                }
            }
        }

        this.albumRepository.delete(album);

        return  "redirect:/admin/albums/";
    }

}