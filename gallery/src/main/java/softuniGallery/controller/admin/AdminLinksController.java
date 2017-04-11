package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.entity.Link;
import softuniGallery.repository.LinkRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/links")
public class AdminLinksController {

    @Autowired
    private LinkRepository linkRepository;

    @GetMapping("/")
    public String listLinks(Model model) {
        List<Link> links = this.linkRepository.findAll();

        model.addAttribute("links", links);
        model.addAttribute("view", "admin/link/list");

        return "base-layout";
    }
}
