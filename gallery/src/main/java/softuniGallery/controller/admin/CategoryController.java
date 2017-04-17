package softuniGallery.controller.admin;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.bindingModel.CategoryBindingModel;
import softuniGallery.entity.Article;
import softuniGallery.entity.Category;
import softuniGallery.repository.ArticleRepository;
import softuniGallery.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minko Vasilev on 4/4/2017.
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;



    @GetMapping("/")
    public String list (Model model){
        List<Category> categories = this.categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("view","admin/category/list");


        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        return "base-layout";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel){
        if(org.springframework.util.StringUtils.isEmpty(categoryBindingModel.getName())){
            return "redirect:/admin/categories/create";
        }
        Category category = new Category(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){
        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);
        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");
        return "base-layout";
    }
    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){
        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findOne(id);
        category.setName(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id){
        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }
    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id){
        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);

        for(Article article : category.getArticles()){
            this.articleRepository.delete(article);
        }
        this.categoryRepository.delete(category);
        return "redirect:/admin/categories/";
    }
}
