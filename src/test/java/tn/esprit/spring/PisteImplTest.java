package tn.esprit.spring;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

@SpringBootTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class PisteImplTest {
	
	@Autowired
	IPisteServices ps;
	
	@Test
	@Order(0)
	void testAjouterPiste() {
	    Piste piste = new Piste(null, "piste", Color.RED, 1, 2, null);
	    Piste p = new Piste();
    	p.setColor(Color.RED);
    	p.setLength(1);
    	p.setNamePiste("piste");
    	p.setSkiers(null);
    	p.setSlope(2);
		Piste result = ps.addPiste(piste);
		Assertions.assertEquals(piste, result);
        ps.removePiste(result.getNumPiste());
	}
	@Test
	@Order(1)
	void testRetrieveByIdPiste() {
	    Piste piste = new Piste(null, "piste", Color.RED, 1, 2, null);
		piste = ps.addPiste(piste);
		Piste retrievedPiste = ps.retrievePiste(piste.getNumPiste());
		Assertions.assertEquals(retrievedPiste.getNumPiste(), piste.getNumPiste());
        ps.removePiste(piste.getNumPiste());
	}
	
	@Test
	@Order(2)
	void testRetriveAllPistes() {
		Piste piste = ps.addPiste(new Piste(null, "piste", Color.RED, 1, 2, null));
		List<Piste> listPistes = ps.retrieveAllPistes();
		Assertions.assertEquals(1, listPistes.size());
        ps.removePiste(piste.getNumPiste());
	}
	
	@Test
	@Order(3)
	void testRemovePiste() {
		Piste piste = ps.addPiste(new Piste(null, "piste", Color.RED, 1, 2, null));
		ps.removePiste(piste.getNumPiste());
		Assertions.assertNotNull(piste);
	}
	
	@Test
	@Order(4)
	void testRetriveAllPistesAfterDeletion() {
		List<Piste> listPistes = ps.retrieveAllPistes();
		Assertions.assertEquals(0, listPistes.size());
	}
	
	
	
}
