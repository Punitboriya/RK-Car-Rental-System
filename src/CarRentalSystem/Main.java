package CarRentalSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {

	private String carId, brand, model;
	private double basePrice;
	private boolean isAvailable;

	public Car(String carId, String model, String brand, double basePrice) {
		this.carId = carId;
		this.model = model;
		this.brand = brand;
		this.basePrice = basePrice;
		this.isAvailable = true;
	}

	public String getCarId() {
		return carId;
	}

	public String getModel() {
		return model;
	}

	public String getBrand() {
		return brand;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public double calculatePrice(int rantalDays) {
		return basePrice * rantalDays;
	}

	public void rent() {
		isAvailable = false;
	}

	public void returnCar() {
		isAvailable = true;
	}

}

class Customer {
	private String customerId;
	private String name;

	public Customer(String customerId, String name) {
		this.customerId = customerId;
		this.name = name;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}
}

class Rental {// its depends on both classes Car & Coustomer
	private Car car;
	private Customer coustomer;
	private int days;

	public Rental(Car car, Customer coustomer, int days) {
		this.car = car;
		this.coustomer = coustomer;
		this.days = days;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCoustomer() {
		return coustomer;
	}

	public int getDays() {
		return days;
	}

}

class CarRentalSystem {	
	
	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));
		} else {
			System.out.println("Car is not avilable for rent.");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);

		} else {
			System.out.println("Car was not rented.");
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println(" **** Welcome to \"R.K.\" Car Rental System ****\n");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit");
			System.out.println("Enter your choice : ");

			int choice = sc.nextInt();
			sc.nextLine();// Consume newLine.

			if (choice == 1) {
				System.out.println("\n == Rent a Car ==\n");
				System.out.println("Enter your name: ");
				String customerName = sc.nextLine();

				System.out.println("\nAvailavle Cars: ");
				for (Car car : cars) {
					if (car.isAvailable()) {
						System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
					}
				}
				System.out.println("\nEnter the carID you want to rent: ");
				String carId = sc.nextLine();

				System.out.println("How many days you want for rental ?");
				int rentalDays = sc.nextInt();
				sc.nextLine();

				Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectdCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectdCar = car;
						break;
					}
				}
				if (selectdCar != null) {
					double totalPrice = selectdCar.calculatePrice(rentalDays);
					System.out.println("\n == Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectdCar.getBrand() + "  " + selectdCar.getModel());
					System.out.println("Rental Days: " + rentalDays);
					System.out.println("Total Price: " + totalPrice + " ₹");

					System.out.println("\n Confirm Rental (Y/N)");
					String confirm = sc.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectdCar, newCustomer, rentalDays);
						System.out.println("\n Car rented Successfully.");
					} else {
						System.out.println("\n Rental canceled");
					}

				} else {
					System.out.println("\n Invalid car selection or car not avilable for rent.");
				}
			} else if (choice == 2) {
				System.out.println("\n == Return a Car ==\n");
				System.out.println("Enter the carID you want to return: ");
				String carId = sc.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}
				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCoustomer();
							break;
						}
					}
					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Return car sucessfully by " + customer.getName());
					} else {
						System.out.println("Car was not rented or rental information is missing");

					}

				} else {
					System.out.println("Invalid carID or Car is not rented.");
				}

			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalide choice,please enter a valide option.");
			}
		}
		System.out.println("\n Thank you..bye...");
	}

	public List<Car> getCars() {
		return cars;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Rental> getRentals() {
		return rentals;
	}
}

public class Main{
	public static void main(String[] args){
		CarRentalSystem obj = new CarRentalSystem();
		Car car1 = new Car("C001", "Maruti", "Alto", 1000);
		Car car2 = new Car("C002", "Hundai", "i20", 1450);
		Car car3 = new Car("C003", "Mahindra", "Thar", 1750);

		obj.addCar(car1);
		obj.addCar(car2);
		obj.addCar(car3);

		obj.menu();
		
	}
}