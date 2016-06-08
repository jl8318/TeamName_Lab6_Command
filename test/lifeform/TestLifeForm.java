package lifeform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
import environment.Environment;
import exceptions.EnvironmentException;
import gameplay.SimpleTimer;

/**
 * Test the functionality provided by the LifeForm class.
 * 
 * @author Jixiang Lu
 *
 */
public class TestLifeForm 
{
	
	/*
	 *This section for Lab 5. 
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
	 * Test the x coordinate and y coordinate.
	 */
	@Test
	public void testCoordinate()
	{
		LifeForm life = new MockLifeForm("Bob",100); 
		//if the x-coordinate or y-coordinate is negative, the LifeForm is not in the Environment.
		assertEquals(-1,life.getX_coordinate());
		assertEquals(-1,life.getY_coordinate());
		life.setX_coordinate(6);
		life.setY_coordinate(0);
		assertEquals(6,life.getX_coordinate());
		assertEquals(0,life.getY_coordinate());
		
	}
	
	/*
	 * Modified by the requirement of Lab 5. 
	 */
	 /**
	  * Test new attack method with weapon.
	  */
	 @Test
	 public void testAttackWeapon() throws EnvironmentException
	 {
		 Environment env = Environment.createWorldInstance(10, 10);
		 LifeForm entity = new MockLifeForm("Bob",500);
		 LifeForm entity2 = new MockLifeForm("Jack",600);
		 entity.setAttackStrength(5);
		 Weapon wp = new PlasmaCannon();
		 Weapon wp2 = new Pistol();
		 //no weapon, reload weapon do nothing.
		 entity.reloadWeapon();
		 
		 entity.pickUpWeapon(wp);
		 //5<distance<10, distance =7
		 env.addLifeForm(0, 0, entity);
		 env.addLifeForm(1, 1, entity2);
		 entity.attack(entity2, 1);
		 assertEquals(550,entity2.getCurrentLifePoints());
		 //actual rate fire = 0,using attack strength
		 assertEquals(0,entity.getWeapon().getActualRateFire());
		 entity.attack(entity2, 2);
		 assertEquals(545,entity2.getCurrentLifePoints());
		 entity.getWeapon().setActualRateFire(3);
		 entity.attack(entity2, 3);
		 assertEquals(471,entity2.getCurrentLifePoints());
		 assertEquals(0,entity.getWeapon().getActualAmmo());
		 //no ammo, 5<distance<10, using attack strength
		 entity.attack(entity2);
		 assertEquals(466,entity2.getCurrentLifePoints());
		 //no ammo, no rate fire
		 entity.getWeapon().setActualRateFire(0);
		 entity.attack(entity2);
		 assertEquals(461,entity2.getCurrentLifePoints());
		 //distance>10,distance=15
		 entity.getWeapon().setActualAmmo(1);
		 entity.getWeapon().setActualRateFire(1);
		 env.removeLifeForm(1, 1);
		 env.addLifeForm(0, 3, entity2);
		 entity.attack(entity2,1);
		 assertEquals(449,entity2.getCurrentLifePoints());
		 //no ammo, distance>10
		 entity.attack(entity2,2);
		 assertEquals(449,entity2.getCurrentLifePoints());
		 //no weapon, distance>10
		 entity.dropWeapon();
		 entity.attack(entity2,2);
		 assertEquals(449,entity2.getCurrentLifePoints());
		 //no weapon, distance < 10.
		 entity.attack(entity2,2);
		 assertEquals(449,entity2.getCurrentLifePoints());
		 
		 //distance<=5, distance =5
		//no weapon
		 env.removeLifeForm(0, 3);
		 env.addLifeForm(0, 1, entity2);
		 entity.attack(entity2);
		 assertEquals(444,entity2.getCurrentLifePoints());
		 
		 //with another weapon
		 entity.pickUpWeapon(wp2);
		 entity.attack(entity2);
		 assertEquals(439,entity2.getCurrentLifePoints());
		
		 //distance> maxRange,distance =30;
		 env.removeLifeForm(0, 1);
		 env.addLifeForm(0, 6, entity2);
		 entity.attack(entity2);
		 assertEquals(439,entity2.getCurrentLifePoints());
		 //test different distance is works. distance =15
		 env.removeLifeForm(0, 6);
		 env.addLifeForm(0, 3, entity2);
		 entity.attack(entity2);
		 assertEquals(433,entity2.getCurrentLifePoints());
		 //distance =10
		 env.removeLifeForm(0, 3);
		 env.addLifeForm(0, 2, entity2);
		 entity.getWeapon().setActualRateFire(2);
		 entity.attack(entity2,1);
		 assertEquals(425,entity2.getCurrentLifePoints());
		 assertEquals(7,entity.getWeapon().getActualAmmo());
		 
		 //test reload method.
		 entity.reloadWeapon();
		 assertEquals(10,entity.getWeapon().getActualAmmo());
				 
	 }
	
