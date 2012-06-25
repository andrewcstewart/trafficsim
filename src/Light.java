import java.util.Random;
import java.util.logging.Logger;

public class Light {
	private boolean state;
	private int timer = 0;
	private int redTime;
	private int greenTime;
	Random random = new Random();
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("LightLogger");
	
	public Light(int redTime, int greenTime) {
		super();
		this.redTime = redTime;
		this.greenTime = greenTime;
		this.state = true;
		//this.state = random.nextBoolean();
	}
	
	public boolean isGreen() {
		return (this.state == true) ? true : false;
	}
	
	public boolean isRed() {
		return (this.state == false) ? true : false;
	}
	
	public void update() {
		if(this.isGreen() == true && this.timer >= this.greenTime) {
			change();
			this.timer = 0;
		} else if (this.isRed() == true && this.timer >= this.redTime) {
			change();
			this.timer = 0;
		} else {
			this.timer++;
		}
	}

	private void change() {
		if(this.isGreen()) {
			this.state = false;
		} else if(this.isRed()) {
			this.state = true;
		}
	}
}
