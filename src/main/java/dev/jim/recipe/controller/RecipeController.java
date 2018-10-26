package dev.jim.recipe.controller;

import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    public static final String BASE_URL = "/recipe/show";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(RecipeController.BASE_URL+"/{id}")
    public String showRecipeById(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

}
