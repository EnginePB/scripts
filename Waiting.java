package Fisher;

import java.awt.Point;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

public class Waiting extends Task<ClientContext> {

	public long nextAntiban = Random.nextInt(4000, 6000);

	public Waiting(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.players.local().animation() != -1;
	}

	public void antiBan() {
		long currTime = System.currentTimeMillis();
		if (nextAntiban > currTime) {
			return;
		} else {
			nextAntiban = currTime + Random.nextInt(15000, 25000);
		}

		int r = Random.nextInt(1, 999);

		if (r < 500) {
			ctx.camera.angleTo(Random.nextInt(0, 360));
		} else if (r >= 500 && r < 900) {
			ctx.input.move(new Point(Random.nextInt(0,600),Random.nextInt(0, 500)));
		}
		Condition.sleep(Random.nextInt(300, 500));
	}


	@Override
	public void execute() {
		Fisher.status = "Fishing";
		while (ctx.players.local().animation() != -1) {
			antiBan();
		}
	}

}