package AIORooftops;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;

import powerbot.barbfisher.barbGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class agilityGUI extends JFrame {

	private JPanel contentPane;
	private String[] options = { "Seer's Village", "Canifis" };
	//private String[] options = { "Seer's Village", "Canifis", "Falador" };
	JLabel lblNewLabel = new JLabel("Rooftop course:");
	JComboBox box = new JComboBox(options);
	JButton button = new JButton("Start");
	JCheckBox checkbox = new JCheckBox("Use teleport");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					agilityGUI frame = new agilityGUI();
					(new agilityGUI()).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public agilityGUI() {
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("AIO Rooftops GUI - by Boolean");
		setBounds(100, 100, 355, 154);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel.setBounds(10, 13, 98, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNewLabel);

		box.setBounds(113, 10, 216, 20);
		contentPane.add(box);
		box.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					checkbox.setSelected(false);
					checkbox.setEnabled(box.getSelectedItem().toString().equals("Seer's Village"));
				}
			}
		});

		button.setBounds(113, 81, 126, 23);
		contentPane.add(button);

		checkbox.setBounds(10, 47, 97, 23);
		contentPane.add(checkbox);
		/*//DELETE WHEN OUT OF BETA
		checkbox.setSelected(true);
		checkbox.setEnabled(false);
		//DELETE WHEN OUT OF BETA
*/
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				agilityGUI.this.jButtonActionPerformed(evt);
			}
		});
	}

	public void jButtonActionPerformed(ActionEvent evt) {
		if (checkbox.isEnabled())
			AIORooftops.useTeleport = checkbox.isSelected();
		else
			AIORooftops.useTeleport = false;
		/*AIORooftops.useTeleport = true;*/
		if (box.getSelectedItem().toString().equals("Seer's Village")) {
			AIORooftops.orientations = AIORooftops.seersOrientations;
			AIORooftops.areas = AIORooftops.seersAreas;
			AIORooftops.IDs = AIORooftops.seersIDs;
			AIORooftops.actions = AIORooftops.seersActions;
			AIORooftops.bounds = AIORooftops.seersBounds;
		}
		
		if (box.getSelectedItem().toString().equals("Canifis")) {
			AIORooftops.orientations = AIORooftops.canifisOrientations;
			AIORooftops.areas = AIORooftops.canifisAreas;
			AIORooftops.IDs = AIORooftops.canifisIDs;
			AIORooftops.actions = AIORooftops.canifisActions;
			AIORooftops.bounds = AIORooftops.canifisBounds;
		}
		AIORooftops.generateVariables();
		this.setVisible(false);
	}
}
