package dev.jim.recipe.services;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
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

    @Override
    public Optional<UnitOfMeasure> findById(Long id) {
        return repository.findById(id);
    }

    public UnitOfMeasureCommand findCommandById(Long id) {
        Optional<UnitOfMeasure> uomOp = this.findById(id);
        return uomToCommand.convert(uomOp.orElse(null));
    }
}
