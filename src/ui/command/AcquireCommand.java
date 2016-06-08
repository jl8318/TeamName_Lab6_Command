package ui.command;

import lifeform.LifeForm;
import weapon.Weapon;
import environment.Environment;

/**
 * 
 * 
 * @author Jixiang Lu
 *
 */
public class AcquireCommand implements Command
{

	private LifeForm life;
	
	public AcquireCommand(LifeForm life)
	{
		this.life = life;
	}
	
	/**
	 * 
	 */
	@Override
	public String execute()
	{
		Environment env = Environment.getWorldInstance();
		Weapon wp1 = env.getWeapon(life.getX_coordinate(), life.getY_coordinate(), 0);
		//Weapon wp2 = env.getWeapon(life.getX_coordinate(), life.getY_coordinate(), 1);
		if(life.getWeapon() == null)
		{
			if(wp1 != null)
			{
				life.pickUpWeapon(wp1);
				env.removeWeapon(life.getX_coordinate(), life.getY_coordinate(), wp1);
				return "Weapon has been picked up.";
			}
			/*else if(wp2 != null)
			{
				life.pickUpWeapon(wp1);
				env.removeWeapon(life.getX_coordinate(), life.getY_coordinate(), wp2);
				return "Weapon has been picked up.";
			}*/
			else
			{
				return "No Weapon can be picked up.";
			}
		}
		else
		{
			Weapon temp = life.getWeapon();
			if(wp1 != null)
			{
				life.dropWeapon();
				life.pickUpWeapon(wp1);
				env.removeWeapon(life.getX_coordinate(), life.getY_coordinate(), wp1);
				env.addWeapon(life.getX_coordinate(), life.getY_coordinate(), temp);
				return "Weapon has been picked up.";
			}
			/*else if(wp2 != null)
			{
				life.dropWeapon();
				life.pickUpWeapon(wp2);
				env.removeWeapon(life.getX_coordinate(), life.getY_coordinate(), wp2);
				env.addWeapon(life.getX_coordinate(), life.getY_coordinate(), temp);
				return "Weapon has been picked up.";
			}*/
			else
			{
				return "No Weapon can be picked up.";
			}
		}
		
	}

}
