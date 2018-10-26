package dev.jim.recipe.converters;

import dev.jim.recipe.commands.NotesCommand;
import dev.jim.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    public NotesCommand convert(Notes notes) {
        if (notes == null) {
            return null;
        }

        NotesCommand cmd = new NotesCommand();
        cmd.setId(notes.getId());
        cmd.setRecipeNotes(notes.getRecipeNotes());

        return cmd;
    }
}
