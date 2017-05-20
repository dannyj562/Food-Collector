/* 
	Danny Rivera
	March 19, 2017
	CSC 311
	Project 02
	Dr. Amlan Chaterjee
*/
import java.util.*;
public class Food
{
	private List<Object> foodList = new ArrayList<Object>();	// List of Food Items

	public Food(List<Object> foodList)
	{
		this.foodList = foodList;	// Copies food Items
	}

	public Object searchFood(Object name, int startIndex)
	{
		// Linear search
		for(int i=startIndex+1; i<foodList.size(); i++)
		{
			if(foodList.get(i).equals(name))
			{
				return name;
			}
		}
		return null;	// unsuccessful search
	}

	public void display()
	{
		for(int i=0; i<foodList.size(); i++)
		{
			System.out.println(foodList.get(i));
		}
		
	}
}