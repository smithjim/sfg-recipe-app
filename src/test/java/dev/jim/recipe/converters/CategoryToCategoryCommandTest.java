package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

@Component
public class CategoryToCategoryCommandTest {

    protected static final String DESCRIPTION  = "description";

    protected static final Long ID = 1L;

    private CategoryToCategoryCommand converter;

    @Before
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convertEmpty() {
        Category cat = new Category();
        assertNotNull(converter.convert(cat));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        Category cat = new Category();
        cat.setId(ID);
        cat.setDescription(DESCRIPTION);
        CategoryCommand cc = converter.convert(cat);

        assertEquals(ID, cc.getId());
        assertEquals(DESCRIPTION, cc.getDescription());

    }
}