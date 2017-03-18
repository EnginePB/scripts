package AIORooftops;

import org.powerbot.script.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;
import org.powerbot.script.rt4.Npc;

@Script.Manifest(name = "AIO Rooftops", description = "Completes seers village agility course.", properties = "client=4;topic=1329527;author=Boolean")
public class AIORooftops extends PollingScript<ClientContext> implements PaintListener {

	// Areas.
	public static Area[] faladorAreas = { new Area(new Tile(3471, 3464, 0), new Tile(3511, 3504, 0)),
			new Area(new Tile(3504, 3491, 2), new Tile(3508, 3498, 2)),
			new Area(new Tile(3503, 3504, 2), new Tile(3497, 3506, 2)),
			new Area(new Tile(3486, 3498, 2), new Tile(3493, 3505, 2)),
			new Area(new Tile(3474, 3491, 3), new Tile(3480, 3500, 3)),
			new Area(new Tile(3477, 3480, 2), new Tile(3484, 3487, 2)),
			new Area(new Tile(3488, 3468, 3), new Tile(3504, 3479, 3)),
			new Area(new Tile(3509, 3474, 2), new Tile(3515, 3483, 2)) };
	public static Area[] seersAreas = { new Area(new Tile(2721, 3490, 0), new Tile(2735, 3481, 0)),
			new Area(new Tile(2720, 3499, 3), new Tile(2730, 3490, 3)),
			new Area(new Tile(2705, 3495, 2), new Tile(2714, 3487, 2)),
			new Area(new Tile(2710, 3483, 2), new Tile(2714, 3477, 2)),
			new Area(new Tile(2700, 3475, 3), new Tile(2715, 3469, 3)),
			new Area(new Tile(2698, 3466, 2), new Tile(2703, 3460, 2)) };
	public static Area[] canifisAreas = { new Area(new Tile(3471, 3464, 0), new Tile(3511, 3504, 0)),
			new Area(new Tile(3504, 3491, 2), new Tile(3508, 3498, 2)),
			new Area(new Tile(3503, 3504, 2), new Tile(3497, 3506, 2)),
			new Area(new Tile(3486, 3498, 2), new Tile(3493, 3505, 2)),
			new Area(new Tile(3474, 3491, 3), new Tile(3480, 3500, 3)),
			new Area(new Tile(3477, 3480, 2), new Tile(3484, 3487, 2)),
			new Area(new Tile(3488, 3468, 3), new Tile(3504, 3479, 3)),
			new Area(new Tile(3509, 3474, 2), new Tile(3515, 3483, 2)) };
	public static Area[] draynorAreas = { new Area(new Tile(3103, 3277, 0), new Tile(3105, 3280, 0)),
			new Area(new Tile(3097, 3277, 3), new Tile(3102, 3281, 3)),
			new Area(new Tile(3088, 3273, 3), new Tile(3091, 3276, 3)),
			new Area(new Tile(3089, 3265, 3), new Tile(3094, 3267, 3)),
			new Area(new Tile(3088, 3257, 3), new Tile(3088, 3261, 3)),
			new Area(new Tile(3088, 3255, 3), new Tile(3094, 3255, 3)),
			new Area(new Tile(3096, 3261, 3), new Tile(3101, 3256, 3)) };
	public static Area[] alkharidAreas = { new Area(new Tile(3272, 3195, 0), new Tile(3274, 3196, 0)),
			new Area(new Tile(3278, 3192, 3), new Tile(3272, 3180, 3)),
			new Area(new Tile(3272, 3173, 3), new Tile(3265, 3161, 3)),
			new Area(new Tile(3302, 3176, 3), new Tile(3283, 3160, 3)),
			new Area(new Tile(3318, 3165, 1), new Tile(3313, 3160, 1)),
			new Area(new Tile(3318, 3179, 2), new Tile(3312, 3174, 2)),
			new Area(new Tile(3318, 3186, 3), new Tile(3312, 3180, 3)),
			new Area(new Tile(3305, 3186, 3), new Tile(3298, 3193, 3)) };
	public static Area[] varrockAreas = { new Area(new Tile(3221, 3416, 0), new Tile(3213, 3412, 0)),
			new Area(new Tile(3219, 3410, 3), new Tile(3214, 3419, 3)),
			new Area(new Tile(3208, 3413, 3), new Tile(3201, 3419, 3)),
			new Area(new Tile(3197, 3416, 1), new Tile(3194, 3416, 1)),
			new Area(new Tile(3198, 3406, 3), new Tile(3192, 3402, 3)),
			new Area(new Tile(3183, 3401, 3), new Tile(3208, 3382, 3)),
			new Area(new Tile(3218, 3403, 3), new Tile(3232, 3393, 3)),
			new Area(new Tile(3236, 3408, 3), new Tile(3240, 3403, 3)),
			new Area(new Tile(3236, 3410, 3), new Tile(3240, 3415, 3)) };
	public static Area[] pollnivneachAreas = { new Area(new Tile(3352, 2962, 0), new Tile(3350, 2961, 0)),
			new Area(new Tile(3346, 2964, 1), new Tile(3351, 2968, 1)),
			new Area(new Tile(3352, 2973, 1), new Tile(3355, 2976, 1)),
			new Area(new Tile(3360, 2977, 1), new Tile(3362, 2979, 1)),
			new Area(new Tile(3366, 2975, 1), new Tile(3369, 2976, 1)),
			new Area(new Tile(3365, 2982, 1), new Tile(3369, 2988, 1)),
			new Area(new Tile(3355, 2980, 2), new Tile(3365, 2985, 2)),
			new Area(new Tile(3357, 2991, 2), new Tile(3366, 2995, 2)),
			new Area(new Tile(3356, 3000, 2), new Tile(3362, 3003, 2)) };
	public static Area[] rellekkaAreas = { new Area(new Tile(2624, 3677, 0), new Tile(2626, 3679, 0)),
			new Area(new Tile(2626, 3676, 3), new Tile(2622, 3672, 3)),
			new Area(new Tile(2622, 3668, 3), new Tile(2615, 3658, 3)),
			new Area(new Tile(2626, 3655, 3), new Tile(2630, 3651, 3)),
			new Area(new Tile(2639, 3653, 3), new Tile(2644, 3649, 3)),
			new Area(new Tile(2643, 3662, 3), new Tile(2650, 3657, 3)),
			new Area(new Tile(2666, 3685, 3), new Tile(2655, 3665, 3)) };

