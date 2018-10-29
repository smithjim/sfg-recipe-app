package dev.jim.recipe.services;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.converters.IngredientCommandToIngredient;
import dev.jim.recipe.converters.IngredientToIngredientCommand;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;
    private final RecipeService recipeService;
    private final UnitOfMeasureService uomService;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand toIngredientCommand, IngredientCommandToIngredient toIngredient, RecipeService recipeService, UnitOfMeasureService uomService) {
        this.ingredientRepository = ingredientRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
        this.recipeService = recipeService;
        this.uomService = uomService;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Ingredient ingredient = ingredientRepository.findByIdAndRecipeId(id, recipeId).orElse(null);
        return toIngredientCommand.convert(ingredient);
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand cmd) {

        Optional<Recipe> recipeOp = recipeService.findById(cmd.getRecipeId());
        if (! recipeOp.isPresent()) {
            throw new RuntimeException("Can't find Recipe for ingredient!");
        }

        Optional<UnitOfMeasure> uom = uomService.findById(cmd.getUom().getId());
        if (! uom.isPresent()) {
            throw new RuntimeException("Invalid UOM");
        }

        Recipe recipe = recipeOp.get();

        Optional<Ingredient> ingredientOp = this.getIngredientFromRecipe(recipe, cmd.getId());
        Ingredient toSave;

        if (ingredientOp.isPresent()) {
            Ingredient update = ingredientOp.get();
            update.setAmount(cmd.getAmount());
            update.setDescription(cmd.getDescription());
            update.setUom(uom.get());
            toSave = update;
        } else {
            toSave = toIngredient.convert(cmd);
            toSave.setRecipe(recipe);
        }

        Ingredient saved = ingredientRepository.save(toSave);

        return toIngredientCommand.convert(saved);
    }

    private Optional<Ingredient> getIngredientFromRecipe(Recipe recipe, Long id) {
        return recipe.getIngredients()
                .stream()
                .filter(ing -> ing.getId().equals(id))
                .findFirst();
    }
}
