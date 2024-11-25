import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CreateFrame extends JFrame
{

	// variables for frame size
	private static int width = 250;
	private static int height = 200;
	
	// value for clicker slider
	private int autoclickerVal = 12;
	// value if autoclicker is activated
	private boolean activated = false;
	
	// menu features
	private JLabel m_clicksLabel = null;
	private JSlider m_clicksPerSecond = null;
	private JButton m_activatorClicker = null;
	
	// object for autoclicker
	Autoclicker clicker = null;
	
	// reupdate slider text and if system.dll exists it will set the clicks through the Autoclicker class
	public void reupdateSliderText()
	{
		autoclickerVal = m_clicksPerSecond.getValue();
		
		if (clicker.getLoader() == 1)
		{
			clicker.setClicksPerSecond(autoclickerVal);
		}
		
		String sliderVal = Integer.toString(autoclickerVal);
		m_clicksLabel.setText("Clicks Per Second: " + sliderVal);
		this.repaint();
		this.revalidate();
	}
	
	// change if activated or not
	public void activateHook()
	{
		activated = !activated;
		
		if (clicker.getLoader() == 1)
		{
			clicker.clickerOn(activated);
		}

		
		if (!activated)
		{
			m_activatorClicker.setText("Activate Clicker");
		} else {
			m_activatorClicker.setText("Deactivate Clicker");
		}
		
		this.repaint();
		this.revalidate();
	}
	
	public CreateFrame(String title)
	{
		clicker = new Autoclicker();

		this.setTitle(title);
		this.setSize(width, height);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
		
		// exit_on_close didn't work with unloading the dll
		// so i had to override window closing to use system.exit(0);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
         @Override
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
             clicker.setEnabled(false);
             System.exit(0);
         }
      });
		
		JPanel contentPane = new JPanel();
      this.setContentPane(contentPane);
		
		m_clicksLabel = new JLabel();
		m_clicksLabel.setText("Clicks Per Second:");
		m_clicksLabel.setBounds(55, 5, 180, 40);
		m_clicksLabel.setVisible(true);
		
		m_clicksPerSecond = new JSlider(0, 20, 12);
		m_clicksPerSecond.setBounds(10, 30, 215, 40);
		m_clicksPerSecond.addChangeListener(e -> reupdateSliderText());
		m_clicksPerSecond.setVisible(true);
		
		m_activatorClicker = new JButton();
		m_activatorClicker.setText("Activate Clicker");
		m_activatorClicker.setBounds(10, 60, 215, 60);
		m_activatorClicker.addActionListener(e -> activateHook());
		m_activatorClicker.setVisible(true);
		
		
		this.add(m_clicksLabel);
		this.add(m_clicksPerSecond);
		this.add(m_activatorClicker);
		
		
		// need to re-update slider 
		reupdateSliderText();

	}
}