	public Area seersFinal = new Area(new Tile(2702, 3469, 0), new Tile(2708, 3459, 0));

	public static Area[] areas;

	// Actions.
	public static String[] seersActions = { "Climb", "Jump", "Cross", "Jump", "Jump", "Jump" };
	public static String[] canifisActions = { "Climb", "Jump", "Jump", "Jump", "Jump", "Vault", "Jump", "Jump" };

	public static String[] actions;

	// Orientations.
	public static char[] seersOrientations = { 'n', 'w', 's', 's', 's', 'e' };
	public static char[] canifisOrientations = { 'n', 'n', 'w', 'w', 's', 's', 'e', 'n' };

	public static char[] orientations;

	// Obstacle IDs.
	public static int[] seersIDs = { 11373, 11374, 11378, 11375, 11376, 11377 };
	public static int[] faladorIDs = { 10833, 10834, 10836, 11161, 11360, 11361, 11364, 11365, 11366, 11367, 11368,
			11370, 11371 };
	public static int[] canifisIDs = { 10819, 10820, 10821, 10828, 10822, 10831, 10823, 10832 };

	public static int[] IDs;

	// Bounds.
	public static int[][] seersBounds = { { 0, 130, -65, -10, 50, 100 }, { -32, 32, -64, 0, -32, 32 },
			{ 80, 52, 0, 0, 20, 36 }, { -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 },
			{ -32, 32, -64, 0, -32, 32 } };
	public static int[][] canifisBounds = { { 168, 204, -200, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 },
			{ -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 },
			{ -52, 52, -8, 0, -20, 0 }, { -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 } };
	public static int[][] faladorBound = { { 16, 96, -192, -68, 100, 140 }, { 64, 120, 28, 68, 44, 128 },
			{ -28, 28, -12, 60, 60, 120 }, { -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 },
			{ -68, 4, 0, 76, -4, 72 }, { 0, 60, -68, 8, 44, 116 }, { -32, 32, -64, 0, -32, 32 },
			{ -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 },
			{ -32, 32, -64, 0, -32, 32 }, { -32, 32, -64, 0, -32, 32 } };

	public static int[][] bounds;

	// Global variables
	public static List<Obstacle> obstacles = new ArrayList<Obstacle>();
	public static List<Instance> instances = new ArrayList<Instance>();
	public static boolean useTeleport;
	private agilityGUI gui;

	// Paint variables.
	int mark = 11849;
	public String status = "None";
	String currentTime;
	long start = 0, last = 0;
	int starte;
	double runTime;
	private int exp = 0, laps = 0;
	private int expHour = 0, level;
	private static final DecimalFormat k = new DecimalFormat("#.#");
	private final Color color1 = new Color(0, 0, 0, 171);
	private final Color color2 = new Color(255, 255, 255);
	private final BasicStroke stroke1 = new BasicStroke(1);
	private final Font font1 = new Font("Tahoma", 0, 11);

	public void start() {
		gui = new agilityGUI();
		gui.setVisible(true);
		starte = ctx.skills.experience(Constants.SKILLS_AGILITY);
		start = System.currentTimeMillis();
		level = ctx.skills.realLevel(Constants.SKILLS_AGILITY);
	}

	public static void generateVariables() {
		for (int i = 0; i < areas.length; i++) {
			obstacles.add(new Obstacle(IDs[i], actions[i], bounds[i]));
			instances.add(new Instance(areas[i], obstacles.get(i), orientations[i]));
		}
	}

