/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.springdemo;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás
 */
public class main {
	public static void main(String a[]) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		CinemaServices cs = ac.getBean(CinemaServices.class);
		Cinema c = new Cinema("Cine Prueba", null);
		cs.addNewCinema(c);
		Set<Cinema> cines = cs.getAllCinemas();
		for (Cinema ci : cines) {
			System.out.println(ci.getName());
		}
		cs.buyTicket(0, 0, "cinemaX", "2018-12-18 15:30", "The Night");
		List<CinemaFunction> cineFs = cs.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:30");
		for (CinemaFunction ciF : cineFs) {
			System.out.println(ciF.getMovie().getName());
			System.out.println(ciF.getSeats());
		}
		try {
			System.out.println(cs.getCinemaByName("cinemaX").getName());
		} catch (CinemaException ex) {
			System.out.println("Cine no encontrado");
		}

		// Pruebas con filtros
		
		//Filtro A

		try {
			List<Movie> moviesGenre = cs.filterA("cinemaS", "2018-12-18 15:30", "Horror");
			for (Movie m : moviesGenre) {
				System.out.println("Peliculas por genero " + m.getName());
			}
		} catch (UnsupportedOperationException ue) {

		}

		// Filtro B
		try {
			List<Movie> moviesSeats = cs.filterB("cinemaX", "2018-12-18 15:30", 25);

			for (Movie m : moviesSeats) {
				System.out.println("Peliculas por asientos " + m.getName());
			}
		} catch (UnsupportedOperationException ue) {

		}

	}
}
