import java.util.*;
import java.util.logging.*;

public class Road {

	private int numLanes;
	private int speedLimit;
	private Lane[] lane;
	private Sink end;
	private LinkedList <Sink>exitList = new LinkedList<Sink>();
	private LinkedList <Light>lightList = new LinkedList<Light>();	
	private Random random = new Random();
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("RoadLogger");

	public Road(int numLanes, int speedLimit) {
		super();
		this.numLanes = numLanes;
		this.speedLimit = speedLimit;
		lane = new Lane[numLanes];
		
		Lane lastLane = null;
		end = new Sink('e');
		exitList.add(end);

		for(int i = 0; i < numLanes; i++) {
			lane[i] = new Lane();
			lane[i].setLeft(lastLane);
			if(lastLane != null) {
				lastLane.setRight(lane[i]);
			}
			lastLane = lane[i];
			
			// set sink
			lane[i].setSink(lane[i].length()-1, end);
			
			// set speed limit
			lane[i].setSpeedLimit(this.speedLimit);
		}
	}
	
	public void addCar() {
		int laneNumber = random.nextInt( lane.length );
		addCar(laneNumber, end);
	}

	public void addCar(Sink destination) {
		int laneNumber = random.nextInt( lane.length );
		addCar(laneNumber, destination);
	}
	
	public void addCar(int laneNumber, Sink destination) {
		if(lane[laneNumber].isEmpty(0)) {
			Car car = new Car();
			car.setLane(lane[laneNumber]);
			car.setPos(0);
			car.setDestination(destination);
			
		} else {
			//logger.info("Can't add car!");
		}
	}
	
	public void addParkedCar(int laneNumber, int pos) {
		lane[laneNumber].setCar(pos, new Car(true));
	}
	
	public void addExit(int l, int n, char d) {
		Sink exit = new Sink(d);
		lane[l].setSink(n, exit);
		exitList.add(exit);
	}
	
	public void addLight(int l, int n, int gt, int rt) {
		Light light = new Light(gt, rt);
		lane[l].setLight(n, light);
		lightList.add(light);
	}
	
	public Sink[] getExits() {
		Sink[] exitListArray = new Sink[exitList.size()];
		return(exitList.toArray(exitListArray));
	}
	
	public void pushCars() {
		for(int i = lane[0].length() -1; i >= 0; i--) {
			for(int l = 0; l < lane.length; l++) {
				if(! lane[l].isEmpty(i)) {
					lane[l].getCar(i).decide();
				}
			}
		}
	}
	
	public void updateLights() {
		Iterator<Light> i = lightList.iterator(); 
		while (i.hasNext()) {
			i.next().update();  
		}
	}
	
	public void print() {
		for(int i = 0; i < lane.length ; i++) {
			for (int j = 0; j < lane[i].length() ; j++) {
				if(lane[i].isCar(j)) {
					System.out.print("x");
				} else if(lane[i].isSink(j)) {
					System.out.print(lane[i].getSink(j).getIcon());
				} else if(lane[i].isLight(j)) {
					if(lane[i].getLight(j).isGreen()) {
						System.out.print("]");
					} else {
						System.out.print("[");
					}
				} else {
					System.out.print("-");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public int getNumLanes() {
		return numLanes;
	}

	public void setNumLanes(int numLanes) {
		this.numLanes = numLanes;
	}
	
	public Lane getLane(int n) {
		return lane[n];
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
}
