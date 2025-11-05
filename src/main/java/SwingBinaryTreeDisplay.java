import edu.desu.book.treebook.LinkedBinaryTree;

import javax.swing.*;
import java.awt.*;

public class SwingBinaryTreeDisplay {

    static class BinaryTreePanel<E> extends JPanel {
        private final LinkedBinaryTree<E> tree;
        private final int nodeRadius = 24;
        private final int vGap = 70;

        public BinaryTreePanel(LinkedBinaryTree<E> tree) {
            this.tree = tree;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (tree.root != null) {
                drawTree(g, tree.root, getWidth() / 2, 40, getWidth() / 4, 0);
            }
        }

        private void drawTree(Graphics g, LinkedBinaryTree.Node<E> node, int x, int y, int hGap, int level) {
            if (level > 3) {
                return; // limit drawing to levels 0–3
            }

            g.setColor(Color.BLACK);
            g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            String text = node.getElement() != null ? node.getElement().toString() : "";
            g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y + 5);

            if (node.getLeft() != null) {
                int childX = x - hGap;
                int childY = y + vGap;
                g.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
                drawTree(g, node.getLeft(), childX, childY, hGap / 2, level + 1);
            }

            if (node.getRight() != null) {
                int childX = x + hGap;
                int childY = y + vGap;
                g.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
                drawTree(g, node.getRight(), childX, childY, hGap / 2, level + 1);
            }
        }
    }

    public static <E> void showInWindow(LinkedBinaryTree<E> T) {
        JFrame frame = new JFrame("LinkedBinaryTree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new BinaryTreePanel<>(T));
        frame.setVisible(true);
    }
}

