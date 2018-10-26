package dev.jim.recipe.services;

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
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> opRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(opRecipe);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);

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
}