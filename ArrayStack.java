/* 
	Danny Rivera
	March 19, 2017
	CSC 311
	Project 02
	Dr. Amlan Chaterjee
*/
public class ArrayStack<E> implements BareBonesStack<E>
{
	// Storage for the Stack
	private E[] theData;
	// Top of Stack
	private int topOfStack = -1;	// Indicates nothing is there
	private static final int INITIAL_CAPACITY = 100; // Default capacity of the stack

	// Default constructor, creates a stack of default capacity
	public ArrayStack()
	{
		this.theData = (E[]) new Object[INITIAL_CAPACITY];
	}

	// Push Object on the stack
	@Override
	public E push(E obj)
	{
		// This method adds element to the stack if there is space
		// Check if the stack is full
		if(topOfStack == this.theData.length - 1)
		{
			System.out.println("Stack Overflow");
			return null;
		}
		// else there is space available, insert the data
		topOfStack++;
		this.theData[topOfStack] = obj; 
		return obj;
	}

	// Pop object on the stack
	@Override
	public E pop()
	{
		// This method removes elements from the stack if there is something
		// Check for emptiness of the stack
		if(empty())
		{
			System.out.println("Stack Underflow");
			return null;
		}
		
		// There is some element that can be deleted
		return theData[topOfStack--];
	}

	@Override
	public E peek()
	{
		if(empty())
		{
			System.out.println("Error");
		}
		return theData[topOfStack];
	}

	@Override
	public boolean empty()
	{
		return (topOfStack == -1);
	}

	public Object searchStack(Object name, int lastIndex)
	{
		for(int index=0; index<lastIndex; index++)
		{
			if(this.theData[index].equals(name))
			{
				return this.theData[index];
			}
		}
		return null;
	}

	public int getStackIndex()
	{
		return this.topOfStack;
	}

	// Display the content of the stack
	public void display()
	{
		System.out.print("Stack : ");
		for(int i=0; i<= topOfStack; i++)
		{
			System.out.print(theData[i] + " | ");
		}
		System.out.println();
	}
}