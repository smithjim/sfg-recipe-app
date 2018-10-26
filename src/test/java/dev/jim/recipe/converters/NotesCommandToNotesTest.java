package dev.jim.recipe.converters;

import dev.jim.recipe.commands.NotesCommand;
import dev.jim.recipe.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NotesCommandToNotesTest {

    private static final Long ID = 1L;
    private static final String RECIPE_NOTE = "NOTES";

    NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        Notes note = converter.convert(new NotesCommand());
        assertNotNull(note);
    }

    @Test
    public void convert() {
        NotesCommand cmd = new NotesCommand();
        cmd.setId(ID);
        cmd.setRecipeNotes(RECIPE_NOTE);

        Notes note = converter.convert(cmd);

        assertEquals(ID, note.getId());
        assertEquals(RECIPE_NOTE, note.getRecipeNotes());
    }
}