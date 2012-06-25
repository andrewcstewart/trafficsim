
public class Cell {
	private Car car = null;
	private Sink sink = null;
	private Light light = null;
	
	public Cell() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Car getCar() {
		return this.car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Sink getSink() {
		return this.sink;
	}
	public void setSink(Sink sink) {
		this.sink = sink;
	}
	public Light getLight() {
		return this.light;
	}
	public void setLight(Light light) {
		this.light = light;
	}
	
	public boolean hasCar() {
		return (this.car != null) ? true : false;
	}
	
	public boolean hasLight() {
		return (this.light != null) ? true : false;
	}
	
	public boolean hasExit() {
		return (this.sink != null) ? true : false;
	}
}
