package edu.desu.utilities;

import edu.desu.book.treebook.LinkedBinaryTree;
import edu.desu.book.treebook.Position;

import java.util.ArrayList;
import java.util.Iterator;

public class UtilityMethodsBinaryTreeBook {

    public static <E> String printBinaryTreeInorder(LinkedBinaryTree<E> T) {
        Iterator<Position<E>> iter = T.positions().iterator();
        ArrayList<String> treePositionsByList = new ArrayList<>();
        while (iter.hasNext()) {
            Position<E> p = iter.next();
            E ourE = p.getElement();
            // Add them to an arrayList of String
            treePositionsByList.add(ourE.toString());
        }
        //return treePositionsByList.toString();
        return String.join("\n", treePositionsByList);
    }



}
