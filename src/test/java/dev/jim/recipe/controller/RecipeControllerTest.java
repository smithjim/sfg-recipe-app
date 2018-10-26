package dev.jim.recipe.controller;

import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.domain.Recipe;
import dev.jim.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
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
    public void testGetNewRecipe() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipe() throws Exception {
        RecipeCommand returnRecipe = new RecipeCommand();
        returnRecipe.setId(2L);

        when(recipeServiceMock.saveRecipeCommand(any())).thenReturn(returnRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "")
                .param("description", "test description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand updatedRecipe = new RecipeCommand();
        updatedRecipe.setId(2L);

        when(recipeServiceMock.findCommandById(2L)).thenReturn(updatedRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/recipe/"+updatedRecipe.getId()+"/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testGetShowRecipe() throws Exception {
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

    @Test
    public void testGetDeleteRecipe() throws Exception {
        Long id = new Long(2L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/"+id.intValue()+"/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(recipeServiceMock, times(1)).deleteById(argument.capture());

        assertEquals(id, argument.getValue());


    }

}