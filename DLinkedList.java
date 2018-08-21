///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Cloud.java
// File:             DLinkedList.java
// Semester:         CS 367 Summer 2017
//
// Author:           Utkarsh Maheshwari umaheshwari@wisc.edu
// CS Login:         maheshwari
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Jared Akers
// Email:            jakers@wisc.edu
// CS Login:         akers
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Class is a doubly linked list of DblListnodes which uses a tail reference. 
 * 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 * @param <E> 
 */
public class DLinkedList<E> implements ListADT {

	/**Head reference of list*/
	private DblListnode<E> head;

	/**Tail reference of list*/
	private DblListnode<E> tail;

	/**Number of items in the list*/
	private int numItems;

	/**
	 * Default constructor
	 */
	public DLinkedList() {
		head = null;
		tail = null;
		numItems = 0;
	}

	/**
	 * This method adds an item onto the end of the list and updates
	 * the tail reference accordingly.
	 * 
	 * @param item Object that will become data field of the new DblListnode
	 */
	@Override
	public void add(Object item) {

		if (item == null)
			throw new IllegalArgumentException();

		/**Create a new node with its data field being item*/
		DblListnode<E> newNode = new DblListnode<E>((E)item);

		// Adding to a list that did not previously exist
		if (head == null) {
			head = newNode;
			tail = newNode;
		}

		// Adding to a list with at least one node previously in it
		else {

			/**Reference that will traverse the list*/
			DblListnode<E> curr = head;

			// Iterate until the end of the list
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			
			curr.setNext(newNode); // Have last node in list point to the new node
			newNode.setPrev(curr); // Prev field of new node to point back to curr
			
			tail = tail.getNext(); // Update tail reference

		}
		numItems++;
	}

	/**
	 * This method creates a new list node and adds it to the list at the 
	 * specified position. It will update the tail if necessary and throw an
	 * exception if input is bad.
	 * 
	 * @param pos Position to insert item.
	 * @param item Object that will become the data field of a new node.
	 */
	@Override
	public void add(int pos, Object item) 
			throws NullPointerException, IndexOutOfBoundsException {

		// Throw exception if pos is larger than the list or a negative number
		if(pos >= numItems || pos < 0) { 
			throw new IndexOutOfBoundsException();
		}

		if (item == null) {
			throw new IllegalArgumentException();
		}

		/**Create reference node for traversal*/
		DblListnode<E> pointer = head;     

		// Iterate until position
		for(int i = 1; i < pos; i++) {     
			pointer = pointer.getNext();
		}

		/**Create node with item parameter in data field*/
		DblListnode<E> newNode = new DblListnode<E>((E)item);

		// Use the add() method if adding to the end of the list
		if(pointer.getNext() == null){
			add(item);
		}
		
		//Adding to position 0 of list and changing the head reference
		else if (pos == 0) {
			head = newNode;
			newNode.setNext(pointer);
			pointer.setPrev(newNode);
		}

		else {
			// Store node that will be directly to the right of new node's position
			DblListnode<E> rightOfInsert = pointer.getNext();  
			
			pointer.setNext(newNode); // Have node left of insert point to new node
			
			// Have newNode point to node right of insert
			newNode.setNext(rightOfInsert); 
			
			newNode.setPrev(pointer); // Have newNode's Prev point left of insert
			
			// Node right of pos points back to newNode
			rightOfInsert.setPrev(newNode); 
		}
		numItems++; 

	}

	/**
	 * Method checks to see if the data component of any of the nodes in the
	 * list contains the data passed in @param item. 
	 * 
	 * @param item Object to search for in list 
	 * @return boolean True if the item is in the list, otherwise false
	 */
	@Override
	public boolean contains(Object item) throws IllegalArgumentException {

		/**Create reference for traversal*/
		DblListnode<E> pointer = head;

		/* Will execute if no nodes exist in the list or if a null Object is 
		   given as a parameter.*/
		if (item == null || pointer == null) {
			throw new IllegalArgumentException(); 
		}

		// Search list for node with 'item' in data field
		while (pointer != null) {
			
			if (pointer.getData().equals(item))
				return true;

			pointer = pointer.getNext();
		}
		return false;
	}
	
