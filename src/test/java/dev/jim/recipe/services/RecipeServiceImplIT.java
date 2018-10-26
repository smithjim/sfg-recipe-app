package dev.jim.recipe.services;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.converters.RecipeCommandToRecipe;
import dev.jim.recipe.converters.RecipeToRecipeCommand;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplIT {

    public static final String NEW_DESCRIPTION = "new description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        recipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand saveCmd = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(recipeCommand.getId(), saveCmd.getId());
        assertEquals(NEW_DESCRIPTION, saveCmd.getDescription());
        assertEquals(recipeCommand.getCategories().size(), saveCmd.getCategories().size());
        assertEquals(recipeCommand.getIngredients().size(), saveCmd.getIngredients().size());

    }



}
