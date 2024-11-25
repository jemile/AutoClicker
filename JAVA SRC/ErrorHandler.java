import javax.swing.JOptionPane;


public class ErrorHandler
{
	
	public ErrorHandler()
	{
	}
	
	public void showError()
	{
        JOptionPane.showMessageDialog(null, "Couldn't find system.dll", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}