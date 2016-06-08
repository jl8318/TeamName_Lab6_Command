package environment;

import lifeform.LifeForm;
import weapon.Weapon;
import exceptions.EnvironmentException;

/**
 * A Environment hold a 2-Dimensional array of Cell.
 * 
 * @author Jixiang Lu
 *
 */
public class Environment
{
	private Cell[][] theCells;
	private static Environment theWorld;
	private int row;
	private int col;
	
	/**
	 * Create an Environment. If the value of row or the value of column is invalid, the 
	 * constructor will create an Environment with 10 rows and 10 columns.
	 * 
	 * @param row the index of row
	 * @param col the index of column
	 */
	private Environment(int row, int col)
	{
		try{
			this.row = row;
			this.col = col;
			theCells = new Cell[row][col];
			for(int i = 0;i<row;i++)
			{
				for(int j = 0; j<col;j++)
				{
					theCells[i][j]=new Cell();
				}
			}
		}
		catch(NegativeArraySizeException ex)
		{
			this.row = 10;
			this.col = 10;
			theCells = new Cell[10][10];
			for(int i = 0;i<10;i++)
			{
				for(int j = 0; j<10;j++)
				{
					theCells[i][j]=new Cell();
				}
			}
		}
		
	}
	
	/**
	 * Create an Environment instance with the row and the column and return the Environment
	 * has been created.
	 * 
	 * @param row the index of row
	 * @param col the index of column
	 * @return the Environment has been created
	 */
	public static synchronized Environment createWorldInstance(int row, int col)
	{
		theWorld = new Environment(row,col);
		return theWorld;
	}
	
	/**
	 * Gets the Environment has been created. If the Environment is not created, it will create an 
	 * Environment with 10 rows and 10 columns.
	 * 
	 * @return the Environment
	 */
	public  static synchronized Environment getWorldInstance()
	{
		if(theWorld == null)
		{
			theWorld = new Environment(10,10);
		}
		return theWorld;
	}

	/**
	 * Gets a LifeForm at Cell theCells[row][col].
	 * Return LifeForm if there is a LifeForm in the Cell,
	 * null if the row and col are invalid or there is not
	 * LifeForm in the Cell.
	 * @param row the index of row
	 * @param col the index of column
	 * @return LifeForm if there is a LifeForm in the Cell,
	 * null if the row and col are invalid or there is not
	 * LifeForm in the Cell.
	 * 
	 */
	public LifeForm getLifeForm(int row, int col)
	{
		try
		{
			return theCells[row][col].getLifeForm();
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}

	/**
	 * Adds a LifeForm to the Cell the Cell[row][col].
	 * Will not add the LifeForm if the row and col
	 * are invalid or if a LifeForm is already in that Cell.
	 * Return true if successfully added, false otherwise.
	 * 
	 * @param row the index of row
	 * @param col the index of column
	 * @param entity the entity is used  added into the Cell
	 * @return true if successfully added, false otherwise.
	 */
	public boolean addLifeForm(int row, int col, LifeForm entity)
	{
		try
		{
			boolean result = theCells[row][col].addLifeForm(entity);
			entity.setX_coordinate(row);
			entity.setY_coordinate(col);
			return result;
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return false;
		}
		
		
	}

	/**
	 * Removes the LifeForm at theCells[row][col].
	 * Returns the LifeForm removed(null if no LifeForm in 
	 * the Cell or the row or the col is invalid)
	 * 
	 * @param row the index of row
	 * @param col the index of column
	 * @return LifeForm removed or null
	 */
	public LifeForm removeLifeForm(int row, int col)
	{
		try
		{
			LifeForm result = theCells[row][col].removeLifeForm();
			if(result !=null)
			{
				result.setX_coordinate(-1);
				result.setY_coordinate(-1);
			}
			return result;
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	
	/**
	 * Gets the number of rows.
	 * @return the number of rows.
	 */
	public int getNumberOfRow()
	{
		return this.row;
	}
	
	/**
	 * Gets the number of columns.
	 * @return the number of columns.
	 */
	public int getNumberOfCol()
	{
		return this.col;
	}
	
	/**
	 * Clear all the elements which are stored in the Environment.
	 */
	public void clearBoard()
	{
		for(int i = 0; i<this.row;i++)
		{
			for(int j = 0; j<this.col;j++)
			{
				this.theCells[i][j].clearCell();
			}
		}
		
	}

	/**
	 * Gets the index's Weapon which is stored in the theCells[row][col].
	 * If one or more of the parameters is invalid, return null.
	 * 
	 * @param row the index of row
	 * @param col the index of col
	 * @param index the index of Weapons
	 * @return
	 */
	public Weapon getWeapon(int row, int col, int index)
	{
		try
		{ 
			return theCells[row][col].getWeapon(index);
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}

	/**
	 * Adds the Weapon into theCellsp[row][col]. If the operation is executed successfully,
	 * return true. Otherwise, return false.
	 * 
	 * @param row the index of row
	 * @param col the index of col
	 * @param wp the Weapons will be added
	 * @return true or false
	 */
	public boolean addWeapon(int row, int col, Weapon wp)
	{
		try
		{
			return theCells[row][col].addWeapon(wp);
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return false;
		}
	}

	/**
	 * Gets index of the Weapon which is stored in theCell[row][col]. 0 or 1 is valid value.
	 * -1 means the Weapon which is not stored in this Cell. -2 means the index of row or the
	 * index of column is invalid.
	 *  
	 * @param row the index of row
	 * @param col the index of column
	 * @param wp the method will return the index of this Weapon
	 * @return 0-1 or -1 or -2
	 */
	public int getIndexOfWeapon(int row, int col, Weapon wp)
	{
		try
		{
			return theCells[row][col].indexOfWeapon(wp);
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return -2;
		}
	}

	/**
	 * Removes the Weapon which is stored in theCell[row][col].
	 * If the Weapon is removed successfully, return the Weapon. Otherwise, return null.
	 * 
	 * @param row the index of row
	 * @param col the index of column
	 * @param wp the Weapon will be removed
	 * @return the removed Weapon or null
	 */
	public Weapon removeWeapon(int row, int col, Weapon wp)
	{
		try
		{
			return theCells[row][col].removeWeapons(wp);
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}

	/**
	 * Gets the distance from LifeForm A to LifeForm B.Each Cell is considered to be 5 feet square.
	 * If at least one the LifeForm is not in the Environment, throws the EnvironmentException.
	 * 
	 * @param lifea LifeForm A
	 * @param lifeb LifeForm B
	 * @return the distance from A to B 
	 * @throws EnvironmentException at least one the LifeForm is not in the Environment
	 */
	public int getDistance(LifeForm lifea, LifeForm lifeb) throws EnvironmentException
	{
		if(lifea!=null&&lifeb!=null&&lifea.getX_coordinate()>=0&&lifea.getY_coordinate()>=0&&
				lifeb.getX_coordinate()>=0&&lifeb.getY_coordinate()>=0)
		{
			double x = (double)Math.abs(lifea.getX_coordinate()-lifeb.getX_coordinate())*5;
			double y = (double)Math.abs(lifea.getY_coordinate()-lifeb.getY_coordinate())*5;
			int distance = (int)Math.hypot(x, y);
			return distance;
		}
		else
		{
			throw new EnvironmentException("At least one of LifeForm is not in the environment!");
		}

			
	}


	
}
