package dev.jim.recipe.converters;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeToRecipeCommandTest {

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

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {

        Notes note = new Notes();
        note.setId(NOTES_ID);

        Category cat1 = new Category();
        cat1.setId(CATEGORY_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID);

        Recipe r = new Recipe();
        r.setId(ID);
        r.setDescription(DESCRIPTION);
        r.setPrepTime(PREP_TIME);
        r.setCookTime(COOK_TIME);
        r.setServings(SERVING);
        r.setSource(SOURCE);
        r.setUrl(URL);
        r.setDirections(DIRECTIONS);
        r.setDifficulty(DIFFICULTY);
        r.getCategories().add(cat1);
        r.getIngredients().add(ingredient1);

        RecipeCommand cmd = converter.convert(r);

        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
        assertEquals(PREP_TIME, cmd.getPrepTime());
        assertEquals(COOK_TIME, cmd.getCookTime());
        assertEquals(SERVING, cmd.getServings());
        assertEquals(SOURCE, cmd.getSource());
        assertEquals(URL, cmd.getUrl());
        assertEquals(DIRECTIONS, cmd.getDirections());
        assertEquals(DIFFICULTY, cmd.getDifficulty());
        assertEquals(1, cmd.getCategories().size());
        assertEquals(1, cmd.getIngredients().size());
    }

}