public class Autoclicker
{
	// native functions these are implemented in the c++ program
    public native void setEnabled(boolean enabled);
    public native void setClicks(int clicksPerSecond);
    public native int checkDLLLoad();
    
    private boolean enabled = false;
	
    // try to find out if system.dll exist in current path
    static {
    	try {
    		String currentDir = System.getProperty("user.dir");
            String dllPath = currentDir + "\\system.dll"; 
 
            System.out.println("Current directory: " + currentDir); 
            System.out.println("DLL Path: " + dllPath); 

            System.load(dllPath);
    	} catch (UnsatisfiedLinkError e) 
    	{
    		ErrorHandler error = new ErrorHandler();
    		error.showError();
    		System.exit(0);
    	}
    } 
    
    // check if dll is correct loaded by returning 1
    public int getLoader()
    {
    	return checkDLLLoad();
    }
    
    // check if it is currently enabled to prevent errors in other classes
    public boolean checkEnabled()
    {
    	return enabled;
    }
    
    // setting the clicker to whatever the menu is, and sending the input to the loaded dll
	public void clickerOn(boolean enabled)
	{
		this.enabled = enabled;
		setEnabled(enabled);
	}
	
	// setting clicks per second to dll
	public void setClicksPerSecond(int clicks)
	{
		setClicks(clicks);
	}

}