	/*
	 * This section for Lab 4-Decorator Lab 
	 */
	/**
	 * Test the LifeForm can pick and drop a weapon.
	 */
	 @Test
	 public void testDropOrPickWeapon()
	 {
		 LifeForm life = new MockLifeForm("Bob",100); 
		 //initialization with no weapon.
		 assertNull(life.getWeapon());
		 //when life w/o weapon, it can dropWeapon
		 life.dropWeapon();
		 assertNull(life.getWeapon());
		 		 
		 //When life w/o weapon, it can pickup weapon.
		 Weapon wp = new Pistol();
		 life.pickUpWeapon(wp);
		 assertEquals(wp,life.getWeapon());
		 //when life w/ weapon, it can pick up another weapon.
		 Weapon wp2 = new PlasmaCannon();
		 life.pickUpWeapon(wp2);
		 assertEquals(wp,life.getWeapon());
		 
		 //drop weapon
		 life.dropWeapon();
		 assertNull(life.getWeapon());
		 
		 //pickup another weapon
		 life.pickUpWeapon(wp2);
		 assertEquals(wp2,life.getWeapon());	 
		 
	 }
	 
	
	/*
	 *This Section for updating LifeForm so that the LifeForms can
	 *attack each other. It is still for the Strategy Pattern. (Lab 3)
	 */
	/**
	 * Test Attack function and the attackStrength instance variable.
	 */
	@Test
	public void testAttack() throws EnvironmentException
	{
		Environment env = Environment.createWorldInstance(10, 10);
		LifeForm entity = new MockLifeForm("Bob",50);
		LifeForm entity2 = new MockLifeForm("Jack",60);
		env.addLifeForm(0, 1, entity);
		env.addLifeForm(0, 0, entity2);
		
		//test attackStrength instance variable
		assertEquals(0,entity.getAttackStrength());
		entity.setAttackStrength(5);
		assertEquals(5,entity.getAttackStrength());
		
		//test attack method
		entity.attack(entity2);
		assertEquals(55, entity2.getCurrentLifePoints());
		entity.setAttackStrength(60);
		//test the LifePoint can drop below 0.
		entity.attack(entity2);
		assertEquals(0,entity2.getCurrentLifePoints());
		entity.attack(entity2);
		assertEquals(0,entity2.getCurrentLifePoints());
		
		//test dead can't attack
		entity2.setAttackStrength(5);
		entity2.attack(entity);
		assertEquals(50,entity.getCurrentLifePoints());
				
	}
	
	/**
	 * Test update method.
	 */
	@Test
	public void testUpdate()
	{
		LifeForm entity = new MockLifeForm("Bob",50);
		SimpleTimer st = new SimpleTimer();
		st.addTimeObserver(entity);
		st.timeChanged();
		assertEquals(1,entity.getTime());
	}
	
	/**
	 * Test setTime method
	 */
	@Test
	public void testSetTime()
	{
		LifeForm entity = new MockLifeForm("Bob",50);
		entity.setTime(5);
		assertEquals(5,entity.getTime());
	}
	
	/*
	 * Start Section for Strategy Pattern Tests.(Lab 1 and Lab 2)
	 */
	/**
	 * When a LifeForm is created, it should know its name and how
	 * many life points it has.
	 */
	@Test
	public void testInitialization() 
	{
		LifeForm entity;
		entity = new MockLifeForm("Bob",40);
		assertEquals("Bob",entity.getName());
		assertEquals(40,entity.getCurrentLifePoints());
		
		entity = new MockLifeForm("Bob",0);
		assertEquals(null,entity.getName());
		assertEquals(0,entity.getCurrentLifePoints());
	}
	
	/**
	 * Test setCurrentLifePoints method. The LifePoint can 
	 * not be below 0.
	 */
	@Test
	public void testSetCurrentLifePoints()
	{
		LifeForm entity = new MockLifeForm("Bob",40);
		entity.setCurrentLifePoints(50);
		assertEquals(50,entity.getCurrentLifePoints());
		entity.setCurrentLifePoints(-100);
		assertEquals(0,entity.getCurrentLifePoints());
		
	}
	
	/**
	 * Test the takeHit method. This method can reduce the amount of
	 *  LifePoint.
	 */
	@Test
	public void testTakeHit()
	{
		LifeForm entity = new MockLifeForm("Bob",40);
		entity.takeHit(20);
		assertEquals(20,entity.getCurrentLifePoints());
		entity.takeHit(5);
		assertEquals(15,entity.getCurrentLifePoints());
		
		//The LifePoint cannot go below 0 life point.
		entity.takeHit(100);
		assertEquals(0,entity.getCurrentLifePoints());
		
		//Take attack when the currentLifepoint is 0.
		entity.takeHit(10);
		assertEquals(0,entity.getCurrentLifePoints());
	}
	


}
