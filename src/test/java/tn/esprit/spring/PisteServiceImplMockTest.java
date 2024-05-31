package tn.esprit.spring;

import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PisteServiceImplMockTest {

	@Mock
	IPisteRepository iPisteRepository;
	
	@InjectMocks
    PisteServicesImpl pistesService;
	
    Piste piste = new Piste(null, "piste 1", Color.RED, 1, 2, null);
    @Test
    @Order(0)
    void testAddInstructor() {
        Mockito.when(iPisteRepository.save(Mockito.any(Piste.class))).thenReturn(piste);
        Piste savedPiste = iPisteRepository.save(piste);
        Assertions.assertEquals(savedPiste.getNumPiste(), piste.getNumPiste());
    }
    @Test
    @Order(1)
    void testRetrievePiste() {
        Mockito.when(iPisteRepository.findById(anyLong())).thenReturn(Optional.of(piste));
        Piste piste1 = pistesService.retrievePiste((long)1);
        Assertions.assertNotNull(piste1);
        
    }
    @Test
    @Order(2)
    void testRetrieveAllInstructors() {
        List<Piste> mockList = new ArrayList<>();
        mockList.add(piste);
        Mockito.when(iPisteRepository.findAll()).thenReturn(mockList);
        List<Piste> listInstructors = iPisteRepository.findAll();
        Assertions.assertTrue(listInstructors.size() > 0);
    }
    

}
