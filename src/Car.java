import java.util.logging.*;

public class Car {
	static int counter = 0;
	private int index;
	private int velocity = 4;
	private int acceleration = 1;
	private int pos = 0;
	private boolean parked=false;
	private Lane lane;
	private Sink destination;
	private static Logger logger = Logger.getLogger("CarLogger");

	public Car() {
		this.index = counter++;
	}
	
	public int getIndex() {
		return(this.index);
	}
	
	public Car(boolean parked) {
		this.parked=true;
	}
	
	public void setSpeed(int value) {
		this.velocity = value;
	}

	public int getSpeed() {
		return this.velocity;
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
		this.lane.setCar(pos, this);
	}

	public void movePos(int pos) {
		this.setPos(pos);
	}
	
	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public Sink getDestination() {
		return destination;
	}

	public void setDestination(Sink destination) {
		this.destination = destination;
	}
	
	public void decide() {
		if(parked) {
			// do nothing
		} else if(this.atDestination() || this.nearDestination()) {
			//logger.info("At destination! " + this.destination.getCount() + "  pos" + this.pos);
			done();
		} else {
			if(this.destinationBound(this.lane)) {
				if(this.forwardClear()) {
					if(this.velocity < this.lane.getSpeedLimit()) {
						this.speedUp();
					} else if(this.velocity > this.lane.getSpeedLimit()) {
						this.slowDown();
					}
				} else if(this.leftClear()) {
					shiftLeft();
				} else if(this.rightClear()) {
					shiftRight();
				} else {
					applyBreak();
				}
				// drive
				this.drive();
			} else {
				// change lane
				if(this.destination.getIcon() == '^' && this.leftClear()) {
					this.shiftLeft();
				}
				else if(this.destination.getIcon() == 'v' && this.rightClear()) {
					this.shiftRight();
				} else {
					applyBreak();
				}
				this.drive();
			}
		}
	}

	private void done() {
		this.destination.tick();
		this.lane.setCar(this.pos, null);	
		this.parked = true;
	}

	private void applyBreak() {
		// TODO Auto-generated method stub
		//this.velocity /= 2;
		slowDown();
	}
	
	private void speedUp() {
		//this.velocity += this.acceleration;
		this.velocity = lane.getSpeedLimit();
	}
	
	private void slowDown() {
		if(this.velocity > 0) {
			this.velocity -= this.acceleration;
		}
	}

	private void shiftRight() {
		this.lane.setCar(this.pos, null);
		this.lane = this.lane.getRight();
		this.lane.setCar(this.pos, this);
	}

	private boolean rightClear() {
		if(this.lane.getRight() != null) {
			return (this.lane.getRight().isClear(this.pos + this.velocity)) ? true : false;
		} else {
			return(false);
		}
	}

	private void shiftLeft() {
		this.lane.setCar(this.pos, null);
		this.lane = this.lane.getLeft();
		this.lane.setCar(this.pos, this);
	}

	private boolean leftClear() {
		if(this.lane.getLeft() != null) {
			return (this.lane.getLeft().isClear(this.pos + this.velocity)) ? true : false;
		} else {
			return(false);
		}
	}

	private void drive() {
		if(this.pos + this.velocity > this.lane.length()) {
			done();
		} else {
			this.lane.setCar(this.pos, null);
			this.setPos(this.pos += this.velocity);
			//this.pos += this.velocity;
			//this.lane.setCar(this.pos, this);
		}
	}

	private boolean forwardClear() {
		if(this.velocity == 0) {
			if(this.lane.isClear(this.pos + 1)) {
				return(true);
			} else {
				return(false);
			}
		} else {
			for(int i = 1; i <= this.velocity; i++) {
				if( this.lane.isClear(this.pos + i) == false) {
					return(false);
				}
			}
			return(true); // all clear
		}
	}

	private boolean destinationBound(Lane l) {
		
		if(this.destination == null) {
			return(false);
		} else if (this.destination.getIcon() == '>') {
			return(true);
		} else if (this.destination.getIcon() == '^') {
			return (this.lane.getLeft() == null) ? true : false;
		} else if (this.destination.getIcon() == 'v') {
			return (this.lane.getRight() == null) ? true : false;
		} else if (this.destination.getIcon() == '<') {
			logger.warning("You're going the wrong way!");
			return(false);
		} else {
			logger.warning("No destination? Parked?");
			return(false);
		}
	}

	public boolean nearDestination() {
		for(int i = 0; i <= this.velocity; i++) {
			if(this.destination.equals(this.lane.getSink(this.pos + i))) {
				return(true);
			}
		}
		return(false);
	}
	
	private boolean atDestination() {
		if(this.destination.equals(this.lane.getSink(this.pos))) {
			return(true);
		} else {
			return(false);
		}
	}

}
