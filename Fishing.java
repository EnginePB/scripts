package Fisher;

import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;

import org.powerbot.script.rt4.Npc;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

public class Fishing extends Task<ClientContext> {

	public Fishing(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.inventory.select().count() < 28 && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		TilePath path = ctx.movement.newTilePath(Resources.thePath);
		Npc fishSpot = ctx.npcs.select().id(Fisher.actualSpot).nearest().poll();
		if (!ctx.npcs.id(Fisher.actualSpot).isEmpty()) {
			if (fishSpot.inViewport()) {
				Fisher.status = "Attempting to fish";
				fishSpot.interact(Fisher.actualAction);
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.players.local().animation() != -1;
					}
				}, 50, 50);
			} else {
				if (fishSpot.tile().distanceTo(ctx.players.local().tile()) > 5) {
					Fisher.status = "Walking to spot";
					ctx.movement.step(fishSpot.tile());
					for (int i = 0; i < 400 && ctx.players.local().inMotion(); i++)
						Condition.sleep(10);
				}
			}
		} else {
			Fisher.status = "Walking to spot";
			if (path.reverse().traverse()) {
				Fisher.status = "Turning camera";
				ctx.camera.turnTo(fishSpot);
			}
		}
	}

}
