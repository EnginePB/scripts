package out;

import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Magic;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

@Script.Manifest(name = "AIO Jewelry Enchanter", description = "Enchants any type of jewelry.", properties = "game=6;")
public class AIOEnchanter<C extends ClientContext> extends PollingScript<C>implements PaintListener {

	private static final DecimalFormat k = new DecimalFormat("#.#");
	boolean paintMouse = true;
	String jewelry = "", enchanted = "";
	int widget = 218, components[] = { 6, 17, 29, 37, 52, 64 };
	int component = 0;
	boolean need = false;
	String now = "None";
	public int amount = 0, exp = 0, level = 0;
	int amountHour, expHour;
	long start, starte;
	String currentTime;
	double runTime;
	GUI gui;
	double per;
	Magic.Spell spell = null;

	public void start() {
		if (ctx.game.loggedIn()) {
			gui = new GUI(ctx);
			gui.setVisible(true);
			level = ctx.skills.realLevel(Constants.SKILLS_MAGIC);
			start = System.currentTimeMillis();
			starte = ctx.skills.experience(Constants.SKILLS_MAGIC);
			if (!ctx.inventory.component().visible())
				ctx.game.tab(Game.Tab.INVENTORY);
		}
	}

	@Override
	public void repaint(Graphics arg0) {
		int hours = (int) (System.currentTimeMillis() - start) / 3600000;
		int minutes = (int) (System.currentTimeMillis() - start) / 60000 - hours * 60;
		int seconds = (int) (System.currentTimeMillis() - start) / 1000 - hours * 3600 - minutes * 60;
		currentTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
				+ String.format("%02d", seconds);
		runTime = (double) (System.currentTimeMillis() - start) / 3600000;
		exp = (int) (ctx.skills.experience(Constants.SKILLS_MAGIC) - starte);
		amount = (int)(exp / per);
		amountHour = (int) (amount / runTime);
		expHour = (int) (exp / runTime);
		int levels = ctx.skills.realLevel(Constants.SKILLS_MAGIC) - level;
		arg0.setColor(Color.BLACK);
		arg0.fillRect(0, 0, 160, 70);
		arg0.setColor(Color.WHITE);
		arg0.drawString("Time: " + currentTime + " - " + now, 5, 20);
		arg0.drawString("Exp: " + exp + " (" + k.format(expHour / 1000D) + "K) (+" + levels + ")", 5, 40);
		arg0.drawString("Enchanted: " + amount + " (" + k.format(amountHour / 1000D) + "K)", 5, 60);
		arg0.drawRect(0, 0, 160, 70);

		if (paintMouse) {
			Point mouse = ctx.input.getLocation();
			arg0.setColor(Color.GREEN);
			arg0.drawLine(mouse.x - 5, mouse.y - 5, mouse.x + 5, mouse.y + 5);
			arg0.drawLine(mouse.x + 5, mouse.y - 5, mouse.x - 5, mouse.y + 5);
		}
	}

	public boolean contains(String id) {
		if (ctx.inventory.component().visible()) {
			return ctx.inventory.select().name(id).count(true) > 0;
		} else {
			return true;
		}
	}

	public boolean action(String id) {
		Point p = ctx.inventory.name(id).first().poll().centerPoint();
		int x = Random.nextInt(p.x - 6, p.x + 6);
		int y = Random.nextInt(p.y - 6, p.y + 6);
		if (ctx.input.click(x, y, true)) {
			return true;
		}
		return false;
	}

