import java.util.logging.Logger;


public class Lane {
	
	private Cell[] cell;
	private Lane left;
	private Lane right;
	private int speedLimit;
	private static Logger logger = Logger.getLogger("LaneLogger");

	public Lane() {
		super();
		this.left = null;
		this.right = null;
		cell = new Cell[100];
		for(int i = 0; i < 100; i++) {
			cell[i] = new Cell();
		}
	}

	public boolean isCar(int n) {
		return (cell[n].getCar() != null) ? true : false;
	}
	
	public Car getCar(int n) {
		return cell[n].getCar();
	}
	
	public void setCar(int n, Car x) {
		if(n < cell.length) {
			this.cell[n].setCar(x);
		}
	}
	
	public boolean isSink(int n) {
		return (cell[n].getSink() != null) ? true : false;
	}
	
	public Sink getSink(int n) {
		return cell[n].getSink();
	}
	
	public void setSink(int n, Sink x) {
		this.cell[n].setSink(x);
	}
	
	public boolean isLight(int n) {
		return (cell[n].getLight() != null) ? true : false;
	}
	
	public Light getLight(int n) {
		return cell[n].getLight();
	}
	
	public void setLight(int n, Light x) {
		this.cell[n].setLight(x);
	}

	public boolean isEmpty(int n) {
		if(n < this.length()) {
			return (cell[n].hasCar() == false) ? true : false;
		} else {
			logger.info("detection failed");
			return(false);
		}
	}	
	
	public boolean isClear(int n) {
		if(this.isEmpty(n)) {
			if(cell[n].hasLight()) {
				if(cell[n].getLight().isGreen()) {
					return(true);
				} else {
					return(false);
				}
			} else {
				return(true);
			}		
		} else {
			return(false);
		}
	}
	
	public int length() {
		return(cell.length);
	}
	
	public Lane getLeft() {
		return left;
	}

	public void setLeft(Lane left) {
		this.left = left;
	}

	public Lane getRight() {
		return right;
	}

	public void setRight(Lane right) {
		this.right = right;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

}
