package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Override
    public CategoryCommand convert(Category category) {
        if (category == null) {
            return null;
        }

        CategoryCommand cc = new CategoryCommand();
        cc.setId(category.getId());
        cc.setDescription(category.getDescription());

        return cc;
    }
}
