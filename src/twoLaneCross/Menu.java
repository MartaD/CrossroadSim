package twoLaneCross;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Label labelYellowCars;
	private Label labelPinkCars;
	private Label labelSimulationSpeed;
	private Label labelMaxSpeed;
	private Label labelRoadConditions;
	private Label labelLeftCars;
	private Label labelAvgSpeed;
	private Label labelCarsOnCrossroad;
	private JComboBox comboBoxBlueCars;
	private JComboBox comboBoxLightBlueCars;
	private JComboBox comboBoxYellowCars;
	private JComboBox comboBoxPinkCars;
	private JComboBox comboBoxMaxSpeed;
	private JComboBox comboBoxConditions;
	private JTextField textLeftCars;
	private JTextField textAverageSpeed;
	private JTextField textCarsOnCrossroad;

	private Timer timer;
	private Board board;
	private String[] trafficTypes = { "small", "average", "high" };
	private String[] speed = { "1", "2", "3", "4", "5" };
	private String[] conditionTypes = { "bad", "medium", "good" };

	// simulation variables
	private int simulationSpeed = 100;
	private int blueCars;
	private int lightBlueCars;
	private int yellowCars;
	private int pinkCars;
	private int maxSpeed;
	private int conditions;

	public Menu(JFrame frame) {
		this.frame = frame;
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.iteration(blueCars, lightBlueCars, yellowCars, pinkCars,
						maxSpeed, conditions);
				textLeftCars.setText(" " + Point.getTotalNumberOfCars());
				if (Point.getTotalNumberOfCars() != 0)
					textAverageSpeed.setText(" "
							+ Board.getTotalSpeed()
							/ (Point.getTotalNumberOfCars() + Board
									.getTotalGeneratedCars()));
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
		panelMenu.setLayout(new GridLayout(22, 2));

		buttonStart = new Button("Start simulation");
		buttonStop = new Button("Stop simulation");
		buttonPause = new Button("Pause simulation");

		labelMenu = new Label("Menu");

		labelBlueCars = new Label("Blue cars traffic");
		labelLightBlueCars = new Label("Lightblue cars traffic");
		labelYellowCars = new Label("Yellow cars traffic");
		labelPinkCars = new Label("Pink cars traffic");

		labelSimulationSpeed = new Label("Simulation speed");
		labelMaxSpeed = new Label("Max speed");
		labelRoadConditions = new Label("Conditions");
		labelLeftCars = new Label("Cars that left crossroad");
		labelAvgSpeed = new Label("Average speed");
		labelCarsOnCrossroad = new Label("Cars on crossroad");

		comboBoxBlueCars = new JComboBox<>(trafficTypes);
		comboBoxLightBlueCars = new JComboBox<>(trafficTypes);
		comboBoxYellowCars = new JComboBox<>(trafficTypes);
		comboBoxPinkCars = new JComboBox<>(trafficTypes);

		comboBoxMaxSpeed = new JComboBox<>(speed);
		comboBoxMaxSpeed.setSelectedIndex(2);
		comboBoxConditions = new JComboBox<>(conditionTypes);
		comboBoxConditions.setSelectedIndex(1);

		sliderSimulationSleep = new JSlider(0, 100, 100);
		sliderSimulationSleep.setPaintLabels(true);
		sliderSimulationSleep.setPaintTicks(true);
		sliderSimulationSleep.setMajorTickSpacing(20);
		sliderSimulationSleep.setMinorTickSpacing(10);

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
		panelMenu.add(labelYellowCars);
		panelMenu.add(comboBoxYellowCars);
		panelMenu.add(labelPinkCars);
		panelMenu.add(comboBoxPinkCars);

		panelMenu.add(labelMaxSpeed);
		panelMenu.add(comboBoxMaxSpeed);

		panelMenu.add(labelRoadConditions);
		panelMenu.add(comboBoxConditions);

		panelMenu.add(labelLeftCars);
		panelMenu.add(textLeftCars);

		panelMenu.add(labelAvgSpeed);
		panelMenu.add(textAverageSpeed);

		panelMenu.add(labelCarsOnCrossroad);
		panelMenu.add(textCarsOnCrossroad);

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
				yellowCars = comboBoxYellowCars.getSelectedIndex() + 1;
				pinkCars = comboBoxPinkCars.getSelectedIndex() + 1;
				maxSpeed = comboBoxMaxSpeed.getSelectedIndex() + 1;
				conditions = comboBoxConditions.getSelectedIndex() + 1;
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
			}
		});
	}
}
