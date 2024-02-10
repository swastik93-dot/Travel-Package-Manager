import java.util.ArrayList;
import java.util.List;

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private Destination destination;

    public Activity(String name, String description, double cost, int capacity, Destination destination) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public Destination getDestination() {
        return destination;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private double balance;
    private PassengerType type;
    private List<Activity> activities;

    public Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.activities = new ArrayList<>();
        if (type == PassengerType.STANDARD || type == PassengerType.GOLD) {
            this.balance = 1000; // Initial balance for standard and gold passengers
        } else {
            this.balance = Double.POSITIVE_INFINITY; // Premium passengers have unlimited balance
        }
    }

    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getBalance() {
        return balance;
    }

    public PassengerType getType() {
        return type;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }
}

enum PassengerType {
    STANDARD,
    GOLD,
    PREMIUM
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Passenger> passengers;
    private List<Destination> destinations;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.passengers = new ArrayList<>();
        this.destinations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : destinations) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println("Activity: " + activity.getName());
                System.out.println("Description: " + activity.getDescription());
                System.out.println("Cost: " + activity.getCost());
                System.out.println("Capacity: " + activity.getCapacity());
            }
            System.out.println();
        }
    }

    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    public void printPassengerDetails(Passenger passenger) {
        System.out.println("Name: " + passenger.getName());
        System.out.println("Passenger Number: " + passenger.getPassengerNumber());
        if (passenger.getType() == PassengerType.STANDARD || passenger.getType() == PassengerType.GOLD) {
            System.out.println("Balance: " + passenger.getBalance());
        }
        System.out.println("Activities:");
        for (Activity activity : passenger.getActivities()) {
            System.out.println("Destination: " + activity.getDestination().getName());
            System.out.println("Activity: " + activity.getName());
            System.out.println("Cost: " + activity.getCost());
        }
    }

    public void printAvailableActivities() {
        System.out.println("Available Activities:");
        for (Destination destination : destinations) {
            for (Activity activity : destination.getActivities()) {
                int spacesAvailable = activity.getCapacity() - getNumberOfPassengersForActivity(activity);
                if (spacesAvailable > 0) {
                    System.out.println("Destination: " + destination.getName());
                    System.out.println("Activity: " + activity.getName());
                    System.out.println("Spaces Available: " + spacesAvailable);
                }
            }
        }
    }

    private int getNumberOfPassengersForActivity(Activity activity) {
        int count = 0;
        for (Passenger passenger : passengers) {
            if (passenger.getActivities().contains(activity)) {
                count++;
            }
        }
        return count;
    }
}public class TravelPackageManager {
    public static void main(String[] args) {
        // Create destinations
        Destination paris = new Destination("Paris");
        Activity eiffelTowerTour = new Activity("Eiffel Tower Tour", "Guided tour of the Eiffel Tower", 50.0, 20, paris);
        Activity louvreMuseumVisit = new Activity("Louvre Museum Visit", "Guided tour of the Louvre Museum", 40.0, 30, paris);
        paris.addActivity(eiffelTowerTour);
        paris.addActivity(louvreMuseumVisit);

        Destination london = new Destination("London");
        Activity buckinghamPalaceTour = new Activity("Buckingham Palace Tour", "Guided tour of Buckingham Palace", 60.0, 25, london);
        Activity britishMuseumVisit = new Activity("British Museum Visit", "Guided tour of the British Museum", 45.0, 35, london);
        london.addActivity(buckinghamPalaceTour);
        london.addActivity(britishMuseumVisit);

        // Create a travel package
        TravelPackage europeTour = new TravelPackage("Europe Tour", 50);
        europeTour.addDestination(paris);
        europeTour.addDestination(london);

        // Add passengers
        Passenger passenger1 = new Passenger("John", 1, PassengerType.STANDARD);
        Passenger passenger2 = new Passenger("Alice", 2, PassengerType.GOLD);
        Passenger passenger3 = new Passenger("Bob", 3, PassengerType.PREMIUM);

        europeTour.addPassenger(passenger1);
        europeTour.addPassenger(passenger2);
        europeTour.addPassenger(passenger3);

        // Assign activities to passengers
        assignActivity(passenger1, eiffelTowerTour);
        assignActivity(passenger1, buckinghamPalaceTour);
        assignActivity(passenger2, louvreMuseumVisit);
        assignActivity(passenger2, buckinghamPalaceTour);
        assignActivity(passenger3, britishMuseumVisit);

        // Print available activities
        System.out.println("Available Activities:");
        europeTour.printAvailableActivities();
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        // Print itinerary
        System.out.println("Travel Package Itinerary:");
        europeTour.printItinerary();
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        // Print passenger list
        System.out.println("Passenger List:");
        europeTour.printPassengerList();
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            // Print details of individual passengers
    System.out.println("Details of Passenger 1:");
    europeTour.printPassengerDetails(passenger1);
    System.out.println("- - - - - - - - - - - - - - - - - - - - -");

    System.out.println("Details of Passenger 2:");
    europeTour.printPassengerDetails(passenger2);
    System.out.println("- - - - - - - - - - - - - - - - - - - - -");

    System.out.println("Details of Passenger 3:");
    europeTour.printPassengerDetails(passenger3);
    System.out.println("- - - - - - - - - - - - - - - - - - - - -");
}

// Method to assign activity to a passenger with error handling
private static void assignActivity(Passenger passenger, Activity activity) {
    if (passenger.getActivities().contains(activity)) {
        System.out.println("Passenger " + passenger.getName() + " is already assigned to activity " + activity.getName());
        return;
    }

    int spacesAvailable = activity.getCapacity() - passenger.getActivities().size();
    if (spacesAvailable <= 0) {
        System.out.println("Activity " + activity.getName() + " is already full. Cannot assign to passenger " + passenger.getName());
        return;
    }

    if (passenger.getType() == PassengerType.STANDARD) {
        if (passenger.getBalance() < activity.getCost()) {
            System.out.println("Passenger " + passenger.getName() + " does not have enough balance to join activity " + activity.getName());
            return;
        }
        passenger.deductBalance(activity.getCost());
    } else if (passenger.getType() == PassengerType.GOLD) {
        double discountedCost = 0.9 * activity.getCost(); // 10% discount
        if (passenger.getBalance() < discountedCost) {
            System.out.println("Passenger " + passenger.getName() + " does not have enough balance to join activity " + activity.getName());
            return;
        }
        passenger.deductBalance(discountedCost);
    }

    passenger.addActivity(activity);
    System.out.println("Passenger " + passenger.getName() + " successfully assigned to activity " + activity.getName());
}
}
