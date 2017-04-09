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
}
