package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuniGallery.bindingModel.LinkBindingModel;
import org.springframework.web.bind.annotation.PostMapping;
import softuniGallery.entity.Link;
import softuniGallery.repository.LinkRepository;

import java.util.List;

@Controller
public class LinkHomeController {

    @Autowired
    private LinkRepository linkRepository;

    @GetMapping("/link/viewLinks")
    public String indexLink(Model model) {

        List<Link> links = this.linkRepository.findAll();

        model.addAttribute("view", "link/indexLink");
        model.addAttribute("links", links);

        return "base-layout";
    }

    @GetMapping("/link/{id}")
    public String details(Model model, @PathVariable Integer id) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/";
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
            return "redirect:/";
        }

        Link link = this.linkRepository.findOne(id);

        model.addAttribute("view", "link/edit");
        model.addAttribute("link", link);

        return "base-layout";
    }

    @PostMapping("/link/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, LinkBindingModel linkBindingModel) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/";
        }

        Link link = this.linkRepository.findOne(id);
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

        this.linkRepository.delete(link);

        return "redirect:/link/viewLinks";
    }
}
