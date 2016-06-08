package environment;

import lifeform.LifeForm;
import weapon.Weapon;

/**
 * A Cell that can hold a LifeForm.
 * 
 * @author Jixiang Lu
 *
 */
public class Cell
{

	private LifeForm entity;
	private Weapon[] weapons;

	/**
	 * Create a Cell without LifeForm and Weapons.
	 */
	public Cell()
	{
		entity = null;
		weapons = new Weapon[2];
	}

	/**
	 * @return the LifeForm in this Cell
	 */
	public LifeForm getLifeForm()
	{

		return entity;
	}

	/**
	 * Tries to add the LifeForm to the Cell. Will not add if a LifeForm is
	 * already present.
	 * 
	 * @param life
	 *            the LifeForm will be tried to add the Cell.
	 * @return true if the LifeForm was added to the Cell, false other wise.
	 */
	public boolean addLifeForm(LifeForm life)
	{
		if (entity == null)
		{
			this.entity = life;
			return true;
		} 
		else
		{
			return false;
		}
	}

	/**
	 * Removes the LifeForm in the Cell.
	 *
	 * @return the LifeForm removed, null if none present.
	 */
	public LifeForm removeLifeForm()
	{
		if (this.entity != null)
		{
			LifeForm temp = entity;
			entity = null;
			return temp;
		} 
		else
		{
			return null;
		}

	}


	/**
	 * Return true, if the Weapon is added into the Cell successfully or the weapon has been 
	 * stored in the Cell. Otherwise, return false. There are only two slot to store weapon.
	 * If the first slot is null, there is nothing in the second slot.
	 * 
	 * @param wp the weapon is tried adding into the Cell
	 * @return  True if add successfully. Otherwise, return false
	 */
	public boolean addWeapon(Weapon wp)
	{
		if(weapons[0]==null )
		{
			weapons[0]= wp;
			return true;
		}
		else if(weapons[1]==null && this.indexOfWeapon(wp)==-1)
		{
			weapons[1]= wp;
			return true;
		}
		else
		{
			if(this.indexOfWeapon(wp)!=-1)
				return true;
			return false;
		}
		
	}
	
	/**
	 * Gets the Weapon that is stored in the index's slot.
	 * If the index is invalid, it will return null.
	 * The index of first slot is 0 and the index of second slot is 1.
	 * 
	 * @param index the index of slots
	 * @return the Weapons or null
	 */
	public Weapon getWeapon(int index)
	{
		try
		{
			return weapons[index];
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}

	/**
	 * If the weapons exist in the Cell, return its index in the array.
	 * Otherwise, return -1.
	 * @param wp the weapon will be used to check whether it exist in the Cell.
	 * @return -1 or the index of weapon
	 */
	public int indexOfWeapon(Weapon wp)
	{
		for(int i=0;i<weapons.length;i++ )
		{
			if(weapons[i]==wp)
				return i ;
		}
		return -1;
	}

	/**
	 * Remove the weapon exists in the slots. If the weapon exists in the slot and is removed successfully,
	 * return the Weapon. Otherwise, return null.
	 * If there are two Weapons in the Cell and the Weapon that is in the first slot is removed, the other
	 * Weapon will be moved from the second slot to first slot.
	 *  
	 * @param wp the Weapon will be removed
	 * @return the Weapon has been removed or null
	 */
	public Weapon removeWeapons(Weapon wp)
	{
		int index =indexOfWeapon(wp);
		if(index!=-1)
		{
			Weapon deleted = weapons[index];
			weapons[index]=null;
			if(index==0)
			{
				weapons[0]=weapons[1];
				weapons[1]=null;
			}
			return deleted;
		}
		return null;
	}

	/**
	 * Clear the LifeForm and Weapons in this Cell.
	 */
	public void clearCell()
	{
		this.entity = null;
		weapons[0] = null;
		weapons[1] = null;
		
	}

}
