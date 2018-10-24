package dev.jim.recipe.services;

import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> findAll() {
        log.debug("I'm in the RecipeServiceIMple");
        Set<Recipe> set = new TreeSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }
}
