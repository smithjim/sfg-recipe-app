package dev.jim.recipe.converters;

import dev.jim.recipe.commands.NotesCommand;
import dev.jim.recipe.domain.Notes;
import dev.jim.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private static final Long ID = 1L;
    private static final String RECIPE_NOTE = "NOTES";

    NotesToNotesCommand converter;

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        NotesCommand cmd = converter.convert(new Notes());
        assertNotNull(cmd);
    }

    @Test
    public void convert() {
        Notes note = new Notes();
        note.setId(ID);
        note.setRecipeNotes(RECIPE_NOTE);
        note.setRecipe(new Recipe());

        NotesCommand cmd = converter.convert(note);

        assertEquals(ID, cmd.getId());
        assertEquals(RECIPE_NOTE, cmd.getRecipeNotes());
    }
}