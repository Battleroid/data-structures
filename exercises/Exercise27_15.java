
import java.util.*;

public class Exercise27_15Working {
	public static void main(String[] args){
		java.util.Collection<String> set = new MyHashSet<>();
		set.add("Smith");
		set.add("Anderson");
		set.add("Lewis");
		set.add("Cook");
		set.add("Smith");

		System.out.println("Elements in set: " + set);
		System.out.println("Number of elements in set: " + set.size());
		System.out.println("Is Smith in set? " + set.contains("Smith"));

		set.remove("Smith");
		System.out.print("Names in set in uppercase are ");
		for (String s: set)
			System.out.print(s.toUpperCase() + " ");

		set.clear();
		System.out.println("\nElements in set: " + set);

	}

	public static class MyHashSet<E extends Comparable<E>>implements Collection {
		// Define the default hash table size. Must be a power of 2
		private static int DEFAULT_INITIAL_CAPACITY = 4;

		// Define the maximum hash table size. 1 << 30 is same as 2^30
		private static int MAXIMUM_CAPACITY = 1 << 30; 

		// Current hash table capacity. Capacity is a power of 2
		private int capacity;

		// Define default load factor
		private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f; 

		// Specify a load factor threshold used in the hash table
		private float loadFactorThreshold; 

		// The number of elements in the set
		private int size = 0; 

		// Hash table is an array with each cell that is a linked list
		//-1
		private AVLTree<E>[] table;

		/** Construct a set with the default capacity and load factor */
		public MyHashSet() {  
			this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);    
		}

