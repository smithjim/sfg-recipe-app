package dev.jim.recipe.services;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.converters.RecipeCommandToRecipe;
import dev.jim.recipe.converters.RecipeToRecipeCommand;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;


    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public Set<Recipe> findAll() {
        log.debug("I'm in the RecipeServiceIMple");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }

    @Override
    public Recipe findById(long l) {
        return recipeRepository.findById(l).orElse(null);
    }

    @Override
    public RecipeCommand findCommandById(long l) {
        Recipe r = this.findById(l);
        return recipeToRecipeCommand.convert(r);
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe r = recipeCommandToRecipe.convert(recipeCommand);

        r = recipeRepository.save(r);

        return recipeToRecipeCommand.convert(r);
    }
}
