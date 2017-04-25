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
import softuniGallery.bindingModel.LinkBindingModel;
import softuniGallery.entity.Link;
import softuniGallery.entity.LinkCategory;
import softuniGallery.entity.User;
import softuniGallery.repository.LinkCategoryRepository;
import softuniGallery.repository.LinkRepository;
import softuniGallery.repository.UserRepository;

import java.util.List;

@Controller
public class LinkController {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkCategoryRepository linkCategoryRepository;

    @GetMapping("/link/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {

        List<LinkCategory> linkCategories = this.linkCategoryRepository.findAll();

        model.addAttribute("linkCategories", linkCategories);
        model.addAttribute("view", "link/create");

        return "base-layout";
    }

    @PostMapping("/link/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(LinkBindingModel linkBindingModel) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        LinkCategory linkCategory = this.linkCategoryRepository.findOne(linkBindingModel.getCategoryId());

        Link linkEntity = new Link(
                linkBindingModel.getLink(),
                linkBindingModel.getContent(),
                userEntity,
                linkCategory
        );

        this.linkRepository.saveAndFlush(linkEntity);

        return "redirect:/link/viewLinks";
    }

    @GetMapping("/link/{id}")
    public String details(Model model, @PathVariable Integer id) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Link link = this.linkRepository.findOne(id);

        model.addAttribute("link", link);
        model.addAttribute("view", "link/details");

        return "base-layout";
    }

    @GetMapping("/link/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.linkRepository.exists(id)) {
            return "redirect:/link/viewLinks";
        }
        Link link = this.linkRepository.findOne(id);

        if (!isUserAuthorOrAdmin(link)) {
            return "redirect:/link/" + id;
        }

        List<LinkCategory> linkCategories = this.linkCategoryRepository.findAll();

        model.addAttribute("view", "link/edit");
        model.addAttribute("link", link);
        model.addAttribute("linkCategories", linkCategories);

        return "base-layout";
    }

    @PostMapping("/link/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, LinkBindingModel linkBindingModel) {
        if (!this.linkRepository.exists(id)) {
            return "redirect:/link/viewLinks";
        }
        Link link = this.linkRepository.findOne(id);

        if (!isUserAuthorOrAdmin(link)) {
            return "redirect:/link/" + id;
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(linkBindingModel.getCategoryId());

        link.setLinkCategory(linkCategory);
        link.setContent(linkBindingModel.getContent());
        link.setLink(linkBindingModel.getLink());

        this.linkRepository.saveAndFlush(link);

        return "redirect:/link/" + link.getId();
    }

    @GetMapping("/link/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/";
        }

        Link link = this.linkRepository.findOne(id);

        if (!isUserAuthorOrAdmin(link)) {
            return "redirect:/link/" + id;
        }

        model.addAttribute("link", link);
        model.addAttribute("view", "link/delete");

        return "base-layout";
    }

    @PostMapping("/link/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/";
        }

        Link link = this.linkRepository.findOne(id);

        if (!isUserAuthorOrAdmin(link)) {
            return "redirect:/link/" + id;
        }

        this.linkRepository.delete(link);

        return "redirect:/link/viewLinks";
    }

    private boolean isUserAuthorOrAdmin(Link link) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(link);
    }
}