		/** Construct a set with the specified initial capacity and 
		 * default load factor */
		public MyHashSet(int initialCapacity) { 
			this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);    
		}

		/** Construct a set with the specified initial capacity 
		 * and load factor */
		public MyHashSet(int initialCapacity, float loadFactorThreshold) { 
			if (initialCapacity > MAXIMUM_CAPACITY)
				this.capacity = MAXIMUM_CAPACITY;
			else
				this.capacity = trimToPowerOf2(initialCapacity);

			this.loadFactorThreshold = loadFactorThreshold;    
			table = new AVLTree[capacity];
		}

		@Override /** Remove all elements from this set */ 
			public void clear() {
				size = 0;
				removeElements();
			}

		/** Return true if the element is in the set */
		@Override
		public boolean contains(Object e) {
			int bucketIndex = hash(e.hashCode());
			if (table[bucketIndex] != null) {
				AVLTree<E> bucket = table[bucketIndex]; 
				return bucket.contains(e);
			}

			return false;
		}


		//@Override /** Add an element to the set */
		public boolean add(Object e) {
			if (contains(e)) // Duplicate element not stored
				return false;

			if (size + 1 > capacity * loadFactorThreshold) {
				if (capacity == MAXIMUM_CAPACITY)
					throw new RuntimeException("Exceeding maximum capacity");

				rehash();
			}

			int bucketIndex = hash(e.hashCode());

			// Create a linked list for the bucket if it is not created
			if (table[bucketIndex] == null) {
				table[bucketIndex] = new AVLTree<E>();
			}

			// Add e to hashTable[index]
			table[bucketIndex].add((E)e);

			size++; // Increase size

			return true;
		}

		@Override /** Remove the element from the set */
			public boolean remove(Object e) {
				if (!contains(e))
					return false;

				int bucketIndex = hash(e.hashCode());

				// Create a linked list for the bucket if it is not created
				if (table[bucketIndex] != null) {
					AVLTree<E> bucket = table[bucketIndex]; 
					bucket.remove(e);
				}

				size--; // Decrease size

				return true;
			}

		@Override /** Return true if the set contains no elements */
			public boolean isEmpty() {
				return size == 0;
			}

		@Override /** Return the number of elements in the set */
			public int size() {
				return size;
			}

		@Override /** Return an iterator for the elements in this set */
			public java.util.Iterator<E> iterator() {
				return new MyHashSetIterator(this);
			}


		@Override
		public Object[] toArray(Object[] ts) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public boolean containsAll(Collection clctn) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public boolean addAll(Collection clctn) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public boolean removeAll(Collection clctn) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public boolean retainAll(Collection clctn) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}


		/** Inner class for iterator */
		private class MyHashSetIterator implements java.util.Iterator<E> {
			// Store the elements in a list
			private java.util.ArrayList<E> list;
			private int current = 0; // Point to the current element in list
			private MyHashSet<E> set;

			/** Create a list from the set */
			public MyHashSetIterator(MyHashSet<E> set) {
				this.set = set;
				list = setToList();
			}

			@Override /** Next element for traversing? */
				public boolean hasNext() {
					return current < list.size();
				}

			@Override /** Get current element and move cursor to the next */
				public E next() {
					return list.get(current++);
				}

			@Override /** Remove the current element and refresh the list */
				public void remove() {
					// Delete the current element from the hash set
					set.remove(list.get(current)); 
					list.remove(current); // Remove current element from the list
				}
		}  

		/** Hash function */
		private int hash(int hashCode) {
			return supplementalHash(hashCode) & (capacity - 1);
		}

		/** Ensure the hashing is evenly distributed */
		private static int supplementalHash(int h) {
			h ^= (h >>> 20) ^ (h >>> 12);
			return h ^ (h >>> 7) ^ (h >>> 4);
		}

		/** Return a power of 2 for initialCapacity */
		private int trimToPowerOf2(int initialCapacity) {
			int capacity = 1;
			while (capacity < initialCapacity) {
				capacity <<= 1;
			}

			return capacity;
		}

		/** Remove all e from each bucket */
		private void removeElements() {
			for (int i = 0; i < capacity; i++) {
				if (table[i] != null) {
					table[i].clear();
				}
			}
		}

		/** Rehash the set */
		private void rehash() {
			java.util.ArrayList<E> list = setToList(); // Copy to a list
			capacity <<= 1; // Double capacity      
			table = new AVLTree[capacity]; // Create a new hash table
			size = 0; // Reset size 

			for (E element: list) {
				add(element); // Add from the old table to the new table
			}
		}

		/** Copy elements in the hash set to an array list */
		private java.util.ArrayList<E> setToList() {
			java.util.ArrayList<E> list = new java.util.ArrayList<>();

			for (int i = 0; i < capacity; i++) {
				if (table[i] != null) {
					for (E e: table[i]) {
						list.add(e);
					}
				}
			}  

			return list;
		}

		@Override
		public String toString() {
			java.util.ArrayList<E> list = setToList();
			StringBuilder builder = new StringBuilder("[");

			// Add the elements except the last one to the string builder
			for (int i = 0; i < list.size() - 1; i++) {
				builder.append(list.get(i) + ", ");
			}

			// Add the last element in the list to the string builder
			if (list.size() == 0)
				builder.append("]");
			else
				builder.append(list.get(list.size() - 1) + "]");

			return builder.toString();
		}
		//@Override
		public boolean aaddAll(Collection<? extends E> c) {
			boolean check = false;
			for (E e : c){
				if (add(e)){
					check = true;
				}    
			}
			return check;
		}


		//@Override
		public boolean acontainsAll(Collection<?> c) {
			boolean check = true;
			for (Object e : c){
				if (!contains(e))
					check = false;
				break;
			}
			return check;
		}

		//@Override
		public boolean aremoveAll(Collection<?> c) {
			boolean check = false;
			for (Object e : c){
				if (remove(e)){
					check = true;
				}
			}
			return check;
		}

		//@Override
		public boolean aretainAll(Collection<?> arg0) {
			// Left as an exercise
			return false;
		}

		@Override
		public Object[] toArray() {
			// Left as an exercise
			return null;
		}


		public <T> T[] atoArray(T[] arg0) {
			// Left as an exercise
			return null;
		}
	}

	public static class AVLTree<E extends Comparable<E>> extends BST<E> {
		/** Create an empty AVL tree */
		public AVLTree() {
		}

		/** Create an AVL tree from an array of objects */
		public AVLTree(E[] objects) {
			super(objects);
		}

		@Override /** Override createNewNode to create an AVLTreeNode */
			protected AVLTreeNode<E> createNewNode(E e) {
				return new AVLTreeNode<E>(e);
			}

		@Override /** Insert an element and rebalance if necessary */
			public boolean insert(E e) {
				boolean successful = super.insert(e);
				if (!successful)
					return false; // e is already in the tree
				else {
					balancePath(e); // Balance from e to the root if necessary
				}

				return true; // e is inserted
			}

		/** Update the height of a specified node */
		private void updateHeight(AVLTreeNode<E> node) {
			if (node.left == null && node.right == null) // node is a leaf
				node.height = 0;
			else if (node.left == null) // node has no left subtree
				node.height = 1 + ((AVLTreeNode<E>)(node.right)).height;
			else if (node.right == null) // node has no right subtree
				node.height = 1 + ((AVLTreeNode<E>)(node.left)).height;
			else
				node.height = 1 +
					Math.max(((AVLTreeNode<E>)(node.right)).height,
							((AVLTreeNode<E>)(node.left)).height);
		}

		/** Balance the nodes in the path from the specified
		 * node to the root if necessary
		 */
		private void balancePath(E e) {
			java.util.ArrayList<TreeNode<E>> path = path(e);
			for (int i = path.size() - 1; i >= 0; i--) {
				AVLTreeNode<E> A = (AVLTreeNode<E>)(path.get(i));
				updateHeight(A);
				AVLTreeNode<E> parentOfA = (A == root) ? null :
					(AVLTreeNode<E>)(path.get(i - 1));

				switch (balanceFactor(A)) {
					case -2:
						if (balanceFactor((AVLTreeNode<E>)A.left) <= 0) {
							balanceLL(A, parentOfA); // Perform LL rotation
						}
						else {
							balanceLR(A, parentOfA); // Perform LR rotation
						}
						break;
					case +2:
						if (balanceFactor((AVLTreeNode<E>)A.right) >= 0) {
							balanceRR(A, parentOfA); // Perform RR rotation
						}
						else {
							balanceRL(A, parentOfA); // Perform RL rotation
						}
				}
			}
		}

		/** Return the balance factor of the node */
		private int balanceFactor(AVLTreeNode<E> node) {
			if (node.right == null) // node has no right subtree
				return -node.height;
			else if (node.left == null) // node has no left subtree
				return +node.height;
			else
				return ((AVLTreeNode<E>)node.right).height -
					((AVLTreeNode<E>)node.left).height;
		}

		/** Balance LL (see Figure 26.3) */
		private void balanceLL(TreeNode<E> A, TreeNode<E> parentOfA) {
			TreeNode<E> B = A.left; // A is left-heavy and B is left-heavy

			if (A == root) {
				root = B;
			}
			else {
				if (parentOfA.left == A) {
					parentOfA.left = B;
				}
				else {
					parentOfA.right = B;
				}
			}

			A.left = B.right; // Make T2 the left subtree of A
			B.right = A; // Make A the left child of B
			updateHeight((AVLTreeNode<E>)A);
			updateHeight((AVLTreeNode<E>)B);
		}

		/** Balance LR (see Figure 26.5) */
		private void balanceLR(TreeNode<E> A, TreeNode<E> parentOfA) {
			TreeNode<E> B = A.left; // A is left-heavy
			TreeNode<E> C = B.right; // B is right-heavy

			if (A == root) {
				root = C;
			}
			else {
				if (parentOfA.left == A) {
					parentOfA.left = C;
				}
				else {
					parentOfA.right = C;
				}
			}

			A.left = C.right; // Make T3 the left subtree of A
			B.right = C.left; // Make T2 the right subtree of B
			C.left = B;
			C.right = A;

			// Adjust heights
			updateHeight((AVLTreeNode<E>)A);
			updateHeight((AVLTreeNode<E>)B);
			updateHeight((AVLTreeNode<E>)C);
		}

		/** Balance RR (see Figure 26.4) */
		private void balanceRR(TreeNode<E> A, TreeNode<E> parentOfA) {
			TreeNode<E> B = A.right; // A is right-heavy and B is right-heavy

			if (A == root) {
				root = B;
			}
			else {
				if (parentOfA.left == A) {
					parentOfA.left = B;
				}
				else {
					parentOfA.right = B;
				}
			}

			A.right = B.left; // Make T2 the right subtree of A
			B.left = A;
			updateHeight((AVLTreeNode<E>)A);
			updateHeight((AVLTreeNode<E>)B);
		}

		/** Balance RL (see Figure 26.6) */
		private void balanceRL(TreeNode<E> A, TreeNode<E> parentOfA) {
			TreeNode<E> B = A.right; // A is right-heavy
			TreeNode<E> C = B.left; // B is left-heavy

			if (A == root) {
				root = C;
			}
			else {
				if (parentOfA.left == A) {
					parentOfA.left = C;
				}
				else {
					parentOfA.right = C;
				}
			}

			A.right = C.left; // Make T2 the right subtree of A
			B.left = C.right; // Make T3 the left subtree of B
			C.left = A;
			C.right = B;

			// Adjust heights
			updateHeight((AVLTreeNode<E>)A);
			updateHeight((AVLTreeNode<E>)B);
			updateHeight((AVLTreeNode<E>)C);
		}

		@Override /** Delete an element from the binary tree.
				   * Return true if the element is deleted successfully
				   * Return false if the element is not in the tree */
			public boolean delete(E element) {
				if (root == null)
					return false; // Element is not in the tree

				// Locate the node to be deleted and also locate its parent node
				TreeNode<E> parent = null;
				TreeNode<E> current = root;
				while (current != null) {
					if (element.compareTo(current.element) < 0) {
						parent = current;
						current = current.left;
					}
					else if (element.compareTo(current.element) > 0) {
						parent = current;
						current = current.right;
					}
					else
						break; // Element is in the tree pointed by current
				}

				if (current == null)
					return false; // Element is not in the tree

				// Case 1: current has no left children (See Figure 23.6)
				if (current.left == null) {
					// Connect the parent with the right child of the current node
					if (parent == null) {
						root = current.right;
					}
					else {
						if (element.compareTo(parent.element) < 0)
							parent.left = current.right;
						else
							parent.right = current.right;

						// Balance the tree if necessary
						balancePath(parent.element);
					}
				}
				else {
					// Case 2: The current node has a left child
					// Locate the rightmost node in the left subtree of
					// the current node and also its parent
					TreeNode<E> parentOfRightMost = current;
					TreeNode<E> rightMost = current.left;

					while (rightMost.right != null) {
						parentOfRightMost = rightMost;
						rightMost = rightMost.right; // Keep going to the right
					}

					// Replace the element in current by the element in rightMost
					current.element = rightMost.element;

					// Eliminate rightmost node
					if (parentOfRightMost.right == rightMost)
						parentOfRightMost.right = rightMost.left;
					else
						// Special case: parentOfRightMost is current
						parentOfRightMost.left = rightMost.left; 

					// Balance the tree if necessary
					balancePath(parentOfRightMost.element);
				}

				size--;
				return true; // Element inserted
			}

		/** AVLTreeNode is TreeNode plus height */
		protected static class AVLTreeNode<E> extends BST.TreeNode<E> {
			protected int height = 0; // New data field

			public AVLTreeNode(E o) {
				super(o);
			}
		}
	}

	public static class BST<E extends Comparable<E>> implements Tree<E> {
		protected TreeNode<E> root;
		protected int size = 0;

		/** Create a default binary tree */
		public BST() {
		}

		/** Create a binary tree from an array of objects */
		public BST(E[] objects) {
			for (int i = 0; i < objects.length; i++)
				add(objects[i]);
		}

		@Override /** Returns true if the element is in the tree */
			public boolean search(E e) {
				TreeNode<E> current = root; // Start from the root

				while (current != null) {
					if (e.compareTo(current.element) < 0) {
						current = current.left;
					}
					else if (e.compareTo(current.element) > 0) {
						current = current.right;
					}
					else // element matches current.element
						return true; // Element is found
				}

				return false;
			}

		@Override /** Insert element o into the binary tree
				   * Return true if the element is inserted successfully */
			public boolean insert(E e) {
				if (root == null)
					root = createNewNode(e); // Create a new root
				else {
					// Locate the parent node
					TreeNode<E> parent = null;
					TreeNode<E> current = root;
					while (current != null)
						if (e.compareTo(current.element) < 0) {
							parent = current;
							current = current.left;
						}
						else if (e.compareTo(current.element) > 0) {
							parent = current;
							current = current.right;
						}
						else
							return false; // Duplicate node not inserted

					// Create the new node and attach it to the parent node
					if (e.compareTo(parent.element) < 0)
						parent.left = createNewNode(e);
					else
						parent.right = createNewNode(e);
				}

				size++;
				return true; // Element inserted successfully
			}

		protected TreeNode<E> createNewNode(E e) {
			return new TreeNode<>(e);
		}

		@Override /** Inorder traversal from the root */
			public void inorder() {
				inorder(root);
			}

		/** Inorder traversal from a subtree */
		protected void inorder(TreeNode<E> root) {
			if (root == null) return;
			inorder(root.left);
			System.out.print(root.element + " ");
			inorder(root.right);
		}

		@Override /** Postorder traversal from the root */
			public void postorder() {
				postorder(root);
			}

		/** Postorder traversal from a subtree */
		protected void postorder(TreeNode<E> root) {
			if (root == null) return;
			postorder(root.left);
			postorder(root.right);
			System.out.print(root.element + " ");
		}

		@Override /** Preorder traversal from the root */
			public void preorder() {
				preorder(root);
			}

		/** Preorder traversal from a subtree */
		protected void preorder(TreeNode<E> root) {
			if (root == null) return;
			System.out.print(root.element + " ");
			preorder(root.left);
			preorder(root.right);
		}

		/** This inner class is static, because it does not access 
		  any instance members defined in its outer class */
		public static class TreeNode<E> {
			protected E element;
			protected TreeNode<E> left;
			protected TreeNode<E> right;

			public TreeNode(E e) {
				element = e;
			}
		}

		@Override /** Get the number of nodes in the tree */
			public int getSize() {
				return size;
			}

		/** Returns the root of the tree */
		public TreeNode<E> getRoot() {
			return root;
		}

		/** Returns a path from the root leading to the specified element */
		public java.util.ArrayList<TreeNode<E>> path(E e) {
			java.util.ArrayList<TreeNode<E>> list =
				new java.util.ArrayList<>();
			TreeNode<E> current = root; // Start from the root

			while (current != null) {
				list.add(current); // Add the node to the list
				if (e.compareTo(current.element) < 0) {
					current = current.left;
				}
				else if (e.compareTo(current.element) > 0) {
					current = current.right;
				}
				else
					break;
			}

			return list; // Return an array list of nodes
		}

		@Override /** Delete an element from the binary tree.
				   * Return true if the element is deleted successfully
				   * Return false if the element is not in the tree */
			public boolean delete(E e) {
				// Locate the node to be deleted and also locate its parent node
				TreeNode<E> parent = null;
				TreeNode<E> current = root;
				while (current != null) {
					if (e.compareTo(current.element) < 0) {
						parent = current;
						current = current.left;
					}
					else if (e.compareTo(current.element) > 0) {
						parent = current;
						current = current.right;
					}
					else
						break; // Element is in the tree pointed at by current
				}

				if (current == null)
					return false; // Element is not in the tree

				// Case 1: current has no left child
				if (current.left == null) {
					// Connect the parent with the right child of the current node
					if (parent == null) {
						root = current.right;
					}
					else {
						if (e.compareTo(parent.element) < 0)
							parent.left = current.right;
						else
							parent.right = current.right;
					}
				}
				else {
					// Case 2: The current node has a left child
					// Locate the rightmost node in the left subtree of
					// the current node and also its parent
					TreeNode<E> parentOfRightMost = current;
					TreeNode<E> rightMost = current.left;

					while (rightMost.right != null) {
						parentOfRightMost = rightMost;
						rightMost = rightMost.right; // Keep going to the right
					}

					// Replace the element in current by the element in rightMost
					current.element = rightMost.element;

					// Eliminate rightmost node
					if (parentOfRightMost.right == rightMost)
						parentOfRightMost.right = rightMost.left;
					else
						// Special case: parentOfRightMost == current
						parentOfRightMost.left = rightMost.left;     
				}

				size--;
				return true; // Element deleted successfully
			}

		@Override /** Obtain an iterator. Use inorder. */
			public java.util.Iterator<E> iterator() {
				return new InorderIterator();
			}

		// Inner class InorderIterator
		private class InorderIterator implements java.util.Iterator<E> {
			// Store the elements in a list
			private java.util.ArrayList<E> list =
				new java.util.ArrayList<>();
			private int current = 0; // Point to the current element in list

			public InorderIterator() {
				inorder(); // Traverse binary tree and store elements in list
			}

			/** Inorder traversal from the root*/
			private void inorder() {
				inorder(root);
			}

			/** Inorder traversal from a subtree */
			private void inorder(TreeNode<E> root) {
				if (root == null)return;
				inorder(root.left);
				list.add(root.element);
				inorder(root.right);
			}

			@Override /** More elements for traversing? */
				public boolean hasNext() {
					if (current < list.size())
						return true;

					return false;
				}

			@Override /** Get the current element and move to the next */
				public E next() {
					return list.get(current++);
				}

			@Override /** Remove the current element */
				public void remove() {
					delete(list.get(current)); // Delete the current element
					list.clear(); // Clear the list
					inorder(); // Rebuild the list
				}
		}

		@Override /** Remove all elements from the tree */
			public void clear() {
				root = null;
				size = 0;
			}
	}

	public static interface Tree<E> extends Collection<E> {

		/**
		 * Return true if the element is in the tree
		 */
		public boolean search(E e);

		/**
		 * Insert element o into the binary tree Return true if the element is
		 * inserted successfully
		 */
		public boolean insert(E e);

		/**
		 * Delete the specified element from the tree Return true if the element
		 * is deleted successfully
		 */
		public boolean delete(E e);

		/**
		 * Get the number of nodes in the tree
		 */
		public int getSize();

		/**
		 * Inorder traversal from the root
		 */
		public default void inorder() {
		}

		/**
		 * Postorder traversal from the root
		 */
		public default void postorder() {
		}

		/**
		 * Preorder traversal from the root
		 */
		public default void preorder() {
		}

		@Override
		/**
		 * Return true if the tree is empty
		 */
		public default boolean isEmpty() {
			return size() == 0;
		}

		;

		@Override
		public default boolean contains(Object e) {
			return search((E) e);
		}

		@Override
		public default boolean add(E e) {
			return insert(e);
		}

		@Override
		public default boolean remove(Object e) {
			return delete((E) e);
		}

		@Override
		public default int size() {
			return getSize();
		}

		@Override
		public default boolean containsAll(Collection<?> c) {
			// Left as an exercise
			return false;
		}

		@Override
		public default boolean addAll(Collection<? extends E> c) {
			// Left as an exercise
			return false;
		}

		@Override
		public default boolean removeAll(Collection<?> c) {
			// Left as an exercise
			return false;
		}

		@Override
		public default boolean retainAll(Collection<?> c) {
			// Left as an exercise
			return false;
		}

		@Override
		public default Object[] toArray() {
			// Left as an exercise
			return null;
		}

		@Override
		public default <T> T[] toArray(T[] array) {
			// Left as an exercise
			return null;
		}
	}
}
