package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.commands.NotesCommand;
import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Difficulty;
import dev.jim.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeCommandToRecipeTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "DESC";
    private static final Integer PREP_TIME = 1;
    private static final Integer COOK_TIME = 2;
    private static final Integer SERVING = 4;
    private static final String SOURCE = "SOURCE";
    private static final String URL = "URL";
    private static final String DIRECTIONS = "DIRECTIONS";
    private static final Difficulty DIFFICULTY = Difficulty.MODERATE;
    private static final Long NOTES_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;
    private static final Long CATEGORY_ID = 1L;

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), new CategoryCommandToCategory());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {

        NotesCommand note = new NotesCommand();
        note.setId(NOTES_ID);

        CategoryCommand cat1 = new CategoryCommand();
        cat1.setId(CATEGORY_ID);

        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_ID);

        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setPrepTime(PREP_TIME);
        cmd.setCookTime(COOK_TIME);
        cmd.setServings(SERVING);
        cmd.setSource(SOURCE);
        cmd.setUrl(URL);
        cmd.setDirections(DIRECTIONS);
        cmd.setDifficulty(DIFFICULTY);
        cmd.getCategories().add(cat1);
        cmd.getIngredients().add(ingredient1);

        Recipe r = converter.convert(cmd);

        assertEquals(ID, r.getId());
        assertEquals(DESCRIPTION, r.getDescription());
        assertEquals(PREP_TIME, r.getPrepTime());
        assertEquals(COOK_TIME, r.getCookTime());
        assertEquals(SERVING, r.getServings());
        assertEquals(SOURCE, r.getSource());
        assertEquals(URL, r.getUrl());
        assertEquals(DIRECTIONS, r.getDirections());
        assertEquals(DIFFICULTY, r.getDifficulty());
        assertEquals(1, r.getCategories().size());
        assertEquals(1, r.getIngredients().size());
    }
}