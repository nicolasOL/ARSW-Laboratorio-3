
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	CinemaServices cs = ac.getBean(CinemaServices.class);
	
	@Before
	public void init() {
		List<CinemaFunction> functions= new ArrayList<>();
		String functionDate = "2018-12-18 15:30";
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
        CinemaFunction funct4 = new CinemaFunction(new Movie("The Night 4","Horror"),functionDate);
        CinemaFunction funct5 = new CinemaFunction(new Movie("The Night 5","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        functions.add(funct4);
        functions.add(funct5);
        Cinema c1=new Cinema("Movies Bogotá",functions);
        cs.addNewCinema(c1);
        cs.buyTicket(0, 0, "Movies Bogotá", "2018-12-18 15:30", "The Night 2");
        cs.buyTicket(0, 1, "Movies Bogotá", "2018-12-18 15:30", "The Night 2");
        cs.buyTicket(0, 2, "Movies Bogotá", "2018-12-18 15:30", "The Night 2");
        cs.buyTicket(0, 3, "Movies Bogotá", "2018-12-18 15:30", "The Night 2");
        cs.buyTicket(0, 3, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(0, 4, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(0, 5, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(1, 6, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(1, 7, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(1, 8, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(1, 9, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        cs.buyTicket(1, 10, "Movies Bogotá", "2018-12-18 15:30", "The Night 3");
        
	}

    @Test
    public void saveNewAndLoadTest() throws CinemaPersistenceException{
    	
        CinemaPersitence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        ipct.saveCinema(c);
        
        assertNotNull("Loading a previously stored cinema returned null.",ipct.getCinema(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.",ipct.getCinema(c.getName()), c);
    }


    @Test
    public void saveExistingCinemaTest() {
    	CinemaPersitence ipct=new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2=new Cinema("Movies Bogotá",functions2);
        try{
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        }
        catch (CinemaPersistenceException ex){
            
        }
                
        
    }
    
    @Test
    public void cinemaServicesFilterA() {
    	try {
    	HashMap<String, String> resultadosEsperados = new HashMap<String, String>();
    	resultadosEsperados.put("The Night 2", "Horror");
    	resultadosEsperados.put("The Night 3", "Horror");
    	resultadosEsperados.put("The Night 4", "Horror");
    	resultadosEsperados.put("The Night 5", "Horror");
    	
    	HashMap<String, String> resultadosObtenidos = new HashMap<String, String>();
    	List<Movie> moviesGenre = cs.filterA("Movies Bogotá", "2018-12-18 15:30", "Horror");
    	
    	for(Movie mv: moviesGenre) {
    		resultadosObtenidos.put(mv.getName(), mv.getGenre());
    	}
    	
    	assertEquals(resultadosEsperados, resultadosObtenidos);
    	}catch (UnsupportedOperationException e) {
			assertTrue(e.getMessage().equals("No se tiene implementado el filtro A."));
		}
    }
    
    @Test
    public void cinemaServicesFilterB() {
    	try {
    	List<Movie> moviesSeats = cs.filterB("Movies Bogotá", "2018-12-18 15:30", 82);
    	List<String> movieRes = new ArrayList<String>();
    	movieRes.add("SuperHeroes Movie 2");
    	movieRes.add("The Night 4");
    	movieRes.add("The Night 5");
    	List<String> movieSeatsString = new ArrayList<String>();
    	for(Movie mv: moviesSeats) {
    		movieSeatsString.add(mv.getName());
    	}
    	assertEquals(movieRes,movieSeatsString);
    	}catch (UnsupportedOperationException e) {
    		assertTrue(e.getMessage().equals("No se tiene implementado el filtro B."));
		}
    }
}
