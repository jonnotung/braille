package authoring;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class represents the user interface for the authoring program. It is
 * responsible for creating all the user interface panels. In order to create a
 * new authoring session, simply create a (single) instance of this class.
 * 
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-15
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = -1291725446662111704L;
	private transient ThreadRunnable audioThread;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	private SettingsPanel settingsPanel;

	/**
	 * Create a new default authoring GUI. Accepts no arguments, and runs until
	 * the UI is closed.
	 */
	public GUI() {
		// Create the colour mapper
		ColourMapper mapper = new ColourMapper();

		// Create the root panel
		JPanel rootContainer = new JPanel();
		rootContainer.setLayout(new BoxLayout(rootContainer, BoxLayout.Y_AXIS));

		// Add the top settings panel
		settingsPanel = new SettingsPanel();
		settingsPanel.setMaximumSize(settingsPanel.getMinimumSize());
		rootContainer.add(settingsPanel);

		JPanel bottomContainer = new JPanel();
		bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.X_AXIS));

		rootContainer.add(bottomContainer);

		// Create the command list pane
		leftPanel = new LeftPanel(this, mapper);
		bottomContainer.add(leftPanel);

		// Create the buttons pane
		rightPanel = new RightPanel(this, mapper);
		bottomContainer.add(rightPanel);

		// Add the root container to the JFrame
		add(rootContainer);

		// Recalculate the button statuses
		leftPanel.recalculateButtonStatus();
	}

	/**
	 * Set the value of the audio thread in order for the main thread to access
	 * it and run various methods on it.
	 * 
	 * @param audioThread
	 *            The instance of the audio thread that the GUI should attempt
	 *            to control
	 */
	public void setAudioThread(ThreadRunnable audioThread) {
		this.audioThread = audioThread;
	}

	/**
	 * Retrieve the last known instance of the audio thread. In the even that
	 * there was never a known instance, this will return null.
	 * 
	 * @return Either the last known instance (if it exists) or null
	 *         (otherwise).
	 */
	public ThreadRunnable getAudioThread() {
		return this.audioThread;
	}

	/**
	 * Obtain the shared reference to the Left Panel. The Left Panel contains
	 * the backend list which is both shown to the authoring user (as a text
	 * list) and used for generating the final output file (via serialization)
	 * 
	 * @return The single instance of the left panel
	 */
	public LeftPanel getLeftPanel() {
		return this.leftPanel;
	}

	/**
	 * Obtain the shared reference to the Right Panel. The Right Panel contains
	 * the buttons for the authoring user to control the application
	 * 
	 * @return The single instance of the right panel
	 */
	public RightPanel getRightPanel() {
		return this.rightPanel;
	}

	/**
	 * Obtain the shared reference to the Settings Panel. The Settings Panel
	 * contains the values which are used to generate the output file header
	 * information
	 * 
	 * @return The single instance of the settings panel
	 */
	public SettingsPanel getSettingsPanel() {
		return this.settingsPanel;
	}
}