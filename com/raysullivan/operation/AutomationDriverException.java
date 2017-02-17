package raysullivan.operation;

/**
 * AutomationDriverException
 * @author rsullivan
 *
 */
public class AutomationDriverException  extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * AutomationDriverException
	 */
	public AutomationDriverException(){}
	
	/**
	 * AutomationDriverException
	 * @param message
	 */
	public AutomationDriverException( String message){
		super(message);
	}
	/**
	 * AutomationDriverException
	 * @param cause
	 */
    public AutomationDriverException (Throwable cause) {
        super (cause);
    }
    /**
     * AutomationDriverException
     * @param message
     * @param cause
     */
    public AutomationDriverException (String message, Throwable cause) {
        super (message, cause);
    }
}
