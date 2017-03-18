package AIORooftops;

import org.powerbot.script.Area;

public class Instance {
	
	private Area area;
	private char orientation;
	private Obstacle obstacle;
	
	public Instance(Area area, Obstacle obstacle, char orientation) {
		this.area = area;
		this.obstacle = obstacle;
		this.orientation = orientation;
	}
	
	public Area area() {
		return area;
	}
	
	public char orientation() {
		return orientation;
	}
	
	public Obstacle obstacle() {
		return obstacle;
	}
}
