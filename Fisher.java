package Fisher;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

@Script.Manifest(name = "BFisher", description = "Fishes.", properties = "game=6;")

public class Fisher extends PollingScript<ClientContext>implements PaintListener, MessageListener {

	public List<Task> taskList = new ArrayList<>();
	static String status = "None";
	public int amount = 0, exp = 0, level = 0;
	int amountHour, expHour;
	long start;
	String currentTime;
	double runTime;

	private GUI gui;
	static int actualSpot, actualBooth, actualFish;
	static String actualAction;

	public void start() {
		gui = new GUI(ctx);
		gui.setVisible(true);
		while (gui.isVisible()) {
			Condition.sleep(1);
		}
		start = System.currentTimeMillis();
		taskList.addAll(Arrays.asList(new Banking(ctx), new Fishing(ctx), new Waiting(ctx)));
	}

	@Override
	public void messaged(MessageEvent m) {
		if (m.text().contains("You catch")) {
			amount++;
		}
	}

	@Override
	public void repaint(Graphics gg) {
		Npc fishSpot = ctx.npcs.select().id(Resources.spot).poll();
		GameObject b = ctx.objects.select().id(Resources.booth).poll();
		int hours = (int) (System.currentTimeMillis() - start) / 3600000;
		int minutes = (int) (System.currentTimeMillis() - start) / 60000 - hours * 60;
		int seconds = (int) (System.currentTimeMillis() - start) / 1000 - hours * 3600 - minutes * 60;
		currentTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
				+ String.format("%02d", seconds);
		runTime = (double) (System.currentTimeMillis() - start) / 3600000;
		amountHour = (int) (amount / runTime);
		expHour = (int) (exp / runTime);
		exp = amount * 90;
		gg.setColor(new Color(0, 0, 0, 146));
		gg.fillRect(5, 5, 115, 80);
		gg.setColor(Color.GREEN);
		gg.drawString("Runtime: " + currentTime, 8, 20);
		gg.drawString("Exp: " + exp + " (" + expHour + ")", 8, 40);
		gg.drawString("Caught: " + amount + " (" + amountHour + ")", 8, 60);
		gg.drawString(status, 8, 80);
		gg.drawRect(5, 5, 115, 80);
	}

	@Override
	public void poll() {
		for (Task t : taskList) {
			if (t.activate()) {
				t.execute();
			}
		}
	}

}