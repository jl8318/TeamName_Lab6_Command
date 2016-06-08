package environment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import lifeform.LifeForm;
import lifeform.MockLifeForm;

import org.junit.Test;

import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;

/**
 * The test cases for the Cell class.
 * 
 * @author Jixiang Lu
 *
 */
public class TestCell
{

	/*
	 * This Section for Singleton Pattern (Lab 5)
	 */
	/**
	 * At initialization, the Cell should be empty and not contain a LifeForm.
	 */
	@Test
	public void testInitialization()
	{
		Cell cell = new Cell();
		assertNull(cell.getLifeForm());
		assertNull(cell.getWeapon(0));
		assertNull(cell.getWeapon(1));
	}

	/**
	 * Test addWeapon method and indexOfWeapon method.
	 */
	@Test
	public void testAddWeapon()
	{
		Cell cell = new Cell();
		Weapon wp = new Pistol();
		int index = cell.indexOfWeapon(wp);
		//the weapon isn't exist, index =-1
		assertEquals(-1,index);
		//add one weapon
		assertTrue(cell.addWeapon(wp));
		index = cell.indexOfWeapon(wp);
		assertEquals(cell.getWeapon(index),wp);
		assertEquals(0,index);
		//add same weapon. return true, but will not take extra slot.
		assertTrue(cell.addWeapon(wp));
		assertEquals(0,index);
		assertNull(cell.getWeapon(1));
		//add second weapon
		Weapon wp2 = new PlasmaCannon();
		index = cell.indexOfWeapon(wp2);
		assertEquals(-1,index);
		assertTrue(cell.addWeapon(wp2));
		index = cell.indexOfWeapon(wp2);
		assertEquals(1,index);
		assertEquals(cell.getWeapon(index),wp2);
		//add third weapon,return false
		Weapon wp3 = new PlasmaCannon();
		assertFalse(cell.addWeapon(wp3));
		assertEquals(-1,cell.indexOfWeapon(wp3));
		//when the slots is full, add same weapon will return true, but do nothing
		assertTrue(cell.addWeapon(wp));
		assertEquals(0,cell.indexOfWeapon(wp));	
	}
	
	/**
	 * Test the removeWeapon method.
	 */
	@Test
	public void testRemoveWeapon()
	{
		Cell cell = new Cell();
		Weapon wp = new Pistol();
		Weapon wp2 = new PlasmaCannon();
		//normally remove the weapon
		cell.addWeapon(wp);
		assertEquals(wp,cell.removeWeapons(wp));
		assertNull(cell.getWeapon(0));
		//remove the weapon does't exist
		assertNull(cell.removeWeapons(wp));
		//If the weapons' list is full and remove the weapon which is in the first slot,
		//the cell will automatically move the other weapon from second slot to first slot.
		cell.addWeapon(wp);
		cell.addWeapon(wp2);
		assertEquals(wp,cell.removeWeapons(wp));
		assertNull(cell.getWeapon(1));
		assertEquals(0,cell.indexOfWeapon(wp2));
		assertEquals(wp2,cell.getWeapon(0));
		//remove the second weapon;
		cell.addWeapon(wp);
		assertEquals(wp,cell.removeWeapons(wp));
		assertNull(cell.getWeapon(1));
		assertEquals(0,cell.indexOfWeapon(wp2));
		assertEquals(wp2,cell.getWeapon(0));
		
		
	}
	/**
	 * Test ClearCell() method.
	 */
	@Test
	public void testClearCell()
	{
		Cell cell = new Cell();
		Weapon wp = new Pistol();
		Weapon wp2 = new PlasmaCannon();
		LifeForm bob = new MockLifeForm("Bob",40);
		cell.addLifeForm(bob);
		cell.addWeapon(wp);
		cell.addWeapon(wp2);
		assertEquals(bob,cell.getLifeForm());
		assertEquals(wp,cell.getWeapon(0));
		assertEquals(wp2,cell.getWeapon(1));
		cell.clearCell();
		assertNull(cell.getLifeForm());
		assertNull(cell.getWeapon(0));
		assertNull(cell.getWeapon(1));
	}
	
	/**
	 * Test getWeapon(index) method can handle a invalid input.
	 */
	@Test
	public void testGetWeapon()
	{
		Cell cell = new Cell();
		assertNull(cell.getWeapon(-1));
		assertNull(cell.getWeapon(3));
		
	}
	/*
	 * Start Section for Lab 1 Test,(include test the ability to add adn remove a LifeForm)
	 */
	
	/**
	 * Checks to see if we change the LifeForm held by the Cell that
	 * getLifeForm properly responds to this change.
	 * 
	 */
	@Test
	public void testSetLifeForm()
	{
		LifeForm bob = new MockLifeForm("Bob",40);
		LifeForm fred = new MockLifeForm("Fred",40);
		Cell cell = new Cell();
		//The cell is empty so this should work.
		boolean success = cell.addLifeForm(bob);
		assertTrue(success);
		assertEquals(bob,cell.getLifeForm());
		//The cell is not empty so this should fail.
		success = cell.addLifeForm(fred);
		assertFalse(success);
		assertEquals(bob,cell.getLifeForm());
	}

	/**
	 * Check the remove method. If there is an entity
	 * in the Cell, remove it and return it. Otherwise,
	 * return null.
	 * 
	 */
	@Test
	public void testRemoveLifeForm()
	{
		LifeForm removeForm;
		Cell cell = new Cell();
		//The cell is empty so return null.
		removeForm = cell.removeLifeForm();
		assertNull(removeForm);
		LifeForm bob = new MockLifeForm("Bob",40);
		cell.addLifeForm(bob);
		//The cell is not empty so return entity.
		removeForm = cell.removeLifeForm();
		assertEquals(bob,removeForm);
		//The cell has been removed so return null.
		removeForm = cell.removeLifeForm();
		assertNull(removeForm);
	}
	

}
