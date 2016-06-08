package ui.command;

import lifeform.LifeForm;

/**
 * The class represents a command. Call the Command to reload the weapon the LifeForm
 * is holding.
 * 
 * @author Jixiang Lu
 *
 */
public class ReloadCommand implements Command
{
	private LifeForm life;
	
	/**
	 * Construct a ReloadCommand.
	 * 
	 * @param life the LifeForm can be manipulated by the Command.
	 */
	public ReloadCommand(LifeForm life)
	{
		this.life = life;
	}

	/**
	 * Reloads the weapon the LifeForm is holding.
	 */
	@Override
	public String execute()
	{		
		life.reloadWeapon();
		return "The Weapon has been reloaded!";
	}

}
