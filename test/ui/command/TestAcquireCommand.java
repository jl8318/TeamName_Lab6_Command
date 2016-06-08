package ui.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import lifeform.LifeForm;
import lifeform.MockLifeForm;

import org.junit.Test;

import weapon.MockWeapon;
import weapon.Weapon;
import environment.Environment;

/**
 * Test AcquireCommand Class.
 * 
 * @author Jixiang Lu
 *
 */
public class TestAcquireCommand
{

	@Test
	public void testInitialization()
	{
		LifeForm life = new MockLifeForm("Bob",100);
		Command acquire = new AcquireCommand(life);
		assertTrue(acquire instanceof Command);
	}
	
	/**
	 * 
	 */
	@Test
	public void testExecute()
	{
		Environment env = Environment.getWorldInstance();
		LifeForm life = new MockLifeForm("Bob",100);
		Command acquire = new AcquireCommand(life);
		env.addLifeForm(0, 0, life);
		
		//LifeForm has no weapon and there is no weapon in the Cell.
		String infor = acquire.execute();
		assertEquals(infor,"No Weapon can be picked up.");
		assertNull(life.getWeapon());
		
		//LifeForm has a weapon and there is no weapon in the Cell.
		Weapon wp = new MockWeapon(15,15,1,2);
		life.pickUpWeapon(wp);
		infor = acquire.execute();
		assertEquals(infor,"No Weapon can be picked up.");
		assertEquals(wp,life.getWeapon());
		
		//LifeForm has no weapon and there is one weapon at slot 1.
		life.dropWeapon();
		env.addWeapon(0, 0, wp);
		infor = acquire.execute();
		assertEquals(infor,"Weapon has been picked up.");
		assertEquals(life.getWeapon(),wp);
		assertNull(env.getWeapon(0, 0, 0));
		
		//LifeForm has weapon and there is two weapon.
		Weapon wp2 = new MockWeapon(15,15,1,2);
		Weapon wp3 = new MockWeapon(15,15,1,2);
		env.addWeapon(0, 0, wp2);
		env.addWeapon(0, 0, wp3);
		infor = acquire.execute();
		assertEquals(infor,"Weapon has been picked up.");
		assertEquals(life.getWeapon(),wp2);
		
		assertEquals(wp3,env.getWeapon(0, 0, 0));
		assertEquals(wp,env.getWeapon(0, 0, 1));
		

		
	}

}
