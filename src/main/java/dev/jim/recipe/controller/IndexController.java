package dev.jim.recipe.controller;

import dev.jim.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipes", recipeService.findAll());

        log.debug("Add recipe data to index page model");

        return "index";
    }

}