	/**
	 * This method will output the contents of a node's data field at a
	 * given position 'pos'. Method will throw an exception if the pos parameter
	 * is out of bounds or the list does not exist. 
	 * 
	 * @param pos Position of node to get data from.
	 * @return Object Data field of node at 'pos'
	 */
	@Override
	public Object get(int pos) 
			throws IndexOutOfBoundsException, IllegalArgumentException {

		// Throw exception if pos is larger than the list or a negative number
		if (pos<0 || pos >= numItems) {
			throw new IndexOutOfBoundsException();
		}

		// Throw exception if the list does not exist
		if (head == null) { 
			throw new IllegalArgumentException(); 
		}

		/**Create reference for traversal*/
		DblListnode<E> pointer = head;

		//Move reference to position
		for (int i=0; i<pos; i++){
			pointer = pointer.getNext();
		}

		return pointer.getData();
	}

	/**
	 * Method checks to see if any nodes are in the list. 
	 * 
	 * @return boolean True if no nodes are in the list, otherwise false
	 */
	@Override
	public boolean isEmpty() {
		
		try{
			
			if (head == null)
				throw new NullPointerException();
		}
		catch (NullPointerException excpt){
			return true;
		}
		return false;
	}

	/**
	 * Method removes a node from the specified position if the position exists
	 * in the list. 
	 * 
	 * @param pos Position of node that will be removed 
	 * @return Contents of removed nodes data field.
	 */
	@Override
	public Object remove(int pos) 
			throws IndexOutOfBoundsException, IllegalArgumentException {

		/**Create reference for traversal*/
		DblListnode<E> pointer = head;

		/**Node whose data field will be returned*/
		DblListnode<E> removedNode = null;

		// Throws exception if pos is larger than the list or a negative number
		if (pos<0 || pos >= numItems) { 
			throw new IndexOutOfBoundsException();
		}

		//  Throws exception if no nodes exist in the list
		if (pointer == null){
			throw new IllegalArgumentException();
		}

		// Move pointer to position pos, pointer will reference the node that 
		// will be removed
		for(int i = 0; i < pos; i++) {
			pointer = pointer.getNext();
		}

		// Special case for removing first node in the list
		if(pointer.getPrev() == null) { 

			// Case for removing first node in a one node list
			if (pointer.getNext() == null) {
				removedNode = pointer; // Update to node located at pos

				head = null;
				tail = null;
			}

			// Case for removing first node in a list with multiple nodes
			else {
				removedNode = pointer; // Update to node located at pos

				// New node at first position should point back to null
				pointer.getNext().setPrev(null); 
				head = pointer.getNext(); // Update head reference
			}
		}

		// Removing the last node in a list
		else if (pointer.getNext() == null) {
			removedNode = pointer; // Update to node located at pos 

			pointer.getPrev().setNext(null); // Removing node
			tail = pointer.getPrev(); // Update tail reference 
		}

		/* Removing a node from list which has Next and Prev fields 
		 * pointing to other nodes.*/
		else {
			removedNode = pointer; // Update to node located at pos

			/** Node that is to the right of the node to be removed*/
			DblListnode<E> rightOfNode = pointer.getNext();

			// Change Prev field of node right of pointer to node left of pointer
			rightOfNode.setPrev(pointer.getPrev());

			// Change Next field of node left of pointer to node right of pointer
			pointer.getPrev().setNext(rightOfNode);  

		}
		return removedNode.getData();
	}

	/**
	 * @return int The number of items in the list
	 */
	@Override
	public int size() {
		int size = 1;

		/**Create reference for traversal*/
		DblListnode<E> pointer = head;

		try{
			
			// Traverse until the end of the list is reached
			while (pointer.getNext() != null) {
		
				size++;
				pointer = pointer.getNext();
			}
	
			numItems = size;
			return size;
		}
		catch(NullPointerException excpt){
			return 0;
		}

	}
}
