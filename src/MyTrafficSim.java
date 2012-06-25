import java.io.*;
import java.util.Random;
import java.util.logging.*;

public class MyTrafficSim {

	static int mode;
	static boolean run = true;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("SimulationLogger");
	private static Random random = new Random();

	static class Simulation extends Thread {

		private Road road;
		private int steps = 1000;

		public Simulation() {

		}		

		public void init() {
			System.out.println("Initializing simulation (null hypothesis).");
			
			// Create road
			road = new Road(2, 3);
			
			// Add lights
			road.addLight(0, 29, 15, 15);
			road.addLight(1, 29, 15, 15);
			//road.addLight(2, 29, 15, 15);
			
			road.addLight(0, 59, 15, 15);
			road.addLight(1, 59, 15, 15);
			//road.addLight(2, 59, 15, 15);

			// Add left turns
			road.addExit(0, 30, 'n');
			road.addExit(0, 60, 'n');
			
			// Add right turns
			road.addExit(road.getNumLanes()-1, 30, 's');
			road.addExit(road.getNumLanes()-1, 60, 's');
			
			// Add obstacles?
			for(int i = 1; i < 15; i++) {
				int rightLaneNumber = road.getNumLanes()-1;
				road.addParkedCar(rightLaneNumber, random.nextInt(road.getLane(rightLaneNumber).length()));
			}
			
			System.out.println("Exits: " + road.getExits().length);
			System.out.println("Inital road...");
			road.print();						
		}

		public void init2() {
			System.out.println("Initializing simulation (alternative hypothesis.");
			
			// Create road
			road = new Road(3, 3);
			
			// Add lights
			road.addLight(0, 29, 15, 15);
			road.addLight(1, 29, 15, 15);
			road.addLight(2, 29, 15, 15);
			
			road.addLight(0, 59, 15, 15);
			road.addLight(1, 59, 15, 15);
			road.addLight(2, 59, 15, 15);

			// NO left turns

			// Add right turns
			road.addExit(road.getNumLanes()-1, 30, 's');
			road.addExit(road.getNumLanes()-1, 60, 's');
			
			// NO parked cars
			
			System.out.println("Exits: " + road.getExits().length);
			System.out.println("Inital road...");
			road.print();						
		}
		
		public void step() {
			rest();
			
			road.updateLights();
			road.pushCars();
			
			if(random.nextInt(10) < 9) {
				road.addCar();
			} else {
				int exitNum = randomRange(1,road.getExits().length-1);
				road.addCar(road.getExits()[exitNum]);
			}
			
			//road.addCar();
			road.print();			
		}

		public void step(int n) {
			for(int i = 0; i < n; i++){
				step();
			}
		}

		public void results() {
			System.out.println("SIMULATION RESULTS");
			System.out.println("Number of iterations: " + this.steps);
			System.out.println("Successful trips:" + road.getExits()[0].getCount());
		}
		
		public void run() {
			System.out.println("Simulation created.");
			while( run ) {

				switch (mode) {
				case 0: 
					// waiting mode
					break;
				case 1: 
					init();
					mode = 0;
					break;
				case 2: 
					init2();
					mode = 0;
					break;
				case 3: 
					step();
					mode = 0;
					break;
				case 4: 
					step(this.steps);
					mode = 0;
					break;
				case 5: 
					step();
					break;					
				case 6: 
					results();
					mode = 0;
					break;					
				default: 
					// do nothing
				}				
			}
			System.out.println("Simulation stopped.");
		}
		
		private void rest() {
			try {
				Thread.sleep(100); // do nothing for 1000 miliseconds (1 second)
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private int randomRange(int min, int max) {
			return random.nextInt(max - min + 1) + min;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Welcome
		System.out.println("Welcome to my traffic simulation project.");
		System.out.println("Choose from the following options:");
		System.out.println("start - Start a simulation");
		System.out.println("stop - Stop a simulation");
		System.out.println("0 - clear");
		System.out.println("1 - init null hypothesis");
		System.out.println("2 - init alt hypothesis");
		System.out.println("3 - step 1");
		System.out.println("4 - step n (1000x)");
		System.out.println("5 - step continuous");
		System.out.println("6 - print results");
		System.out.println("exit - Exit the program");		
		System.out.println("");

		// simulation initialization
		Simulation s = new Simulation();

		// input initialization
		String command = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while ( ! command.equals("exit") ) {
			System.out.print("Input: ");
			try {
				command = reader.readLine();
			} catch (IOException e) {
				System.out.println("Invalid input!");
				e.printStackTrace();
			}

			// parse int command
			if(command.matches("\\d+")) {
				mode = Integer.parseInt(command);
			} else {
				// do nothing
			}

			if(command.matches("start")) {
				s.start();
			}

			if(command.matches("stop")) {
				run = false;
			}

		}
	}
}
