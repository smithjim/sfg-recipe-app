package dev.jim.recipe.converters;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientCommandToIngredientTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 2L;

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void convertEmpty() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        assertNotNull(converter.convert(ingredientCommand));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(UOM_ID);

        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setAmount(AMOUNT);
        cmd.setUom(uomCmd);

        Ingredient ingredient = converter.convert(cmd);

        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertNull(ingredient.getRecipe());
    }

    @Test
    public void convertNullUom() {
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setAmount(AMOUNT);

        Ingredient ingredient = converter.convert(cmd);

        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertNull(ingredient.getUom());
        assertNull(ingredient.getRecipe());
    }
}