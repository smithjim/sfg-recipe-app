package dev.jim.recipe.services;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.converters.IngredientToIngredientCommand;
import dev.jim.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    private static final Long ID = 1L;
    private static final Long RECIPE_ID = 2L;

    @Mock
    IngredientRepository ingredientRepositoryMock;

    IngredientServiceImpl service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new IngredientServiceImpl(ingredientRepositoryMock,
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    public void testFindByRecipeIdAndId() {
        //given
        Recipe r = new Recipe();
        r.setId(RECIPE_ID);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        r.addIngredient(ingredient);
        Optional<Ingredient> opIngrdient = Optional.of(ingredient);

        //when
        when(ingredientRepositoryMock.findByIdAndRecipeId(anyLong(), anyLong())).thenReturn(opIngrdient);
        IngredientCommand val = service.findByRecipeIdAndId(RECIPE_ID, ID);

        //then
        assertNotNull(val);
        assertEquals(ID, val.getId());
        assertEquals(RECIPE_ID, val.getRecipeId());
    }

    @Test
    public void testFindByRecipeIdAndIdNull() {
        //given
        Optional<Ingredient> opIngrdient = Optional.empty();

        //when
        when(ingredientRepositoryMock.findByIdAndRecipeId(anyLong(), anyLong())).thenReturn(opIngrdient);
        IngredientCommand val = service.findByRecipeIdAndId(RECIPE_ID, ID);

        //then
        assertNull(val);

    }
}