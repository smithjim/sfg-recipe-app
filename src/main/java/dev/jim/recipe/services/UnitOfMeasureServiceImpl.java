package dev.jim.recipe.services;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomToCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository repository, UnitOfMeasureToUnitOfMeasureCommand uomToCommand) {
        this.repository = repository;
        this.uomToCommand = uomToCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Iterable<UnitOfMeasure> iter = repository.findAll();
        Stream<UnitOfMeasure> stream = StreamSupport.stream(iter.spliterator(), false);
        return stream.map(uomToCommand::convert).collect(Collectors.toSet());
    }
}
