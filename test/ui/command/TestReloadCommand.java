package ui.command;

import static org.junit.Assert.*;
import lifeform.LifeForm;
import lifeform.MockLifeForm;

import org.junit.Test;

import exceptions.EnvironmentException;
import weapon.MockWeapon;
import weapon.Weapon;

/**
 * Test ReloadCommand Class
 * 
 * @author Jixiang Lu
 *
 */

public class TestReloadCommand
{

	/**
	 * Test initialization of ReloadCommand Class and execute() method.
	 * 
	 * @throws EnvironmentException Environment getDistance(life1,life2) has some error.
	 */
	@Test
	public void testInitializationAndExecute() throws EnvironmentException
	{
		LifeForm life = new MockLifeForm("Bob",100);
		Weapon wp = new MockWeapon(50,15,2,5);
		life.pickUpWeapon(wp);
		Command reload = new ReloadCommand(life);
		//Initialization
		assertTrue(reload instanceof Command);
		assertEquals(5,life.getWeapon().getActualAmmo());
		
		//Weapon shoots
		life.getWeapon().getDamage(10);
		assertEquals(4,life.getWeapon().getActualAmmo());
		
		//Weapon reload.
		String st = reload.execute();
		assertEquals("The Weapon has been reloaded!",st);
		assertEquals(5,life.getWeapon().getActualAmmo());
		
	}
	

}
