package environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import lifeform.LifeForm;
import lifeform.MockLifeForm;

import org.junit.Before;
import org.junit.Test;

import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
import exceptions.EnvironmentException;

/**
 * The test cases for the Environment class
 * 
 * @author Jixiang Lu
 *
 */
public class TestEnvironment
{
	/*
	 * This part for lab5, all tests are either new or modified. 
	 */

	/**
	 * Re-initialization
	 */
	@Before
	public void clearLifeForm()
	{
		Environment env = Environment.getWorldInstance();
		env.clearBoard();
	}

	/**
	 * At initialization, the Cells in the Environment should be empty and not
	 * contain a LifeForm. And test the createWorldInstance() method.
	 */
	@Test
	public void testInitialization()
	{
		Environment env = Environment.createWorldInstance(5, 5);
		assertEquals(5, env.getNumberOfRow());
		assertEquals(5, env.getNumberOfCol());
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				assertNull(env.getLifeForm(i, j));
			}
		}
		// for createWorldInstance() method:
		// If one of parameters or both of them are negative, the method will
		// create an Environment
		// with 10 rows and 10 columns.
		env = Environment.createWorldInstance(-1, 4);
		assertEquals(10, env.getNumberOfRow());
		assertEquals(10, env.getNumberOfCol());
		assertNull(env.getLifeForm(9, 9));

	}

	/**
	 * Check the addLifeForm method.
	 */
	@Test
	public void testAddLifeForm()
	{
		Environment env = Environment.createWorldInstance(4, 4);
		assertEquals(4, env.getNumberOfRow());
		assertEquals(4, env.getNumberOfCol());
		LifeForm bob = new MockLifeForm("Bob", 40);
		LifeForm luck = new MockLifeForm("Luck", 1);
		// The cell is empty so it is successful to add the life into
		// environment.
		boolean success = env.addLifeForm(1, 2, bob);
		assertTrue(success);
		assertEquals(bob, env.getLifeForm(1, 2));
		// The cell is not empty so the program can add the life into
		// environment.
		success = env.addLifeForm(1, 2, luck);
		assertFalse(success);
		assertEquals(bob, env.getLifeForm(1, 2));
		// The row or col is invalid so it doesn't work.
		success = env.addLifeForm(5, 6, luck);
		assertFalse(success);
		assertNull(env.getLifeForm(5, 6));
		// the row or col is negative
		success = env.addLifeForm(-1, -2, luck);
		assertFalse(success);
		assertNull(env.getLifeForm(-1, -2));

	}

	/**
	 * Test the addWeapon method and getIndexOfWeapon method
	 */
	@Test
	public void testAddWeapon()
	{
		Environment env = Environment.createWorldInstance(4, 4);
		assertEquals(4, env.getNumberOfRow());
		assertEquals(4, env.getNumberOfCol());
		Weapon wp = new Pistol();
		Weapon wp2 = new PlasmaCannon();
		// Initialization
		assertNull(env.getWeapon(2, 2, 0));
		assertNull(env.getWeapon(2, 2, 1));
		int index = env.getIndexOfWeapon(2, 2, wp);
		assertEquals(-1, index);
		// Add one Weapon into environment
		boolean success = env.addWeapon(2, 2, wp);
		index = env.getIndexOfWeapon(2, 2, wp);
		assertTrue(success);
		assertEquals(wp, env.getWeapon(2, 2, index));
		// Add second Weapon into environment
		success = env.addWeapon(2, 2, wp2);
		index = env.getIndexOfWeapon(2, 2, wp2);
		assertTrue(success);
		assertEquals(wp2, env.getWeapon(2, 2, index));
		// Add third Weapon into environment
		Weapon wp3 = new PlasmaCannon();
		success = env.addWeapon(2, 2, wp3);
		index = env.getIndexOfWeapon(2, 2, wp3);
		assertFalse(success);
		assertEquals(-1, index);
		// Add Weapon into A Cell with invalid row or invalid col
		success = env.addWeapon(9, 10, wp);
		;
		// invalid row or col, getIndexOfWeapon will return -2;
		index = env.getIndexOfWeapon(9, 10, wp3);
		assertEquals(-2, index);
		assertFalse(success);
		assertNull(env.getWeapon(9, 10, 0));
		// Add Weapon into A Cell with negative row or negative col
		success = env.addWeapon(-9, -1, wp);
		index = env.getIndexOfWeapon(9, 10, wp3);
		assertEquals(-2, index);
		assertFalse(success);
		assertNull(env.getWeapon(-9, -1, 0));
	}

	/**
	 * Check RemoveLifeForm method.
	 */
	@Test
	public void testRemoveLifeForm()
	{
		LifeForm removeForm;
		Environment env = Environment.getWorldInstance();
		env = Environment.createWorldInstance(2, 3);
		// The cell is empty so it doesn't work.
		removeForm = env.removeLifeForm(1, 2);
		assertNull(removeForm);
		// the row or col is invalid so it doesn't work.
		removeForm = env.removeLifeForm(3, 3);
		assertNull(removeForm);
		// the row or col is negative.
		removeForm = env.removeLifeForm(-1, -2);
		assertNull(removeForm);
		// The cell is not empty so it will remove the entity in the cell.
		LifeForm bob = new MockLifeForm("Bob", 40);
		env.addLifeForm(1, 2, bob);
		removeForm = env.removeLifeForm(1, 2);
		assertEquals(bob, removeForm);
		// The cell has been removed successfully.
		removeForm = env.removeLifeForm(1, 2);
		assertNull(removeForm);
	}

	/**
	 * Test RemoveWeapon method
	 */
	@Test
	public void testRemoveWeapon()
	{
		Environment env = Environment.createWorldInstance(4, 4);
		Weapon wp = new Pistol();
		Weapon wp2 = new PlasmaCannon();
		// remove a Weapon does't exist
		Weapon removed = env.removeWeapon(1, 2, wp);
		assertNull(removed);
		// remove a Weapon with invalid coordinate
		removed = env.removeWeapon(6, 8, wp);
		assertNull(removed);
		// remove a Weapon with negative coordinate
		removed = env.removeWeapon(-1, -3, wp2);
		assertNull(removed);
		// remove a Weapon with valid coordinate.
		env.addWeapon(1, 2, wp);
		env.addWeapon(1, 2, wp2);
		removed = env.removeWeapon(1, 2, wp2);
		assertEquals(removed, wp2);
		removed = env.removeWeapon(1, 2, wp);
		assertEquals(removed, wp);
		// make sure the weapon has been removed
		removed = env.removeWeapon(1, 2, wp);
		assertNull(removed);

	}

	/**
	 * Test getDistance method
	 */
	@Test(expected =EnvironmentException.class)
	public void testGetDistance() throws EnvironmentException
	{
		Environment env = Environment.createWorldInstance(10, 10);
		LifeForm bob = new MockLifeForm("Bob", 40);
		LifeForm luck = new MockLifeForm("Luck", 1);
		//at the same row:
		env.addLifeForm(0, 6, bob);
		env.addLifeForm(0, 1, luck);
		int distance = env.getDistance(bob,luck);
		assertEquals(25,distance);
		//at the same column
		env.removeLifeForm(0, 1);
		env.addLifeForm(5, 5, luck);
		distance = env.getDistance(bob,luck);
		assertEquals(25,distance);
		//at the different row and column
		env.removeLifeForm(5, 5);
		env.addLifeForm(1, 5, luck);
		distance = env.getDistance(bob,luck);
		assertEquals(7,distance);
		// one is not in the environment
		env.removeLifeForm(1, 5);
		distance = env.getDistance(bob,luck);
		
	}
	/**
	 * Test getDistance method. Two LifeForm is not in the Environment.
	 */
	@Test(expected =EnvironmentException.class)
	public void testGetDistanceWithoutP() throws EnvironmentException
	{
		Environment env = Environment.createWorldInstance(10, 10);
		LifeForm bob = new MockLifeForm("Bob", 40);
		LifeForm luck = new MockLifeForm("Luck", 1);
		env.getDistance(bob,luck);
	}
	
	/**
	 * Test getDistance method. One LifeForm is Null.
	 */
	@Test(expected =EnvironmentException.class)
	public void testGetDistanceWithNull() throws EnvironmentException
	{
		Environment env = Environment.createWorldInstance(10, 10);
		LifeForm bob = new MockLifeForm("Bob", 40);
		env.getDistance(bob,null);
	}
	/**
	 * Test getDistance method. Two LifeForm is Null.
	 */
	@Test(expected =EnvironmentException.class)
	public void testGetDistanceWithTwoNull() throws EnvironmentException
	{
		Environment env = Environment.createWorldInstance(10, 10);
		env.getDistance(null,null);
	}

}
