package dev.jim.recipe.services;

import dev.jim.recipe.commands.UnitOfMeasureCommand;
import dev.jim.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository uomRepository;

    UnitOfMeasureServiceImpl service;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(uomRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testFindAll() {
        HashSet<UnitOfMeasure> returnVals = new HashSet<>();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        returnVals.add(uom);
        returnVals.add(uom2);

        when(uomRepository.findAll()).thenReturn(returnVals);

        Set<UnitOfMeasureCommand> result = service.findAll();

        assertNotNull(result);
        assertEquals(returnVals.size(), result.size());

    }

}
