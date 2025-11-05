import edu.desu.book.treebook.LinkedBinaryTree;
import edu.desu.book.treebook.Position;

public class BuildBSTFromKVArray {

    public static <K> LinkedBinaryTree<K> buildBinaryTreeFromArrayMidpoint
    (LinkedBinaryTree<K> U,
     Position<K> position,
     char side,
     K[] data,
     int low,
     int high) {
        if (low <= high) {
            // Find the midpoint
            int mid = (low + high) / 2;
            // Create the new node - called a "position"
            Position<K> newposition = null;
            // Make the midpoint the next node root, left or right
            if (side == 'T')
                newposition = U.addRoot(data[mid]);
            if (side == 'L')
                newposition = U.addLeft(position, data[mid]);   // recur left of the middle
            else if (side == 'R')
                newposition = U.addRight(position, data[mid]);   // recur left of the middle
            // Look at this method signature to understand how to call
            buildBinaryTreeFromArrayMidpoint(U, newposition, 'L', data, low, mid - 1);
            buildBinaryTreeFromArrayMidpoint(U, newposition, 'R', data, mid + 1, high);
        }
        return U;
    }
}