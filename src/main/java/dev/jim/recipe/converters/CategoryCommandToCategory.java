package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }

        Category cat = new Category();
        cat.setId(categoryCommand.getId());
        cat.setDescription(categoryCommand.getDescription());

        return cat;
    }
}
