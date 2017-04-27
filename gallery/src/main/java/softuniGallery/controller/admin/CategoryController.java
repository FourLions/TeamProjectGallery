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
import softuniGallery.entity.*;
import softuniGallery.repository.*;

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
    @Autowired
    private AlbumCategoryRepository albumCategoryRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ImageRepository imageRepository;


    @GetMapping("/")
    public String list(Model model) {
        List<Category> categories = this.categoryRepository.findAll();
        List<LinkCategory> linkCategories = this.linkCategoryRepository.findAll();
        List<AlbumCategory> albumCategories = this.albumCategoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("linkCategories", linkCategories);
        model.addAttribute("albumCategories", albumCategories);
        model.addAttribute("view", "admin/category/list");


        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        linkCategories = linkCategories.stream()
                .sorted(Comparator.comparingInt(LinkCategory::getId))
                .collect(Collectors.toList());

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel) {
        if (StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/categories/create";
        }
        Category category = new Category(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);
        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");
        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) {
        if (!this.categoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findOne(id);
        category.setName(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.categoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }
        Category category = this.categoryRepository.findOne(id);

        for (Article article : category.getArticles()) {
            this.articleRepository.delete(article);
        }
        this.categoryRepository.delete(category);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/createLink")
    public String createLink(Model model) {
        model.addAttribute("view", "admin/category/createLink");
        return "base-layout";
    }

    @PostMapping("/createLink")
    public String createLinkProcess(CategoryBindingModel categoryBindingModel) {

        if (StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/categories/createLink";
        }

        LinkCategory linkCategory = new LinkCategory(categoryBindingModel.getName());

        this.linkCategoryRepository.saveAndFlush(linkCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/editLink/{id}")
    public String editLink(Model model, @PathVariable Integer id) {

        if (!this.linkCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        model.addAttribute("linkCategory", linkCategory);
        model.addAttribute("view", "admin/category/editLink");

        return "base-layout";
    }

    @PostMapping("/editLink/{id}")
    public String editLinkProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) {

        if (!this.linkCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        linkCategory.setName(categoryBindingModel.getName());

        this.linkCategoryRepository.saveAndFlush(linkCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/deleteLink/{id}")
    public String deleteLink(Model model, @PathVariable Integer id) {

        if (!this.linkCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        model.addAttribute("linkCategory", linkCategory);
        model.addAttribute("view", "admin/category/deleteLink");

        return "base-layout";
    }

    @PostMapping("/deleteLink/{id}")
    public String deleteLinkProcess(@PathVariable Integer id) {

        if (!this.linkCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        LinkCategory linkCategory = this.linkCategoryRepository.findOne(id);

        for (Link link : linkCategory.getLinks()) {
            this.linkRepository.delete(link);
        }

        this.linkCategoryRepository.delete(linkCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/createAlbumCategory")
    public String createAlbumCategory(Model model) {
        model.addAttribute("view", "admin/category/createAlbumCategory");
        return "base-layout";
    }

    @PostMapping("/createAlbumCategory")
    public String createAlbumCategoryProccess(CategoryBindingModel categoryBindingModel) {

        if (StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/categories/createAlbumCategory";
        }

        AlbumCategory albumCategory = new AlbumCategory(categoryBindingModel.getName());

        this.albumCategoryRepository.saveAndFlush(albumCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/editAlbumCategory/{id}")
    public String editAlbumCategory(Model model, @PathVariable Integer id) {

        if (!this.albumCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        AlbumCategory albumCategory = this.albumCategoryRepository.findOne(id);

        model.addAttribute("albumCategory", albumCategory);
        model.addAttribute("view", "admin/category/editAlbumCategory");

        return "base-layout";
    }

    @PostMapping("/editAlbumCategory/{id}")
    public String editAlbumCategoryProccess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) {

        if (!this.albumCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        AlbumCategory albumCategory = this.albumCategoryRepository.findOne(id);

        albumCategory.setName(categoryBindingModel.getName());

        this.albumCategoryRepository.saveAndFlush(albumCategory);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/deleteAlbumCategory/{id}")
    public String deleteAlbum(Model model, @PathVariable Integer id) {

        if (!this.albumCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        AlbumCategory albumCategory = this.albumCategoryRepository.findOne(id);

        model.addAttribute("albumCategory", albumCategory);
        model.addAttribute("view", "admin/category/deleteAlbumCategory");

        return "base-layout";
    }

    @PostMapping("/deleteAlbumCategory/{id}")
    public String deleteAlbumProccess(@PathVariable Integer id) {

        if (!this.albumCategoryRepository.exists(id)) {
            return "redirect:/admin/categories/";
        }

        AlbumCategory albumCategory = this.albumCategoryRepository.findOne(id);

        for (Album currentAlbum : albumCategory.getAlbums()) {
            for (ImageAlbum currentImage : currentAlbum.getImageAlbums()) {
                this.imageRepository.delete(currentImage);
            }

            this.albumRepository.delete(currentAlbum);
        }

        this.albumCategoryRepository.delete(albumCategory);

        return "redirect:/admin/categories/";
    }

}
