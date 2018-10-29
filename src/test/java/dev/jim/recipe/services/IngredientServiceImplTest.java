package dev.jim.recipe.services;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.converters.IngredientCommandToIngredient;
import dev.jim.recipe.converters.IngredientToIngredientCommand;
import dev.jim.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.jim.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private static final Long ID = 1L;
    private static final Long RECIPE_ID = 2L;
    private static final Long UOM_ID = 3L;
    private static final String DESCRIPTION = "description";

    @Mock
    IngredientRepository ingredientRepositoryMock;

    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService uomServiceMock;

    IngredientServiceImpl service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new IngredientServiceImpl(ingredientRepositoryMock,
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                recipeService,
                uomServiceMock);
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

    @Test
    public void testSaveIngredientCommandNew() {
        //given
        //in
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(UOM_ID);
        IngredientCommand cmd = new IngredientCommand();
        cmd.setDescription(DESCRIPTION);
        cmd.setUom(uomCmd);
        cmd.setRecipeId(RECIPE_ID);

        //saved
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);
        UnitOfMeasure savedUom = new UnitOfMeasure();
        savedUom.setId(UOM_ID);

        //new save state
        Ingredient saved = new Ingredient();
        saved.setId(ID);
        saved.setDescription(DESCRIPTION);
        saved.setUom(savedUom);
        saved.setRecipe(savedRecipe);

        //when
        when(recipeService.findById(anyLong())).thenReturn(Optional.of(savedRecipe));
        when(uomServiceMock.findById(anyLong())).thenReturn(Optional.of(savedUom));
        when(ingredientRepositoryMock.save(any(Ingredient.class))).thenReturn(saved);

        IngredientCommand savedCmd = service.saveIngredientCommand(cmd);

        //then
        assertEquals(ID, saved.getId());
        assertEquals(RECIPE_ID, savedCmd.getRecipeId());
        assertEquals(DESCRIPTION, saved.getDescription());

        ArgumentCaptor<Long> lookupCaptor = ArgumentCaptor.forClass(Long.class);
        verify(recipeService, times(1)).findById(lookupCaptor.capture());
        assertEquals(RECIPE_ID, lookupCaptor.getValue());

        verify(ingredientRepositoryMock, times(1)).save(any(Ingredient.class));

    }

    @Test
    public void testSaveIngredientCommandNoRecipe() {
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);

        when(recipeService.findById(anyLong())).thenReturn(Optional.empty());

        try {
            service.saveIngredientCommand(cmd);
            fail();
        } catch (RuntimeException e){

        }

    }

    @Test
    public void testSaveIngredientCommandInvalidUom() {
        //given
        //in
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(UOM_ID);
        IngredientCommand cmd = new IngredientCommand();
        cmd.setUom(uomCmd);
        cmd.setRecipeId(RECIPE_ID);

        //when
        when(recipeService.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(uomServiceMock.findById(anyLong())).thenReturn(Optional.empty());

        try {
            service.saveIngredientCommand(cmd);
            fail("Should've thrown exception for invalid UnitOfMeasure");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void testSaveIngredientCommandUpdate() {
        //given
        //input
        String oldDescription = "old Description";
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(UOM_ID);
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setRecipeId(RECIPE_ID);
        cmd.setUom(uomCmd);

        //saved
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);
        UnitOfMeasure savedUom = new UnitOfMeasure();
        savedUom.setId(UOM_ID);
        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(ID);
        savedIngredient.setDescription(oldDescription);
        savedRecipe.addIngredient(savedIngredient);

        //new save state
        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setId(ID);
        updatedIngredient.setDescription(DESCRIPTION);
        updatedIngredient.setUom(savedUom);
        updatedIngredient.setRecipe(savedRecipe);;

        //when
        when(recipeService.findById(anyLong())).thenReturn(Optional.of(savedRecipe));
        when(uomServiceMock.findById(anyLong())).thenReturn(Optional.of(savedUom));
        when(ingredientRepositoryMock.save(any(Ingredient.class))).thenReturn(updatedIngredient);

        IngredientCommand saved = service.saveIngredientCommand(cmd);

        //then
        assertEquals(ID, saved.getId());
        assertEquals(RECIPE_ID, saved.getRecipeId());
        assertEquals(DESCRIPTION, saved.getDescription());

        ArgumentCaptor<Long> lookupCaptor = ArgumentCaptor.forClass(Long.class);
        verify(recipeService, times(1)).findById(lookupCaptor.capture());
        assertEquals(RECIPE_ID, lookupCaptor.getValue());

        verify(ingredientRepositoryMock, times(1)).save(any(Ingredient.class));
    }
}