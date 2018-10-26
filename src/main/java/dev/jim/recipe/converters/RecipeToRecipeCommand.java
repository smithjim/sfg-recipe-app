package dev.jim.recipe.converters;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Category;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand noteConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand noteConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 CategoryToCategoryCommand categoryConverter) {
        this.noteConverter = noteConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }


    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(recipe.getId());
        cmd.setDescription(recipe.getDescription());
        cmd.setPrepTime(recipe.getPrepTime());
        cmd.setCookTime(recipe.getCookTime());
        cmd.setServings(recipe.getServings());
        cmd.setSource(recipe.getSource());
        cmd.setUrl(recipe.getUrl());
        cmd.setDirections(recipe.getDirections());
        cmd.setDifficulty(recipe.getDifficulty());
        cmd.setNotes(noteConverter.convert(recipe.getNotes()));

        if (recipe.getIngredients() != null) {
            Consumer<Ingredient> ingredientStreamConverter = ing ->
                    cmd.getIngredients().add(ingredientConverter.convert(ing));
            recipe.getIngredients()
                    .iterator()
                    .forEachRemaining(ingredientStreamConverter);
        }

        if (recipe.getCategories() != null) {
            Consumer<Category> categoryStreamConverter = cat ->
                    cmd.getCategories().add(categoryConverter.convert(cat));
            recipe.getCategories()
                    .iterator()
                    .forEachRemaining(categoryStreamConverter);
        }

        return cmd;
    }
}
