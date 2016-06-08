package exceptions;

/**
 *  An Exception: when using getDistance(LifeForm a, LifeForm b) and at least one of LifeForms 
 *  is not in the environment, the Exception will be thrown.
 *  
 * @author Jixiang Lu
 *
 */
public class EnvironmentException extends Exception
{
	
	public EnvironmentException(String info)
	{
		super(info);
	}
}
