package dev.jim.recipe.services;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureService {

    public Set<UnitOfMeasureCommand> findAll();

    public Optional<UnitOfMeasure> findById(Long id);

    public UnitOfMeasureCommand findCommandById(Long id);
}
