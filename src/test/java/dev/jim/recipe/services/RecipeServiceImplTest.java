package dev.jim.recipe.services;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.converters.*;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,
                new RecipeCommandToRecipe(new NotesCommandToNotes(),
                        new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                        new CategoryCommandToCategory()),
                new RecipeToRecipeCommand(new NotesToNotesCommand(),
                        new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                        new CategoryToCategoryCommand()));
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> opRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(opRecipe);

        Optional<Recipe> recipeReturned = recipeService.findById(1L);

        assertTrue(recipeReturned.isPresent());
        assertNotNull(recipeReturned.orElse(null));

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        Optional<Recipe> opRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(opRecipe);

        RecipeCommand recipeReturned = recipeService.findCommandById(ID);

        assertNotNull(recipeReturned);
        assertEquals(ID, recipeReturned.getId());

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void findAll() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeService.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.findAll();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findAllNone() {

        HashSet<Recipe> recipesData = new HashSet<>();
        when(recipeService.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.findAll();

        assertEquals(recipes.size(), 0);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void saveNewRecipeCommand() {

        Recipe returnRecipe = new Recipe();
        returnRecipe.setId(1L);
        returnRecipe.setDescription(DESCRIPTION);

        RecipeCommand recipeCmd = new RecipeCommand();
        recipeCmd.setDescription(DESCRIPTION);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(returnRecipe);

        RecipeCommand returned = recipeService.saveRecipeCommand(recipeCmd);

        assertEquals(returnRecipe.getId(), returned.getId());
        assertEquals(DESCRIPTION, returned.getDescription());

        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void saveOldRecipeCommand() {

        Recipe returnRecipe = new Recipe();
        returnRecipe.setId(3L);
        returnRecipe.setDescription(DESCRIPTION);

        RecipeCommand recipeCmd = new RecipeCommand();
        recipeCmd.setId(returnRecipe.getId());
        recipeCmd.setDescription(DESCRIPTION);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(returnRecipe);

        RecipeCommand returned = recipeService.saveRecipeCommand(recipeCmd);

        assertEquals(returnRecipe.getId(), returned.getId());
        assertEquals(DESCRIPTION, returned.getDescription());

        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        //given
        Long idDelete = 2L;

        //when
        recipeService.deleteById(idDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());

    }
}