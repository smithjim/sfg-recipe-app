package dev.jim.recipe.controller;

import dev.jim.recipe.domain.Category;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.CategoryRepository;
import dev.jim.recipe.repository.RecipeRepository;
import dev.jim.recipe.repository.UnitOfMeasureRepository;
import dev.jim.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipes", recipeService.findAll());

        return "index";
    }

}
