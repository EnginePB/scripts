package Fisher.resources;

import javax.swing.*;

import org.powerbot.script.rt4.ClientContext;

import Fisher.Fisher;
import Fisher.tasks.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	final JButton start;
	final JButton exit;
	final JComboBox<String> fish;
	final JComboBox<String> bank;

	boolean done;

	public GUI(final ClientContext ctx) {
		start = new JButton("Start Script");
		exit = new JButton("Cancel");
		fish = new JComboBox<>(new String[] { "Lobster", "Swordfish", "Shark" });
		// will add more locations later
		bank = new JComboBox<>(new String[] { "Catherby" });

		this.setTitle("GUI");
		this.setLocationRelativeTo(Frame.getFrames()[0]);

		this.add(bank);
		this.add(fish);
		this.add(start);
		this.add(exit);

		this.setLayout(new FlowLayout());
		this.pack();

		bank.setSelectedIndex(0);
		fish.setSelectedIndex(0);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				done = true;
				int index = fish.getSelectedIndex();
				int index2 = bank.getSelectedIndex();
				Resources.actualFish = Resources.fish[index];
				Resources.actualSpot = Resources.spot[index];
				Resources.actualBooth = Resources.booth[index2];
				Resources.actualAction = Resources.action[index];
				Resources.actualUtil = Resources.util[index];
				Fisher.addTask(new Fish(ctx));
				Fisher.addTask(new Banking(ctx));
				Fisher.addTask(new Wait(ctx));
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