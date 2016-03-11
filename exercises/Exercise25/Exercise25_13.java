package Exercise25;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Iterator;

/* Doesn't actually work (pane doesn't display), but he wants it as a single file so whatever. Does work if you run
   BSTAnimation separately.
 */

public class Exercise25_13 {
    public Exercise25_13() {
        new BSTAnimation();
    }

    public static void main(String[] args) {
        new Exercise25_13();
    }

    static class BST<E extends Comparable<E>> implements Tree<E> {
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

        @Override
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
                } else {
                    if (e.compareTo(parent.element) < 0)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
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
                if (parentOfRightMost.right == rightMost)
                    parentOfRightMost.right = rightMost.left;
                else
                    // Special case: parentOfRightMost == current
                    parentOfRightMost.left = rightMost.left;
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
        public static class TreeNode<E> {
            protected E element;
            protected TreeNode<E> left;
            protected TreeNode<E> right;

            public TreeNode(E e) {
                element = e;
            }
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

    public class BSTAnimation extends Application {
        @Override // Override the start method in the Application class
        public void start(Stage primaryStage) {
            BST<Integer> tree = new BST<>(); // Create a tree

            BorderPane pane = new BorderPane();
            BSTView view = new BSTView(tree); // Create a View
            pane.setCenter(view);

            TextField tfKey = new TextField();
            tfKey.setPrefColumnCount(3);
            tfKey.setAlignment(Pos.BASELINE_RIGHT);
            Button btInsert = new Button("Insert");
            Button btDelete = new Button("Delete");
            Button btInOrder = new Button("In Order");
            Button btPreOrder = new Button("Pre Order");
            Button btPostOrder = new Button("Post Order");
            HBox hBox = new HBox(8);
            hBox.getChildren().addAll(new Label("Enter a key: "),
                    tfKey, btInsert, btDelete, btInOrder, btPreOrder, btPostOrder);
            hBox.setAlignment(Pos.CENTER);
            pane.setBottom(hBox);

            btInsert.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (tree.search(key)) { // key is in the tree already
                    view.displayTree();
                    view.setStatus(key + " is already in the tree");
                } else {
                    tree.insert(key); // Insert a new key
                    view.displayTree();
                    view.setStatus(key + " is inserted in the tree");
                }
            });

            btDelete.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (!tree.search(key)) { // key is not in the tree
                    view.displayTree();
                    view.setStatus(key + " is not in the tree");
                } else {
                    tree.delete(key); // Delete a key
                    view.displayTree();
                    view.setStatus(key + " is deleted from the tree");
                }
            });

            btInOrder.setOnAction(e -> {
                String ordering = "In Order: [ ";
                for (Integer i : tree) {
                    ordering += i + " ";
                }
                view.setOrdering(ordering + "]");
            });

            btPreOrder.setOnAction(e -> {
                String ordering = "Pre Order: [ ";
                for (Iterator<Integer> it = tree.preorderIterator(); it.hasNext(); ) {
                    ordering += it.next() + " ";
                }
                view.setOrdering(ordering + " ]");
            });

            btPostOrder.setOnAction(e -> {
                String ordering = "Post Order: [ ";
                for (Iterator<Integer> it = tree.postorderIterator(); it.hasNext(); ) {
                    ordering += it.next() + " ";
                }
                view.setOrdering(ordering + " ]");
            });

            // Create a scene and place the pane in the stage
            Scene scene = new Scene(pane, 450, 250);
            primaryStage.setTitle("BSTAnimation"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
            scene.widthProperty().addListener(ov -> view.displayTree());
            scene.heightProperty().addListener(ov -> view.displayTree());

            // testing
            int[] keys = {10, 5, 7, 6, 3, 2, 1, 15, 17, 19, 18};
            for (int k : keys) tree.insert(k);
            view.displayTree();
        }

        /**
         * The main method is only needed for the IDE with limited
         * JavaFX support. Not needed for running from the command line.
         */
        public void main(String[] args) {
            launch(args);
        }
    }

    public class BSTView extends Pane {
        private BST<Integer> tree = new BST<>();
        private double radius = 15; // Tree node radius
        private double vGap = 50; // Gap between two levels in a tree

        BSTView(BST<Integer> tree) {
            this.tree = tree;
            setStatus("Tree is empty");
            setOrdering("No Ordering");
        }

        public void setStatus(String msg) {
            getChildren().add(new Text(20, 20, msg));
        }

        public void setOrdering(String msg) {
            displayTree();
            Text txt = new Text(getWidth() / 2, 20, msg);
            getChildren().add(new Text(getWidth() / 2, 20, msg));
        }

        public void displayTree() {
            this.getChildren().clear(); // Clear the pane
            if (tree.getRoot() != null) {
                // Display tree recursively
                displayTree(tree.getRoot(), getWidth() / 10, getHeight() / 2, 25);
            }
        }

        /**
         * Display a subtree rooted at position (x, y)
         */
        private void displayTree(BST.TreeNode<Integer> root, double x, double y, double hGap) {

            double adj = hGap + (hGap / 4);

            // left becomes bottom
            if (root.left != null) {
                double yVal = vGap - (vGap / 4);
                // Draw a line to the left node
                Line left = new Line(x + hGap, y + vGap, x, y);
                getChildren().add(left);
                // Draw the left subtree recursively
                displayTree(root.left, x + hGap, y + vGap, adj);
            }

            // right becomes top
            if (root.right != null) {
                double yVal = vGap + (vGap / 4);
                // Draw a line to the right node
                Line right = new Line(x + hGap, y - vGap, x, y);
                getChildren().add(right);
                // Draw the right subtree recursively
                displayTree(root.right, x + hGap, y - vGap, adj);
            }

            // Display a node
            Circle circle = new Circle(x, y, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            getChildren().addAll(circle,
                    new Text(x - 4, y + 4, root.element + ""));
        }
    }
}