	public void repaint(Graphics g1) {
		if (ctx.game.loggedIn()) {
			int hours = (int) (System.currentTimeMillis() - start) / 3600000;
			int minutes = (int) (System.currentTimeMillis() - start) / 60000 - hours * 60;
			int seconds = (int) (System.currentTimeMillis() - start) / 1000 - hours * 3600 - minutes * 60;
			currentTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
					+ String.format("%02d", seconds);
			runTime = (double) (System.currentTimeMillis() - start) / 3600000;
			exp = (int) (ctx.skills.experience(Constants.SKILLS_AGILITY) - starte);
			expHour = (int) (exp / runTime);
			int lapsHour = (int) (laps / runTime);
			int levels = ctx.skills.realLevel(Constants.SKILLS_AGILITY) - level;
			int toNext = ctx.skills.experienceAt(ctx.skills.realLevel(Constants.SKILLS_AGILITY) + 1)
					- ctx.skills.experience(Constants.SKILLS_AGILITY);
			Graphics2D g = (Graphics2D) g1;
			g.setColor(color1);
			g.fillRoundRect(0, 0, 150, 132, 15, 15);
			g.setColor(color2);
			g.setStroke(stroke1);
			g.drawRoundRect(0, 0, 150, 132, 15, 15);
			g.setFont(font1);
			g.drawString("Runtime: " + currentTime, 10, 20);
			g.drawString("Experience: " + exp + " (" + k.format(expHour / 1000D) + "K)", 10, 40);
			g.drawString("Level: " + ctx.skills.realLevel(Constants.SKILLS_AGILITY) + " (+" + levels + ")", 10, 60);
			g.drawString("Laps: " + laps + " (" + lapsHour + ")", 10, 80);
			g.drawString("Next: " + toNext + " (" + ttl(toNext, expHour) + ")", 10, 100);
			g.drawString("Status: " + status, 10, 120);

			Point mouse = ctx.input.getLocation();
			if (System.currentTimeMillis() - ctx.input.getPressWhen() > 1000) {
				g1.setColor(Color.GREEN);
				g1.drawLine(mouse.x - 5, mouse.y, mouse.x + 5, mouse.y);
				g1.drawLine(mouse.x, mouse.y - 5, mouse.x, mouse.y + 5);
			} else {
				g1.setColor(Color.RED);
				g1.drawLine(mouse.x - 5, mouse.y - 5, mouse.x + 5, mouse.y + 5);
				g1.drawLine(mouse.x + 5, mouse.y - 5, mouse.x - 5, mouse.y + 5);
			}
		}
	}

	public String Time(long i) {
		DecimalFormat nf = new DecimalFormat("00");
		long millis = i;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		return nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
	}

	public String ttl(final int left, final int hourly) {
		if (hourly < 1) {
			return "N/A";
		}
		return Time((long) (left * 3600000D / hourly));
	}

	@Override
	public void poll() {
		ctx.input.speed(Random.nextInt(90, 94));
		while (gui.isVisible()) {
			Condition.sleep();
		}
		GroundItem marky = ctx.groundItems.select().id(mark).nearest().poll();
		if (seersFinal.contains(ctx.players.local()) && useTeleport) {
			status = "Teleporting";
			if (ctx.game.tab(Game.Tab.MAGIC)) {
				if (ctx.widgets.component(218, 27).click()) {
					Condition.wait(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return !seersFinal.contains(ctx.players.local());
						}
					}, 100, 60);
				}
			}
		}
		if (ctx.players.local().tile().floor() == 0 && !instances.get(0).area().contains(ctx.players.local())) {
			status = "Moving to start";
			ctx.movement.findPath(instances.get(0).area().getCentralTile()).traverse();
		}
		for (int i = 0; i < instances.size(); i++) {
			if (instances.get(i).area().contains(ctx.players.local()) && ctx.players.local().animation() == -1) {
				if (marky.valid() && instances.get(i).area().contains(marky)) {
					status = "Picking up mark";
					if (marky.inViewport()) {
						marky.click();
						Condition.wait(new Callable<Boolean>() {

							@Override
							public Boolean call() throws Exception {
								return !ctx.players.local().inMotion();
							}
						}, 80, 60);
					} else {
						ctx.movement.step(marky);
					}
				} else {

					GameObject o = ctx.objects.select().id(instances.get(i).obstacle().id()).nearest().poll();
					if (o.inViewport()) {
						status = "Found obstacle";
						o.bounds(instances.get(i).obstacle().bounds());
						if (o.interact(instances.get(i).obstacle().action())) {
							if (i < instances.size() - 1) {
								ctx.camera.angle(instances.get(i + 1).orientation());
							} else {
								ctx.camera.angle(instances.get(0).orientation());
							}
							status = "Being agile";
							Condition.wait(new Callable<Boolean>() {
								@Override
								public Boolean call() throws Exception {
									return ctx.players.local().animation() != -1;
								}
							}, 80, 50);
						}
					} else {
						ctx.movement.step(o);
					}
				}
			}
		}
	}

}