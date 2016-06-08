package ui.command;

import lifeform.LifeForm;
import weapon.Weapon;
import environment.Environment;

/**
 * The class represents a Command. It is used to drop the weapon the LifeForm is holding.
 * If there is no space for Weapon in the Cell, the LifeForm can't drop the Weapon.
 * 
 * @author Jixiang Lu
 *
 */
public class DropCommand implements Command
{
	private LifeForm life;
	
	/**
	 * Construct a DropCommand.
	 * 
	 * @param life the LifeForm can be manipulated by the Command.
	 */
	public DropCommand(LifeForm life)
	{
		this.life = life;
	}
	
	/**
	 * Drops the Weapon which the LifeForm is holding. If there is no space for Weapon in the 
	 * Cell, the LifeForm can't drop the Weapon.
	 */
	@Override
	public String execute()
	{
		Environment env = Environment.getWorldInstance();
		Weapon slot1 = env.getWeapon(life.getX_coordinate(), life.getY_coordinate(), 0);
		Weapon slot2 = env.getWeapon(life.getX_coordinate(), life.getY_coordinate(), 1);
		if(slot1 != null&& slot2 !=null)
			return "Weapon cannot be dropped.";
		else
		{

			env.addWeapon(life.getX_coordinate(), life.getY_coordinate(), life.getWeapon());
			life.dropWeapon();
			return "Weapon has benn dropped";
		}
	}

}
