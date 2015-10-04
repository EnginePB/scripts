package Fisher;

import javax.swing.*;

import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	final JButton start;
	final JButton exit;
	final JComboBox<String> fish;
	final JComboBox<String> bank;

	public GUI(final ClientContext ctx) {
		start = new JButton("Start Script");
		exit = new JButton("Cancel");
		fish = new JComboBox<>(new String[] { "Lobster", "Shark" });
		//will add more locations later
		bank = new JComboBox<>(new String[] { "Catherby" });

		this.setLocationRelativeTo(Frame.getFrames()[0]);
		this.setTitle("GUI");

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
				int index = fish.getSelectedIndex();
				int index2 = bank.getSelectedIndex();
				Fisher.actualFish = Resources.fish[index];
				Fisher.actualSpot = Resources.spot[index];
				Fisher.actualBooth = Resources.booth[index2];
				Fisher.actualAction = Resources.action[index];
				Fisher.actualUtil = Resources.util[index];
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