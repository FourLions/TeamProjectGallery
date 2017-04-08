package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import softuniGallery.bindingModel.LinkBindingModel;
import softuniGallery.entity.Link;
import softuniGallery.entity.User;
import softuniGallery.repository.LinkRepository;
import softuniGallery.repository.UserRepository;

@Controller
public class LinkController {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/link/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        model.addAttribute("view", "link/create");

        return "base-layout";
    }

    @PostMapping("/link/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(LinkBindingModel linkBindingModel) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        Link linkEntity = new Link(
                linkBindingModel.getLink()/* + "/embed"*/,
                linkBindingModel.getContent(),
                userEntity
        );

        this.linkRepository.saveAndFlush(linkEntity);

        return "redirect:/link/viewLinks";
    }
}
