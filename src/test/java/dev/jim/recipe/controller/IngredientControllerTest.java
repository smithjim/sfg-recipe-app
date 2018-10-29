package dev.jim.recipe.controller;

import dev.jim.recipe.commands.IngredientCommand;
import dev.jim.recipe.commands.RecipeCommand;
import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.services.IngredientService;
import dev.jim.recipe.services.RecipeService;
import dev.jim.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeServiceMock;

    @Mock
    UnitOfMeasureService unitOfMeasureServiceMock;

    @Mock
    IngredientService ingredientServiceMock;


    IngredientController ingredientController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeServiceMock, ingredientServiceMock, unitOfMeasureServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        Long id = new Long(1);
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(id);

        //when
        when(recipeServiceMock.findCommandById(anyLong())).thenReturn(cmd);

        mockMvc.perform(get("/recipe/"+id.toString()+"/ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(recipeServiceMock, times(1)).findCommandById(captor.capture());
        assertEquals(id, captor.getValue());
    }

    @Test
    public void testGetShowIngredient() throws Exception {
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(1L);
        cmd.setRecipeId(2L);

        when(ingredientServiceMock.findByRecipeIdAndId(anyLong(), anyLong())).thenReturn(cmd);

        mockMvc.perform(get("/recipe/" + cmd.getRecipeId() + "/ingredient/" + cmd.getId() + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> recipeIdCapture = ArgumentCaptor.forClass(Long.class);
        verify(ingredientServiceMock, times(1))
                .findByRecipeIdAndId(recipeIdCapture.capture(), idCapture.capture());

        assertEquals(cmd.getId(), idCapture.getValue());
        assertEquals(cmd.getRecipeId(), recipeIdCapture.getValue());
    }

    @Test
    public void testGetUpdateIngredient() throws Exception {
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(1L);
        cmd.setRecipeId(2L);

        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(3L);
        Set<UnitOfMeasureCommand> uomSet = Stream.of(uomCmd).collect(Collectors.toSet());

        when(ingredientServiceMock.findByRecipeIdAndId(anyLong(), anyLong())).thenReturn(cmd);
        when(unitOfMeasureServiceMock.findAll()).thenReturn(uomSet);

        mockMvc.perform(get("/recipe/" + cmd.getRecipeId() + "/ingredient/" + cmd.getId() + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/update"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitMeasures"));
    }

    @Test
    public void testPostNewIngredient() throws Exception {
        Long recipeId = 2L;
        Long id = 3L;
        Long uomId = 4L;

        mockMvc.perform(post("/recipe/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("amount", "0.5")
                .param("description", "MyCustomDescription")
                .param("recipeId", recipeId.toString())
                .param("uom.id", uomId.toString())
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2/ingredient"));


    }
}
