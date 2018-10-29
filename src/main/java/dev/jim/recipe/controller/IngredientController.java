package dev.jim.recipe.controller;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.services.IngredientService;
import dev.jim.recipe.services.RecipeService;
import dev.jim.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @RequestMapping("/recipe/{id}/ingredient")
    public String getListIngredients(Model model, @PathVariable Long id) {

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String getShowIngredient(Model model, @PathVariable Long recipeId, @PathVariable Long id) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        return "recipe/ingredient/show";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String getUpdateIngrdient(Model model, @PathVariable Long recipeId, @PathVariable Long id) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        model.addAttribute("unitMeasures", uomService.findAll());
        return "recipe/ingredient/update";
    }

    @PostMapping
    @RequestMapping("/recipe/ingredient")
    public String postUpdateIngredient(@ModelAttribute IngredientCommand cmd) {
        ingredientService.saveIngredientCommand(cmd);

        return "redirect:/recipe/"+cmd.getRecipeId()+"/ingredient";
    }


}
