package softuniGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.entity.Article;
import softuniGallery.entity.Category;
import softuniGallery.entity.User;
import softuniGallery.repository.CategoryRepository;
import softuniGallery.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class GalleryHomeController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<Category> categories = this.categoryRepository.findAll();

        List<User> latestFiveUsers = this.userRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("latestFiveUsers", latestFiveUsers);
        model.addAttribute("view", "home/index");
        model.addAttribute("categories", categories);

        return "base-layout";
    }
    @GetMapping("/category/{id}")
    public String listArticles(Model model, @PathVariable Integer id){
        model.addAttribute("view", "home/list-articles");
        if(!this.categoryRepository.exists(id)){
            return "redirect:/";
        }

        Category category = this.categoryRepository.findOne(id);
        Set<Article> articles = category.getArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("category", category);
        return "base-layout";
    }

    @RequestMapping("/error/403")
    public String accessDenied(Model model) {
        model.addAttribute("view", "error/403");

        return "base-layout";
    }

    @RequestMapping("/error/404")
    public String notFound(Model model) {
        model.addAttribute("view", "error/404");

        return "base-layout";
    }
}
