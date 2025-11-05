import edu.desu.book.treebook.LinkedBinaryTree;
import edu.desu.book.treebook.Position;
import edu.desu.utilities.UtilityMethodsBinaryTreeBook;

public class BuildBinaryTree831Book {

    public static LinkedBinaryTree<String> build() {
        // Define the tree
        LinkedBinaryTree<String> U = new LinkedBinaryTree<>();
        // Define the position objects
        Position<String> pvd, chi, sea, bal, nyc;
        // Add the position objects to the tree
        pvd = U.addRoot("Providence");
        chi = U.addLeft(pvd, "Chicago");
        sea = U.addRight(pvd, "Seattle");
        bal = U.addLeft(chi, "Baltimore");
        nyc = U.addRight(chi, "New York");
        // Return the tree
        return U;
    }

    public static void test() {
        LinkedBinaryTree<String> U;
        U = BuildBinaryTree831Book.build();
        String treeString = UtilityMethodsBinaryTreeBook.printBinaryTreeInorder(U);
        System.out.println("The tree traversal inorder is: ");
        System.out.println(treeString);
        SwingBinaryTreeDisplay.showInWindow(U);
    }

    public static void main(String[] args) {
        test();
    }

}
