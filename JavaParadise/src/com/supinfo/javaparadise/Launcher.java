package com.supinfo.javaparadise;

import java.sql.SQLException;
import java.util.Scanner;

import com.supinfo.javaparadise.dao.DaoFactory;
import com.supinfo.javaparadise.model.Place;
import com.supinfo.javaparadise.model.Trip;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("Welcome aboard !");

		int userChoice;
		do {
			displayMenu();

			userChoice = (int) getUserChoice(1, 8);

			try {
				computeUserChoice(userChoice);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while(userChoice != 8);
	}

	private static void computeUserChoice(int userChoice) throws SQLException {
		switch (userChoice) {
		case 1:
			addPlace();
			break;
		case 2 :
			findPlace();
			break;
		case 3 :
			editPlace();
			break;
		case 4 :
			removePlace();
			break;
		case 5 :
			addTrip();
			break;
		case 6 :
			findTrip();
			break;
		case 7 :
			removeTrip();
			break;
		default:
			break;
		}
	}

	private static void removeTrip() throws SQLException {
		Trip trip = findTrip();
		if(trip != null) {
			System.out.println("Are you sure you want to remove it ? [yes/no]");
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.next();
			if(choice.equalsIgnoreCase("yes")) {
				if(DaoFactory.getTripDao().removeTrip(trip)) {
					System.out.println("Trip removed !");
				} else {
					System.out.println("Impossible to remove the Trip.");
				}
			}
		}
	}

	private static void removePlace() throws SQLException {
		Place place = findPlace();
		if(place != null) {
			System.out.println("All trips with this Place will be removed with it.\n Are you sure you want to remove it ? [yes/no]");
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.next();
			if(choice.equalsIgnoreCase("yes")) {
				if(DaoFactory.getPlaceDao().removePlace(place)) {
					System.out.println("Place removed !");
				} else {
					System.out.println("Impossible to remove the Place.");
				}
			}
		}
	}

	private static void editPlace() throws SQLException {
		Place place = findPlace();
		if(place != null) {
			System.out.print("Enter the new name : ");
			Scanner scanner = new Scanner(System.in);
			String newName = scanner.nextLine();
			place.setName(newName);
			if(DaoFactory.getPlaceDao().updatePlace(place)) {
				System.out.println("Place updated !");
			} else {
				System.out.println("Error during update.");
			}
		}
	}

	private static long getUserChoice() {
		return getUserChoice(null, null);
	}

	private static long getUserChoice(Integer lower, Integer higher) {
		boolean choiceValid = false;

		long choice = -1;
		while(!choiceValid) {
			Scanner scanner = new Scanner(System.in);
			if(scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if((lower == null || choice >= lower) && (higher == null || choice <= higher)) {
					choiceValid = true;
					continue;
				}
			}
			System.out.println("Bad entry, please enter a valid number.");
		}
		return choice;
	}

	private static void displayMenu() {
		System.out.println();
		System.out.println("What do you want to do ?");
		System.out.println("1 - Add a place");
		System.out.println("2 - Find a place");
		System.out.println("3 - Edit a place");
		System.out.println("4 - Remove a place");

		System.out.println("5 - Add a trip");
		System.out.println("6 - Find a trip");
		System.out.println("7 - Remove a trip");

		System.out.println("8 - Quit");
		System.out.println();
	}

	private static Trip findTrip() throws SQLException {
		System.out.print("Please enter the id of the trip : ");
		long tripId = getUserChoice();
		Trip trip = DaoFactory.getTripDao().findTripById((long) tripId);
		displayTrip(trip);
		return trip;
	}

	private static void displayTrip(Trip trip) {
		if(trip != null) {
			System.out.println(
					"Trip : " + trip.getDeparture().getName() + " => " + trip.getDestination().getName() 
					+ ". Price: " + trip.getPrice()
			);
		} else {
			System.out.println("Unknown trip.");
		}
	}

	private static Place findPlace() throws SQLException {
		System.out.print("Please enter the id of the place : ");
		long placeId = getUserChoice();
		Place place = DaoFactory.getPlaceDao().findPlaceById(placeId);
		displayPlace(place);
		return place;
	}

	private static void displayPlace(Place place) {
		if(place != null) {
			System.out.println("Place : " + place.getName());
		} else {
			System.out.println("Unknown place.");
		}
	}

	private static void addTrip() throws SQLException {
		Trip trip = new Trip();

		System.out.print("Departure: ");
		Place place = findPlace();
		trip.setDeparture(place);

		System.out.print("Destination: ");
		place = findPlace();
		trip.setDestination(place);

		System.out.print("Price: ");
		Scanner scanner = new Scanner(System.in);
		while(!scanner.hasNextBigDecimal()) {
			System.out.println("Bad price, please try again.");
			scanner = new Scanner(System.in);
		}
		trip.setPrice(scanner.nextBigDecimal());

		Long id = DaoFactory.getTripDao().createTrip(trip);

		if(id != null) {
			System.out.println("Trip added with the ID=" + id + ".");
		} else {
			System.out.println("Error, impossible to add the trip.");
		}
	}

	private static void addPlace() throws SQLException {
		Place place = new Place();

		System.out.print("Name: ");
		String name = new Scanner(System.in).nextLine();
		place.setName(name);

		Long id = DaoFactory.getPlaceDao().createPlace(place);

		if(id != null) {
			System.out.println("Place added with the ID=" + id + ".");
		} else {
			System.out.println("Error, impossible to add the place.");
		}
	}

}
