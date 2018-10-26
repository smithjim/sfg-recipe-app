package dev.jim.recipe.controller;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeServiceMock;

    RecipeController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeServiceMock);
    }

    @Test
    public void testNewRecipe() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void saveRecipe() throws Exception {
        RecipeCommand returnRecipe = new RecipeCommand();
        returnRecipe.setId(2L);

        when(recipeServiceMock.saveRecipeCommand(any())).thenReturn(returnRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "")
                .param("description", "test description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/2"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(2L);

        when(recipeServiceMock.findById(2L)).thenReturn(updatedRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/recipe/"+updatedRecipe.getId()+"/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testShowRecipe() throws Exception {
        Recipe r = new Recipe();
        r.setId(1L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeServiceMock.findById(anyLong())).thenReturn(r);

        mockMvc.perform(get(RecipeController.BASE_URL + "/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeServiceMock, times(1)).findById(anyLong());

    }

}