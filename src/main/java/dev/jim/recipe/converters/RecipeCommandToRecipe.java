package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CategoryCommandToCategory categoryConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter,
                                 IngredientCommandToIngredient ingredientConverter,
                                 CategoryCommandToCategory categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }


    @Override
    public Recipe convert(RecipeCommand cmd) {
        if (cmd == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(cmd.getId());
        recipe.setDescription(cmd.getDescription());
        recipe.setPrepTime(cmd.getPrepTime());
        recipe.setCookTime(cmd.getCookTime());
        recipe.setServings(cmd.getServings());
        recipe.setSource(cmd.getSource());
        recipe.setUrl(cmd.getUrl());
        recipe.setDirections(cmd.getDirections());
        recipe.setDifficulty(cmd.getDifficulty());

        if (cmd.getNotes() != null) {
            recipe.setNotes(notesConverter.convert(cmd.getNotes()));
        }

        if (cmd.getIngredients() != null) {
            Consumer<IngredientCommand> ingredientStreamConverter = ing ->
                    recipe.getIngredients().add(ingredientConverter.convert(ing));
            cmd.getIngredients()
                    .iterator()
                    .forEachRemaining(ingredientStreamConverter);
        }

        if (cmd.getCategories() != null) {
            Consumer<CategoryCommand> categoryStreamConverter = cat ->
                    recipe.getCategories().add(categoryConverter.convert(cat));
            cmd.getCategories()
                    .iterator()
                    .forEachRemaining(categoryStreamConverter);
        }

        return recipe;
    }
}
