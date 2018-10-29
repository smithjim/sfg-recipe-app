package dev.jim.recipe.services;

import dev.jim.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    public Set<UnitOfMeasureCommand> findAll();
}
