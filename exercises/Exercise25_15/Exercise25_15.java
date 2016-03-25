package Exercise25_15;

/**
 * Created by casey on 2016-03-24.
 */

import Exercise25.Tree;

import java.util.ArrayList;
import java.util.Scanner;

public class Exercise25_15 {
    public static void main(String[] args) {
        new Exercise25_15();
    }

    public Exercise25_15() {
//        Scanner input = new Scanner(System.in);
        Scanner input = new Scanner("45 54 67 56 50 45 23 59 23 67");
        BST tree = new BST<>();

        int[] list = new int[10];
        System.out.print("Enter 10 integers: ");
        for (int i = 0; i < list.length; i++) {
            tree.insert(list[i] = input.nextInt());
        }
        tree.delete(list[0]);

        for (int i = 0; i < list.length; i++) {
            if (tree.isLeaf(list[i])) {
                System.out.println(tree.getPath(list[i]));
            }
        }
    }

    public class BST<E extends Comparable<E>> implements Tree<E> {
        protected TreeNode<E> root;
        protected int size = 0;

        /**
         * Create a default binary tree
         */
        public BST() {
        }

        /**
         * Create a binary tree from an array of objects
         */
        public BST(E[] objects) {
            for (int i = 0; i < objects.length; i++)
                add(objects[i]);
        }

        @Override
        /** Returns true if the element is in the tree */
        public boolean search(E e) {
            TreeNode<E> current = root; // Start from the root

            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else // element matches current.element
                    return true; // Element is found
            }

            return false;
        }

        // TODO: fix so it works
        public TreeNode<E> getNode(E element) {
            ArrayList<TreeNode<E>> queue = new ArrayList<>();
            TreeNode<E> current = root;
            queue.add(root);

            while (!queue.isEmpty()) {
                if (queue.get(0) != null && element.compareTo(queue.get(0).element) == 0) {
                    return queue.get(0);
                } else {
                    if (queue.get(0).left != null) queue.add(queue.get(0).left);
                    if (queue.get(0).right != null) queue.add(queue.get(0).right);
                    queue.remove(0);
                }
            }

//            while (current != null) {
//                if (element.compareTo(current.element) < 0) {
//                    current = current.left;
//                } else if (element.compareTo(current.element) > 0) {
//                    current = current.right;
//                } else {
//                    return current;
//                }
//            }

            return null;
        }

        @Override
        // TODO: need to override to set parent of node when inserting
        /** Insert element o into the binary tree
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
                    } else if (e.compareTo(current.element) > 0) {
                        parent = current;
                        current = current.right;
                    } else
                        return false; // Duplicate node not inserted

                // Create the new node and attach it to the parent node
                if (e.compareTo(parent.element) < 0) {
                    parent.left = createNewNode(e);
                    parent.left.parent = parent;
                } else {
                    parent.right = createNewNode(e);
                    parent.right.parent = parent;
                }
            }

            size++;
            return true; // Element inserted successfully
        }

        protected TreeNode<E> createNewNode(E e) {
            return new TreeNode<>(e);
        }

        @Override
        /** Inorder traversal from the root */
        public void inorder() {
            inorder(root);
        }

