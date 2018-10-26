package dev.jim.recipe.services;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> findAll();

    public Recipe findById(long l);

    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
