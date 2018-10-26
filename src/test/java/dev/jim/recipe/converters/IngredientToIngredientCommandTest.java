package dev.jim.recipe.converters;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientToIngredientCommandTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 2L;

    IngredientToIngredientCommand converter;

    @Before
    public void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void convertEmpty() {
        Ingredient ingredient = new Ingredient();
        assertNotNull(converter.convert(ingredient));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(uom);
        ingredient.setRecipe(new Recipe());

        IngredientCommand cmd = converter.convert(ingredient);

        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
        assertEquals(AMOUNT, cmd.getAmount());
        assertEquals(UOM_ID, cmd.getUom().getId());
    }

    @Test
    public void convertNullRecipe() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(uom);

        IngredientCommand cmd = converter.convert(ingredient);

        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
        assertEquals(AMOUNT, cmd.getAmount());
        assertEquals(UOM_ID, cmd.getUom().getId());
    }

    @Test
    public void convertNullUom() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(new Recipe());

        IngredientCommand cmd = converter.convert(ingredient);

        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
        assertEquals(AMOUNT, cmd.getAmount());
        assertNull(cmd.getUom());
    }
}