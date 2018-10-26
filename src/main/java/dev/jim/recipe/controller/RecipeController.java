package dev.jim.recipe.controller;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    public static final String BASE_URL = "/recipe";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(RecipeController.BASE_URL+"/{id}/show")
    public String showRecipeById(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @RequestMapping(RecipeController.BASE_URL + "/{id}/update")
    public String getUpdateRecipe(Model model, @PathVariable Long id) {
        RecipeCommand recipe = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/recipeform";
    }

    @RequestMapping(RecipeController.BASE_URL + "/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping(BASE_URL)//, method = RequestMethod.POST) old method
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipe) {
        RecipeCommand saved = recipeService.saveRecipeCommand(recipe);
        return "redirect:/recipe/" + saved.getId() + "/show";
    }

}
