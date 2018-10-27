package dev.jim.recipe.converters;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            cmd.setRecipeId(ingredient.getRecipe().getId());
        }
        cmd.setDescription(ingredient.getDescription());
        cmd.setAmount(ingredient.getAmount());
        cmd.setUom(uomConverter.convert(ingredient.getUom()));

        return cmd;
    }
}
