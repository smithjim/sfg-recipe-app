package dev.jim.recipe.converters;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "DESCRIPTION";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        uom.setDescription(DESCRIPTION);

        UnitOfMeasureCommand cmd = converter.convert(uom);

        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
    }

    @Test
    public void convertEmpty() {
        UnitOfMeasure uom = new UnitOfMeasure();
        assertNotNull(converter.convert(uom));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }
}