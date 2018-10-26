package dev.jim.recipe.converters;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "DESCRIPTION";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void convert() {
        UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);

        UnitOfMeasure uom = converter.convert(cmd);

        assertEquals(ID, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());

    }

    @Test
    public void convertEmpty() {
        UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
        assertNotNull(converter.convert(cmd));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }
}