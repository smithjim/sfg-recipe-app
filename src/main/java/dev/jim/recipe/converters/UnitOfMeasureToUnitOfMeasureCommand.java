package dev.jim.recipe.converters;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            return null;
        }

        UnitOfMeasureCommand uomC = new UnitOfMeasureCommand();
        uomC.setId(unitOfMeasure.getId());
        uomC.setDescription(unitOfMeasure.getDescription());

        return uomC;
    }
}
