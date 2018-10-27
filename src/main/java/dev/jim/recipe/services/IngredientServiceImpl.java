package dev.jim.recipe.services;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.converters.IngredientToIngredientCommand;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand toIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand toIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.toIngredientCommand = toIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Ingredient ingredient = ingredientRepository.findByIdAndRecipeId(id, recipeId).orElse(null);
        return toIngredientCommand.convert(ingredient);
    }
}
