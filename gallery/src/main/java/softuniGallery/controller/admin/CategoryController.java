package softuniGallery.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniGallery.bindingModel.CategoryBindingModel;
import softuniGallery.entity.Article;
import softuniGallery.entity.Category;
import softuniGallery.entity.Link;
import softuniGallery.entity.LinkCategory;
import softuniGallery.repository.ArticleRepository;
import softuniGallery.repository.CategoryRepository;
import softuniGallery.repository.LinkCategoryRepository;
import softuniGallery.repository.LinkRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private LinkCategoryRepository linkCategoryRepository;

    @Autowired
    private LinkRepository linkRepository;



    @GetMapping("/")
    public String list (Model model){
        List<Category> categories = this.categoryRepository.findAll();
        List<LinkCategory> linkCategories = this.linkCategoryRepository.findAll();


        model.addAttribute("categories", categories);
        model.addAttribute("linkCategories", linkCategories);
        model.addAttribute("view","admin/category/list");


        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        linkCategories = linkCategories.stream()
                .sorted(Comparator.comparingInt(LinkCategory::getId))
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
        if(StringUtils.isEmpty(categoryBindingModel.getName())){
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

    @GetMapping("/createLink")
    public String createLink(Model model){

        model.addAttribute("view", "admin/category/createLink");

        return "base-layout";
    }

    @PostMapping("/createLink")
    public String createLinkProcess(CategoryBindingModel categoryBindingModel){

        if(StringUtils.isEmpty(categoryBindingModel.getName())){
            return "redirect:/admin/categories/createLink";
        }

        LinkCategory linkCategory = new LinkCategory(categoryBindingModel.getName());

        this.linkCategoryRepository.saveAndFlush(linkCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/editLink/{id}")
    public String editLink(Model model, @PathVariable Integer id){

        if(!this.linkCategoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        model.addAttribute("linkCategory", linkCategory);
        model.addAttribute("view", "admin/category/editLink");

        return "base-layout";
    }

    @PostMapping("/editLink/{id}")
    public String editLinkProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        if(!this.linkCategoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        linkCategory.setName(categoryBindingModel.getName());

        this.linkCategoryRepository.saveAndFlush(linkCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/deleteLink/{id}")
    public String deleteLink(Model model, @PathVariable Integer id){

        if(!this.linkCategoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        model.addAttribute("linkCategory", linkCategory);
        model.addAttribute("view", "admin/category/deleteLink");

        return "base-layout";
    }

    @PostMapping("/deleteLink/{id}")
    public String deleteLinkProcess(@PathVariable Integer id){

        if(!this.linkCategoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        for(Link link : linkCategory.getLinks()){
            this.linkRepository.delete(link);
        }

        this.linkCategoryRepository.delete(linkCategory);

        return "redirect:/admin/categories/";
    }
}
