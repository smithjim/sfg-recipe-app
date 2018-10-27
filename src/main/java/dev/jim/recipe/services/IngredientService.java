package dev.jim.recipe.services;

import dev.jim.recipe.commands.IngredientCommand;

public interface IngredientService {

    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);

}
