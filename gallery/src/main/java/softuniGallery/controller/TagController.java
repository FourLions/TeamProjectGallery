package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuniGallery.entity.AlbumTag;
import softuniGallery.entity.LinkTag;
import softuniGallery.entity.Tag;
import softuniGallery.repository.AlbumTagRepository;
import softuniGallery.repository.LinkTagRepository;
import softuniGallery.repository.TagRepository;

@Controller
public class TagController {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LinkTagRepository linkTagRepository;
    @Autowired
    private AlbumTagRepository albumTagRepository;

    @GetMapping("/tag/{name}")
    public String articleWithTag(Model model, @PathVariable String name){
        Tag tag = this.tagRepository.findByName(name);
        if(tag==null){
            return "redirect:/";
        }
        model.addAttribute("view", "tag/articles");
        model.addAttribute("tag", tag);
        return "base-layout";
    }

    @GetMapping("/tagLink/{name}")
    public String linksWithTag(Model model, @PathVariable String name) {
        LinkTag linkTag = this.linkTagRepository.findByName(name);

        if (linkTag == null) {
            return "redirect:/";
        }

        model.addAttribute("view", "tag/links");
        model.addAttribute("tag", linkTag);

        return "base-layout";
    }

    @GetMapping("/tagAlbum/{name}")
    public String albumsWithTag(Model model, @PathVariable String name) {
        AlbumTag albumTag = this.albumTagRepository.findByName(name);

        if (albumTag == null) {
            return "redirect:/";
        }

        model.addAttribute("view", "tag/albums");
        model.addAttribute("tag", albumTag);

        return "base-layout";
    }
}
