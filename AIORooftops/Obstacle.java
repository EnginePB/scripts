package AIORooftops;

public class Obstacle {
	
	private int id;
	private String action;
	private int[] bounds;

	public Obstacle(int id, String action, int[] bounds) {
		this.id = id;
		this.action = action;
		this.bounds = bounds;
	}
	
	public int id() {
		return id;
	}
	
	public String action() {
		return action;
	}
	
	public int[] bounds() {
		return bounds;
	}

}
