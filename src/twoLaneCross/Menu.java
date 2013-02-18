package twoLaneCross;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu extends JPanel {

	// GUI
	private JPanel panelMenu;
	private Button buttonStart;
	private Button buttonStop;
	private Button buttonPause;
	private JSlider sliderSimulationSleep;
	private JFrame frame;
	private Label labelMenu;
	private Label labelBlueCars;
	private Label labelLightBlueCars;
	private Label labelGrayCars;
	private Label labelPinkCars;
	private Label labelSimulationSpeed;
	private Label labelMaxSpeed;
	private Label labelRoadConditions;
	private Label labelLeftCars;
	private Label labelAvgSpeed;
	private Label labelCarsOnCrossroad;
	private Label labelChangeLight;
	private JComboBox comboBoxBlueCars;
	private JComboBox comboBoxLightBlueCars;
	private JComboBox comboBoxGrayCars;
	private JComboBox comboBoxPinkCars;
	private JComboBox comboBoxMaxSpeed;
	private JComboBox comboBoxConditions;
	private JComboBox comboBoxChangeLight;
	private JTextField textLeftCars;
	private JTextField textAverageSpeed;
	private JTextField textCarsOnCrossroad;

	private Timer timer;
	private Board board;
	private String[] trafficTypes = { "small", "average", "high" };
	private String[] speed = { "1", "2", "3", "4", "5" };
	private String[] conditionTypes = { "bad", "medium", "good" };
	private String[] changeLight = { "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10" };

	// simulation variables
	private int simulationSpeed = 1;
	private int blueCars;
	private int lightBlueCars;
	private int grayCars;
	private int pinkCars;
	private int maxSpeed;
	private int conditions;
	private int changeLightFreq;

	public Menu(JFrame frame) {
		this.frame = frame;
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.iteration(blueCars, lightBlueCars, grayCars, pinkCars,
						maxSpeed, conditions, changeLightFreq);
				textLeftCars.setText(" " + Point.getTotalNumberOfCars());
				textAverageSpeed.setText(" " + Board.getAvgSpeed());
				textCarsOnCrossroad.setText(" "
						+ (Board.getTotalGeneratedCars() - Point
								.getTotalNumberOfCars()));
			}
		});
		timer.stop();
	}

	protected void initialize(Container container) {
		container.setLayout(new BorderLayout());
		container.setSize(400, 400);

		panelMenu = new JPanel();
		panelMenu.setLayout(new GridLayout(15, 2));

		buttonStart = new Button("Start simulation");
		buttonStop = new Button("Stop simulation");
		buttonPause = new Button("Pause simulation");

		labelMenu = new Label("Crossroad simulation menu");

		labelBlueCars = new Label("Blue cars traffic");
		labelLightBlueCars = new Label("Lightblue cars traffic");
		labelGrayCars = new Label("Gray cars traffic");
		labelPinkCars = new Label("Pink cars traffic");

		labelSimulationSpeed = new Label("Simulation speed");
		labelMaxSpeed = new Label("Max speed");
		labelRoadConditions = new Label("Road conditions");
		labelLeftCars = new Label("Cars that left crossroad");
		labelAvgSpeed = new Label("Average speed");
		labelCarsOnCrossroad = new Label("Cars on crossroad");
		labelChangeLight = new Label("Change light freq.");

		comboBoxBlueCars = new JComboBox(trafficTypes);
		comboBoxLightBlueCars = new JComboBox(trafficTypes);
		comboBoxGrayCars = new JComboBox(trafficTypes);
		comboBoxPinkCars = new JComboBox(trafficTypes);

		comboBoxMaxSpeed = new JComboBox(speed);
		comboBoxMaxSpeed.setSelectedIndex(2);
		comboBoxConditions = new JComboBox(conditionTypes);
		comboBoxConditions.setSelectedIndex(1);

		comboBoxChangeLight = new JComboBox(changeLight);
		comboBoxChangeLight.setSelectedIndex(4);

		sliderSimulationSleep = new JSlider(1, 5, 1);
		sliderSimulationSleep.setPaintLabels(true);
		sliderSimulationSleep.setPaintTicks(true);
		sliderSimulationSleep.setMajorTickSpacing(1);
		sliderSimulationSleep.setMinorTickSpacing(1);

		textLeftCars = new JTextField();
		textLeftCars.setText(" " + 0);

		textAverageSpeed = new JTextField();
		textAverageSpeed.setText(" " + 0);

		textCarsOnCrossroad = new JTextField();
		textCarsOnCrossroad.setText(" " + 0);

		addListeners();
		panelMenu.add(labelMenu);
		panelMenu.add(buttonStart);
		panelMenu.add(buttonPause);
		panelMenu.add(buttonStop);
		panelMenu.add(labelSimulationSpeed);
		panelMenu.add(sliderSimulationSleep);

		panelMenu.add(labelBlueCars);
		panelMenu.add(comboBoxBlueCars);
		panelMenu.add(labelLightBlueCars);
		panelMenu.add(comboBoxLightBlueCars);
		panelMenu.add(labelGrayCars);
		panelMenu.add(comboBoxGrayCars);
		panelMenu.add(labelPinkCars);
		panelMenu.add(comboBoxPinkCars);

		panelMenu.add(labelMaxSpeed);
		panelMenu.add(comboBoxMaxSpeed);

		panelMenu.add(labelRoadConditions);
		panelMenu.add(comboBoxConditions);

		panelMenu.add(labelChangeLight);
		panelMenu.add(comboBoxChangeLight);

		panelMenu.add(labelLeftCars);
		panelMenu.add(textLeftCars);

		panelMenu.add(labelCarsOnCrossroad);
		panelMenu.add(textCarsOnCrossroad);

		 panelMenu.add(labelAvgSpeed);
		 panelMenu.add(textAverageSpeed);

		board = new Board();
		board.initialize(30, 30);
		container.add(board, BorderLayout.CENTER);
		container.add(panelMenu, BorderLayout.EAST);
	}

	private void addListeners() {
		buttonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				blueCars = comboBoxBlueCars.getSelectedIndex() + 1;
				lightBlueCars = comboBoxLightBlueCars.getSelectedIndex() + 1;
				grayCars = comboBoxGrayCars.getSelectedIndex() + 1;
				pinkCars = comboBoxPinkCars.getSelectedIndex() + 1;
				maxSpeed = comboBoxMaxSpeed.getSelectedIndex() + 1;
				conditions = comboBoxConditions.getSelectedIndex() + 1;
				changeLightFreq = comboBoxChangeLight.getSelectedIndex() + 1;
				timer.start();
			}
		});

		buttonStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				board.clear();

			}
		});

		buttonPause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();

			}
		});

		sliderSimulationSleep.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				simulationSpeed = sliderSimulationSleep.getValue();
				timer.setDelay(1000 / simulationSpeed);
			}
		});
	}
}
