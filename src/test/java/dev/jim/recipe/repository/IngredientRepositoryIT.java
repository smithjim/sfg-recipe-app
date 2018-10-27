package dev.jim.recipe.repository;

import dev.jim.recipe.domain.Ingredient;
import dev.jim.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientRepositoryIT {

    @Autowired
    IngredientRepository repository;

    @Autowired
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Transactional
    @Test
    public void testFindByIdAndRecipeId() {
        //given
        Long id = 1L;
        Long recipeId = 1L;
        Ingredient toSave = new Ingredient();
        toSave.setId(id);
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(toSave);

        recipe = recipeRepository.save(recipe);
        toSave = recipe.getIngredients().iterator().next();

        //when
        Optional<Ingredient> opIngredient = repository.findByIdAndRecipeId(id, recipeId);
        Ingredient ingredient = opIngredient.orElse(null);

        //then
        assertNotNull(ingredient);
        assertEquals(id, ingredient.getId());
        assertEquals(recipeId, ingredient.getRecipe().getId());
    }
}