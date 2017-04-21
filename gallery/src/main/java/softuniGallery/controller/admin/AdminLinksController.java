package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.bindingModel.LinkBindingModel;
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

        setLinksSummary(links);
        model.addAttribute("links", links);
        model.addAttribute("view", "admin/link/list");

        return "base-layout";
    }

    private void setLinksSummary(List<Link> links) {
        for (Link currentLink : links) {
            if (currentLink.getLink().length() <= 50) {
                currentLink.setLinkSummary(currentLink.getLink().substring(0, currentLink.getLink().length()));
            } else {
                currentLink.setLinkSummary(currentLink.getLink().substring(0, 50) + "...");

            }
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.linkRepository.exists(id)) {
            return "redirect:/admin/links";
        }

        Link link = this.linkRepository.findOne(id);

        model.addAttribute("link", link);
        model.addAttribute("view", "admin/link/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, LinkBindingModel linkBindingModel) {

        if (!this.linkRepository.exists(id)) {
            return "redirect:/admin/links/";
        }

        Link link = this.linkRepository.findOne(id);

        link.setContent(linkBindingModel.getContent());
        link.setLink(linkBindingModel.getLink());

        this.linkRepository.saveAndFlush(link);

        return "redirect:/admin/links/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        if (!this.linkRepository.exists(id)) {
            return "redirect:/admin/links/";
        }

        Link link = this.linkRepository.findOne(id);

        model.addAttribute("link", link);
        model.addAttribute("view", "admin/link/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.linkRepository.exists(id)) {
            return "redirect:/admin/links/";
        }

        Link link = this.linkRepository.findOne(id);

        this.linkRepository.delete(link);

        return "redirect:/admin/links/";
    }
}
