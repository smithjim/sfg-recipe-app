package dev.jim.recipe.converters;

import dev.jim.recipe.commands.CategoryCommand;
import dev.jim.recipe.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

@Component
public class CategoryCommandToCategoryTest {

    protected static final String DESCRIPTION  = "description";

    protected static final Long ID = 1L;

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convertEmpty() {
        CategoryCommand cc = new CategoryCommand();
        assertNotNull(converter.convert(cc));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        CategoryCommand cc = new CategoryCommand();
        cc.setId(ID);
        cc.setDescription(DESCRIPTION);
        Category cat = converter.convert(cc);

        assertEquals(ID, cat.getId());
        assertEquals(DESCRIPTION, cat.getDescription());

    }
}