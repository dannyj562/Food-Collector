/* 
	Danny Rivera
	March 19, 2017
	CSC 311
	Project 02
	Dr. Amlan Chaterjee
*/

public interface BareBonesStack<E>
{
	public E push(E obj);
	public E pop();
	public E peek();
	public boolean empty();	
}