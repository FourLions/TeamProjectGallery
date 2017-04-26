package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import softuniGallery.bindingModel.UserBindingModel;
import softuniGallery.bindingModel.UserEditBindingModel;
import softuniGallery.bindingModel.UserInfoBindingModel;
import softuniGallery.entity.*;
import softuniGallery.repository.RoleRepository;
import softuniGallery.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        response.addCookie(new Cookie("RememberMe", "loggedOut"));

        return "redirect:/login?logout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");
        return "base-layout";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel) {
        if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);

        this.userRepository.saveAndFlush(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());
        Set<Album> albums = user.getAlbums();
        Set<Link> links = user.getLinks();
        Set<Article> articles = user.getArticles();

        model.addAttribute("user", user);
        model.addAttribute("albums", albums);
        model.addAttribute("links", links);
        model.addAttribute("articles", articles);
        model.addAttribute("view", "user/profile");
        return "base-layout";
    }

    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/";
        }

        User user = this.userRepository.findOne(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "user/editProfile");

        return "base-layout";
    }


    @PostMapping("/profile/edit/{id}")
    public String editProfileProcess(@PathVariable Integer id,
                                     UserEditBindingModel userBindingModel) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/profile";
        }

        User user = this.userRepository.findOne(id);

        if (!StringUtils.isEmpty(userBindingModel.getPassword())
                && !StringUtils.isEmpty(userBindingModel.getConfirmPassword())) {

            if (userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
            }
        }

        String redirectLink = null;

        if (user.getEmail().equals(userBindingModel.getEmail())) {
            redirectLink = "redirect:/profile";
        }
        else {
            redirectLink = "redirect:/login?logout";
        }

        user.setFullName(userBindingModel.getFullName());
        user.setEmail(userBindingModel.getEmail());

        this.userRepository.saveAndFlush(user);

        return redirectLink;
    }

    @GetMapping("/profile/info/{id}")
    public String editInfo(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/profile";
        }

        User user = this.userRepository.findOne(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "user/info");

        return "base-layout";
    }

    @PostMapping("/profile/info/{id}")
    public String editInfoProcess(@PathVariable Integer id, UserInfoBindingModel userInfoBindingModel) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/profile";
        }

        User user = this.userRepository.findOne(id);

        user.setTown(userInfoBindingModel.getTown());
        user.setCountry(userInfoBindingModel.getCountry());
        user.setTelephoneNumber(userInfoBindingModel.getTelephoneNumber());
        user.setInformation(userInfoBindingModel.getInformation());
        String originalNameAndFolder = user.getProfilePicture();

        MultipartFile file = userInfoBindingModel.getProfilePicture();

        uploadFile(user, file);

        AlbumController albumController = new AlbumController();

        albumController.deleteFile(originalNameAndFolder);

        this.userRepository.saveAndFlush(user);

        return "redirect:/profile";
    }

    @GetMapping("/profileInfo/{id}")
    public String profileInfo(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/error/404";
        }

        User user = this.userRepository.findOne(id);
        Set<Album> albums = user.getAlbums();
        Set<Link> links = user.getLinks();
        Set<Article> articles = user.getArticles();

        model.addAttribute("user", user);
        model.addAttribute("albums", albums);
        model.addAttribute("links", links);
        model.addAttribute("articles", articles);
        model.addAttribute("view", "user/profileInfo");

        return "base-layout";
    }

    public void uploadFile(User user, MultipartFile file) {
        if (file != null) {
            String originalName = file.getOriginalFilename();
            File imageFile = new File("C:\\Users\\George-Lenovo\\Desktop\\TeamProjectGallery\\gallery\\src\\main\\resources\\static\\images", originalName);
            try {
                file.transferTo(imageFile);
                user.setProfilePicture("/images/" + originalName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}