	@Override
	public void poll() {
		if (jewelry != "") {
			final state s = states();
			switch (s) {
			case WHEN_MAGIC:
				now = "Magic";
				if (ctx.bank.close()) {
					if (ctx.widgets.component(widget, component).borderThickness() != 2) {
						ctx.magic.cast(spell);
						Condition.wait(new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								return !ctx.widgets.component(widget, component).visible();
							}
						}, 50, 50);
					} else {
						ctx.game.tab(Game.Tab.INVENTORY);
					}
				}
				break;
			case WHEN_INVENTORY:
				now = "Inventory";
				if (ctx.bank.close()) {
					if (ctx.widgets.component(widget, component).borderThickness() == 2) {
						if (contains(jewelry)) {
							need = false;
							action(jewelry);
							Condition.wait(new Callable<Boolean>() {
								@Override
								public Boolean call() throws Exception {
									return ctx.widgets.component(widget, component).visible();
								}
							}, 50, 50);
						} else {
							need = true;
						}
					} else {
						ctx.game.tab(Game.Tab.MAGIC);
					}
				}
				break;
			case BANK:
				now = "Bank";
				if (ctx.bank.opened()) {
					if (!contains("Cosmic")) {
						ctx.bank.withdraw(564, Bank.Amount.ALL);
					}
					if (contains(jewelry)) {
						ctx.bank.close();
						need = false;
					} else {
						if (contains(enchanted)) {
							Item e = ctx.inventory.select().name(enchanted).poll();
							ctx.bank.deposit(e.id(), Bank.Amount.ALL);
						} else {
							Item j = ctx.bank.select().name(jewelry).poll();
							ctx.bank.withdraw(j.id(), Bank.Amount.ALL);
						}
					}

				} else {
					if (contains(jewelry)) {
						need = false;
					} else {
						if (ctx.widgets.component(widget, component).borderThickness() != 2) {
							ctx.objects.select().name("Bank booth").nearest().poll().interact("Bank", "Bank booth");
							Condition.wait(new Callable<Boolean>() {
								@Override
								public Boolean call() throws Exception {
									return ctx.bank.opened();
								}
							}, 50, 50);
						} else {
							ctx.objects.name("Bank booth").nearest().poll().click();
						}
					}
				}

				break;
			case DEFAULT:
				now = "Default";
				break;
			}
		}
	}

	public state states() {
		if (contains(jewelry)) {
			if (ctx.inventory.component().visible()) {
				return state.WHEN_INVENTORY;
			} else if (!ctx.inventory.component().visible() && ctx.widgets.component(widget, component).visible()) {
				return state.WHEN_MAGIC;
			} else {
				return state.DEFAULT;
			}
		} else {
			return state.BANK;
		}
	}

	public enum state {
		WHEN_MAGIC, BANK, WHEN_INVENTORY, DEFAULT
	}

	@SuppressWarnings("serial")
	public class GUI extends JFrame {

		final JButton start;
		final JButton exit;
		final JComboBox<String> jewel;
		final JCheckBox paintm;

		boolean done;

		String[] un = { "Sapphire ring", "Sapphire necklace", "Sapphire amulet", "Sapphire bracelet", "Emerald ring",
				"Emerald necklace", "Emerald amulet", "Emerald bracelet", "Ruby ring", "Ruby necklace", "Ruby amulet",
				"Ruby bracelet", "Diamond ring", "Diamond necklace", "Diamond amulet", "Diamond bracelet",
				"Dragonstone ring", "Dragonstone necklace", "Dragonstone amulet", "Dragonstone bracelet", "Onyx ring",
				"Onyx necklace", "Onyx amulet", "Onyx bracelet" };

		public GUI(final ClientContext ctx) {
			start = new JButton("Start Script");
			exit = new JButton("Cancel");
			jewel = new JComboBox<>(new String[] { "Ring of recoil", "Games necklace(8)", "Amulet of magic",
					"Bracelet of clay", "Ring of dueling(8)", "Castle wars bracelet", "Amulet of defence",
					"Binding necklace", "Ring of forging", "Digsite pendant (5)", "Amulet of strength",
					"Inoculation brace", "Ring of life", "Phoenix necklace", "Amulet of power", "Abyssal bracelet",
					"Ring of wealth (5)", "Skills necklace", "Amulet of glory(4)", "Combat bracelet", "Ring of stone",
					"Berserker necklace", "Amulet of fury", "Bracelet of regeneration" });
			paintm = new JCheckBox("Use mouse paint");

			this.setTitle("GUI");
			this.setLocationRelativeTo(Frame.getFrames()[0]);

			this.add(jewel);
			this.add(paintm);
			this.add(start);
			this.add(exit);

			this.setLayout(new FlowLayout());
			this.pack();

			jewel.setSelectedIndex(0);
			paintm.setSelected(true);

			start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					done = true;
					int index = jewel.getSelectedIndex();
					if (!paintm.isSelected()) {
						paintMouse = false;
					}
					enchanted = jewel.getItemAt(index).toString();
					jewelry = un[index];
					if (un[index].contains("Sapphire")) {
						component = components[0];
						spell = Magic.Spell.ENCHANT_LEVEL_1_JEWELLERY;
						per = 17.5;
					} else if (un[index].contains("Emerald")) {
						component = components[1];
						spell = Magic.Spell.ENCHANT_LEVEL_2_JEWELLERY;
						per = 37;
					} else if (un[index].contains("Ruby")) {
						component = components[2];
						spell = Magic.Spell.ENCHANT_LEVEL_3_JEWELLERY;
						per = 59;
					} else if (un[index].contains("Diamond")) {
						component = components[3];
						spell = Magic.Spell.ENCHANT_LEVEL_4_JEWELLERY;
						per = 67;
					} else if (un[index].contains("Dragonstone")) {
						component = components[4];
						spell = Magic.Spell.ENCHANT_LEVEL_5_JEWELLERY;
						per = 78;
					} else if (un[index].contains("Onyx")) {
						component = components[5];
						spell = Magic.Spell.ENCHANT_LEVEL_6_JEWELLERY;
						per = 97;
					}
					dispose();
				}
			});

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ctx.controller.stop();
					dispose();
				}
			});
		}
	}

}
