
public class Sink {

	private int count;
	private char icon;
	
	public Sink(char dir) {
		switch (dir) {
		case 'e':
			this.icon = '>';
			break;
		case 'w':
			this.icon = '<';
			break;
		case 'n':
			this.icon = '^';
			break;
		case 's':
			this.icon = 'v';
			break;
		default:
			
		}
	}
	
	public char getIcon() {
		return(this.icon);
	}
	
	public int getCount() {
		return(this.count);
	}
	
	public void tick() {
		this.count++;
	}
}
