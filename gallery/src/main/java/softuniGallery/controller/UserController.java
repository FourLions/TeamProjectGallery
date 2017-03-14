package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import softuniGallery.bindingModel.UserBindingModel;
import softuniGallery.entity.Role;
import softuniGallery.entity.User;
import softuniGallery.repository.RoleRepository;
import softuniGallery.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");
        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel){
        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
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
}
