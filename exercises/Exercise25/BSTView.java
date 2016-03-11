package Exercise25;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

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
