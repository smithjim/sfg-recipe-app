package dev.jim.recipe.repository;

import dev.jim.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    public Optional<Ingredient> findByIdAndRecipeId(Long id, Long recipeId);
}
