package Fisher;


import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class Banking extends Task<ClientContext> {

	public Banking(ClientContext ctx) {
		super(ctx);
	}

	public boolean activate() {
		return ctx.inventory.select().count() == 28;
	}

	@Override
	public void execute() {
		GameObject b = ctx.objects.select().id(Fisher.actualBooth).nearest().poll();
		if (!ctx.objects.select().id(Fisher.actualBooth).isEmpty()) {
			if (b.inViewport()) {
				Fisher.status = "Attempting to bank";
				if (ctx.bank.opened()) {
					Fisher.status = "Banking";
					ctx.bank.deposit(Fisher.actualFish, Bank.Amount.ALL);
				} else {
					b.interact("Bank");
					Condition.wait(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return ctx.bank.opened();
						}
					}, 50, 50);
				}
			} else {
				Fisher.status = "Walking to tile[bank]";
				ctx.movement.step(b.tile());
			}
		} else {
			Fisher.status = "Walking to bank";
			ctx.movement.newTilePath(Resources.thePath).traverse();
		}
	}

}
