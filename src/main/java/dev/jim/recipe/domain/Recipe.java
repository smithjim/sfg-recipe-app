package dev.jim.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="recipe")
    private Set<Ingredient> ingredients;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories;

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.getIngredients() == null) {
            this.setIngredients(new HashSet<>());
        }

        this.getIngredients().add(ingredient);
        ingredient.setRecipe(this);
    }

    public void addCategories(Category category) {
        if (this.getCategories() == null) {
            this.setCategories(new HashSet<>());
        }

        this.getCategories().add(category);
        //category.add(this);
    }
}
