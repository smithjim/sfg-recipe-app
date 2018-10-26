package dev.jim.recipe.services;

import dev.jim.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> findAll();

    public Recipe findById(long l);
}
