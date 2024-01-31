package Parkhaus; //Author: Murad Kazbekov

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Fahrzeugtypen
enum VehicleType {
    CAR, MOTORCYCLE
}

// Klasse für ein Fahrzeug
class Vehicle {
    private String id;

    public Vehicle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

// Klasse für das Parkhaus
class Garage {
    private int numberOfFloors;
    private int parkingSpacesPerFloor;
    private Map<String, ParkingSpot> occupiedSpots;

    public Garage(int numberOfFloors, int parkingSpacesPerFloor) {
        this.numberOfFloors = numberOfFloors;
        this.parkingSpacesPerFloor = parkingSpacesPerFloor;
        this.occupiedSpots = new HashMap<>();
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        for (int floor = 1; floor <= numberOfFloors; floor++) {
            for (int spot = 1; spot <= parkingSpacesPerFloor; spot++) {
                ParkingSpot parkingSpot = new ParkingSpot(floor, spot);
                if (!occupiedSpots.containsValue(parkingSpot)) {
                    occupiedSpots.put(vehicle.getId(), parkingSpot);
                    return parkingSpot;
                }
            }
        }
        return null; // Kein freier Parkplatz gefunden
    }

    public ParkingSpot findVehicle(String vehicleId) {
        return occupiedSpots.get(vehicleId);
    }

    public int getFreeParkingSpaces() {
        return numberOfFloors * parkingSpacesPerFloor - occupiedSpots.size();
    }
}

// Klasse für einen Parkplatz
class ParkingSpot {
    private int floor;
    private int spot;

    public ParkingSpot(int floor, int spot) {
        this.floor = floor;
        this.spot = spot;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ParkingSpot that = (ParkingSpot) obj;
        return floor == that.floor && spot == that.spot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, spot);
    }

    @Override
    public String toString() {
        return "Floor: " + floor + ", Spot: " + spot;
    }
}

public class main {
    public static void main(String[] args) {
        // Anzahl an etagen, Anzahl an plätzen
        Garage garage = new Garage(3, 25);

        Vehicle car = new Vehicle("Con360");
        Vehicle motorcycle = new Vehicle("Sulting360");

        ParkingSpot carSpot = garage.parkVehicle(car);
        ParkingSpot motorcycleSpot = garage.parkVehicle(motorcycle);

        System.out.println("Car parked at: " + carSpot);
        System.out.println("Motorcycle parked at: " + motorcycleSpot);

        ParkingSpot foundCarSpot = garage.findVehicle(car.getId());
        System.out.println("Car is at: " + foundCarSpot);

        System.out.println("Free parking spaces: " + garage.getFreeParkingSpaces());
    }
}
