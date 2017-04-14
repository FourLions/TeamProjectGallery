package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.bindingModel.UserEditBindingModel;
import softuniGallery.entity.*;
import softuniGallery.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String listUsers(Model model) {
        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);
        List<Role> roles = this.roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }


    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id,
                              UserEditBindingModel userBindingModel) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User currentLoggedUser = this.userRepository.findByEmail(principal.getUsername());
        User userToEdit = this.userRepository.findOne(id);

        String redirectLink = "redirect:/admin/users/";

        if (userToEdit.getFullName().equals(currentLoggedUser.getFullName())) {
            if (userToEdit.getEmail().equals(userBindingModel.getEmail())) {
                redirectLink = "redirect:/admin/users/";
            }
            else {
                redirectLink = "redirect:/login?logout";
            }
        }

        if (!StringUtils.isEmpty(userBindingModel.getPassword())
                && !StringUtils.isEmpty(userBindingModel.getConfirmPassword())) {

            if (userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                userToEdit.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
            }
        }

        userToEdit.setFullName(userBindingModel.getFullName());
        userToEdit.setEmail(userBindingModel.getEmail());

        Set<Role> roles = new HashSet<>();

        for (Integer roleId : userBindingModel.getRoles()) {
            roles.add(this.roleRepository.findOne(roleId));
        }

        userToEdit.setRoles(roles);

        this.userRepository.saveAndFlush(userToEdit);

        return redirectLink;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);

        for (Article article : user.getArticles()) {
            this.articleRepository.delete(article);
        }

        for (Album album : user.getAlbums()) {
            this.albumRepository.delete(album);
        }

        for (Link link : user.getLinks()) {
            this.linkRepository.delete(link);
        }

        this.userRepository.delete(user);

        return "redirect:/admin/users/";
    }
}