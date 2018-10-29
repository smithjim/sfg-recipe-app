package dev.jim.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

    public int hashCode() {
        int h = (int) (this.id % new Long(Integer.MAX_VALUE));
        return h;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass().equals(this.getClass())) {
            return ((IngredientCommand)o).getId().equals(this.getId());
        }
        return false;
    }
}
