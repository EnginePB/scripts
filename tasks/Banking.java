package Fisher.tasks;

import java.util.concurrent.Callable;

import Fisher.resources.Resources;
import Fisher.resources.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

public class Banking extends Task<ClientContext> {

	public Banking(ClientContext ctx) {
		super(ctx);
	}

	public boolean activate() {
		return ctx.inventory.select().count() == 28
				|| (ctx.bank.opened() && ctx.inventory.count() > ctx.inventory.id(Resources.actualUtil).count());
	}

	public boolean bae(int id) {
		if (ctx.inventory.count() == ctx.inventory.id(id).count()) {
			return true;
		}
		if (ctx.inventory.id(id).count() < 1 && ctx.bank.depositInventory()) {
			return true;
		} else {
			for (final Item item : ctx.inventory.items()) {
				if (item.id() != id) {
					ctx.bank.deposit(item.id(), Bank.Amount.ALL);
				}
			}
		}
		return ctx.inventory.count() == ctx.inventory.id(id).count();
	}

	@Override
	public void execute() {
		if (!ctx.objects.select().id(Resources.actualBooth).isEmpty()) {
			GameObject b = ctx.objects.id(Resources.actualBooth).nearest().poll();
			if (b.inViewport()) {
				Resources.status = "Attempting to bank";
				if (ctx.bank.opened()) {
					Resources.status = "Banking";
					if (bae(Resources.actualUtil)) {
						ctx.bank.close();
					}
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
				Resources.status = "Walking to bank";
				ctx.movement.step(b.tile());
			}
		} else {
			Resources.status = "Walking to bank";
			ctx.movement.newTilePath(Resources.thePath).traverse();
		}
	}

}