        /**
         * Inorder traversal from a subtree
         */
        protected void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            System.out.print(root.element + " ");
            inorder(root.right);
        }

        @Override
        /** Postorder traversal from the root */
        public void postorder() {
            postorder(root);
        }

        /**
         * Postorder traversal from a subtree
         */
        protected void postorder(TreeNode<E> root) {
            if (root == null) return;
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.element + " ");
        }

        @Override
        /** Preorder traversal from the root */
        public void preorder() {
            preorder(root);
        }

        /**
         * Preorder traversal from a subtree
         */
        protected void preorder(TreeNode<E> root) {
            if (root == null) return;
            System.out.print(root.element + " ");
            preorder(root.left);
            preorder(root.right);
        }

        @Override
        /** Get the number of nodes in the tree */
        public int getSize() {
            return size;
        }

        /**
         * Returns the root of the tree
         */
        public TreeNode<E> getRoot() {
            return root;
        }

        /**
         * Returns a path from the root leading to the specified element
         */
        public java.util.ArrayList<TreeNode<E>> path(E e) {
            java.util.ArrayList<TreeNode<E>> list =
                    new java.util.ArrayList<>();
            TreeNode<E> current = root; // Start from the root

            while (current != null) {
                list.add(current); // Add the node to the list
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else
                    break;
            }

            return list; // Return an array list of nodes
        }

        @Override
        /** Delete an element from the binary tree.
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
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else
                    break; // Element is in the tree pointed at by current
            }

            if (current == null)
                return false; // Element is not in the tree

            // Case 1: current has no left child
            if (current.left == null) {
                // Connect the parent with the right child of the current node
                if (parent == null) {
                    root = current.right;
                    current.right.parent = root;
                } else {
                    if (e.compareTo(parent.element) < 0) {
                        parent.left = current.right;
                        parent.left.parent = parent;
                    } else {
                        parent.right = current.right;
                        parent.right.parent = parent;
                    }
                }
            } else {
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
                if (parentOfRightMost.right == rightMost) {
                    parentOfRightMost.right = rightMost.left;
                    parentOfRightMost.right.parent = parentOfRightMost;
                } else {
                    // Special case: parentOfRightMost == current
                    parentOfRightMost.left = rightMost.left;
                }
            }

            size--;
            return true; // Element deleted successfully
        }

        @Override
        /** Obtain an iterator. Use inorder. */
        public java.util.Iterator<E> iterator() {
            return new InorderIterator();
        }

        public java.util.Iterator<E> postorderIterator() {
            return new PostOrderIterator();
        }

        public java.util.Iterator<E> preorderIterator() {
            return new PreOrderIterator();
        }

        @Override
        /** Remove all elements from the tree */
        public void clear() {
            root = null;
            size = 0;
        }

        /**
         * This inner class is static, because it does not access
         * any instance members defined in its outer class
         */
        public class TreeNode<E> {
            protected E element;
            protected TreeNode<E> left;
            protected TreeNode<E> right;
            protected TreeNode<E> parent;

            public TreeNode(E e) {
                element = e;
            }
        }

        // TODO: find node and return true if left/right are null
        public boolean isLeaf(E e) {
            TreeNode<E> current = getNode(e);
            if (current == null) return false;
            if (current.left == null && current.right == null) return true;
            return false;
        }

        // TODO: find node then recursively append root until no roots left
        public ArrayList<E> getPath(E e) {
            ArrayList<E> path = new ArrayList<>();
            TreeNode<E> current = getNode(e);
            while (current != null) {
                path.add(current.element);
                current = current.parent;
            }
            return path;
        }

        private class PostOrderIterator implements java.util.Iterator<E> {
            private java.util.ArrayList<E> list = new java.util.ArrayList<E>();
            private int current = 0;

            public PostOrderIterator() {
                postorder(root);
            }

            private void postorder(TreeNode<E> root) {
                if (root == null) return;
                postorder(root.left);
                postorder(root.right);
                list.add(root.element);
            }

            @Override
            public boolean hasNext() {
                if (current < list.size())
                    return true;
                return false;
            }

            @Override
            public E next() {
                return list.get(current++);
            }

            @Override
            public void remove() {
                delete(list.get(current));
                list.clear();
                inorder();
            }
        }

        private class PreOrderIterator implements java.util.Iterator<E> {
            private java.util.ArrayList<E> list = new java.util.ArrayList<E>();
            private int current = 0;

            public PreOrderIterator() {
                preorder(root);
            }

            private void preorder(TreeNode<E> root) {
                if (root == null) return;
                list.add(root.element);
                preorder(root.left);
                preorder(root.right);
            }

            @Override
            public boolean hasNext() {
                if (current < list.size())
                    return true;
                return false;
            }

            @Override
            public E next() {
                return list.get(current++);
            }

            @Override
            public void remove() {
                delete(list.get(current));
                list.clear();
                inorder();
            }
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

            /**
             * Inorder traversal from the root
             */
            private void inorder() {
                inorder(root);
            }

            /**
             * Inorder traversal from a subtree
             */
            private void inorder(TreeNode<E> root) {
                if (root == null) return;
                inorder(root.left);
                list.add(root.element);
                inorder(root.right);
            }

            @Override
            /** More elements for traversing? */
            public boolean hasNext() {
                if (current < list.size())
                    return true;

                return false;
            }

            @Override
            /** Get the current element and move to the next */
            public E next() {
                return list.get(current++);
            }

            @Override
            /** Remove the current element */
            public void remove() {
                delete(list.get(current)); // Delete the current element
                list.clear(); // Clear the list
                inorder(); // Rebuild the list
            }
        }
    }
}
