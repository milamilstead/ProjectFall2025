import edu.desu.book.treebook.LinkedBinaryTree;
import edu.desu.book.treebook.Position;
import edu.desu.utilities.UtilityMethodsBinaryTreeBook;

import java.util.Scanner;

import static edu.desu.utilities.UtilityMethodsString.compareNumericStrings;

public class KVStringPairBST {
    public String keyTitle;
    public String valueTitle;
    LinkedBinaryTree<KVStringPair> BST;

    // CONSTRUCTORS start here

    // Build the starter binary tree
    // From a KVStringPairSortedArray
    public KVStringPairBST() {
        // Use the code to generate the default sorted array
        KVStringPairSortedArray inputKVStringPairSortedArray =
                new KVStringPairSortedArray();
        keyTitle = inputKVStringPairSortedArray.keyTitle;
        valueTitle = inputKVStringPairSortedArray.valueTitle;
        BST = new LinkedBinaryTree<>();
        // Initialize the new position - first will be root
        Position<KVStringPair> rootPos = null;
        // Now build the binary tree using BuildBSTFromSortedKVArray
        BST = BuildBSTFromKVArray.buildBinaryTreeFromArrayMidpoint
                (BST, rootPos, 'T',
                        inputKVStringPairSortedArray.dataArray,
                        0, inputKVStringPairSortedArray.datasize - 1);
    }

    // Build the starter binary tree from a KVStringPairSortedArray
    // use size parameter to generate 0..(size-1) as array values
    public KVStringPairBST(int size) {
        // Use the code to generate the default sorted array
        KVStringPairSortedArray inputKVStringPairSortedArray =
                new KVStringPairSortedArray(size);
        keyTitle = inputKVStringPairSortedArray.keyTitle;
        valueTitle = inputKVStringPairSortedArray.valueTitle;
        BST = new LinkedBinaryTree<>();
        // Initialize the new position - first will be root
        Position<KVStringPair> rootPos = null;
        // Now build the binary tree using BuildBSTFromSortedKVArray
        BST = BuildBSTFromKVArray.buildBinaryTreeFromArrayMidpoint
                (BST, rootPos, 'T',
                        inputKVStringPairSortedArray.dataArray,
                        0, inputKVStringPairSortedArray.datasize - 1);
    }

    // Build binary tree from an input .csv file
    public KVStringPairBST(String fileName) {
        KVStringPairSortedArray inputKVStringPairSortedArray =
                new KVStringPairSortedArray(fileName);
        keyTitle = inputKVStringPairSortedArray.keyTitle;
        valueTitle = inputKVStringPairSortedArray.valueTitle;
        BST = new LinkedBinaryTree<>();
        // Initialize the new position - first will be root
        Position<KVStringPair> rootPos = null;
        // Now build the binary tree using BuildBSTFromSortedKVArray
        BST = BuildBSTFromKVArray.buildBinaryTreeFromArrayMidpoint
                (BST, rootPos, 'T',
                        inputKVStringPairSortedArray.dataArray,
                        0, inputKVStringPairSortedArray.datasize - 1);
    }

    // TRAVERSALS start here

    // SEARCHING methods start here

    // Single element searches start here

    public String Search(Integer integerToSearch) {
        String toSearch = integerToSearch.toString();
        return Search(toSearch);
    }

    public String Search(String toSearch) {
        Position<KVStringPair> curPosition = BST.root();
        return Search(toSearch, curPosition);
    }

    public String Search(String toSearch, Position<KVStringPair> curPosition) {
        System.out.println("toSearch is: " + toSearch);
        KVStringPair thisKVStringPair;
        String thisKey, thisValue, returnValue = null;
        int compareResult;
        // If still more nodes to traverse, continue...
        if (curPosition != null) {
            // Get the KVStringPair for this position, then get the key
            thisKVStringPair = (KVStringPair) curPosition.getElement();
            thisKey = thisKVStringPair.getKey();
            System.out.println("This key is: " + thisKey);

            // Compare toSearch against this key
            compareResult = compareNumericStrings(toSearch, thisKey);
            System.out.println("compareResult is: " + compareResult);
            // else search left or search right - recursively
            if (compareResult == 0) {
                // Found it - get the value associated with this key and return it
                returnValue = thisKVStringPair.getValue();
            }
            // else: if toSearch less than this key, look on the left
            else if (compareResult < 0) {
                System.out.println("Traversing left...");
                returnValue = Search(toSearch, BST.left(curPosition));
            }
            // else: if toSearch greater than this key, look on the right
            // compareResult here is > 0
            else {
                System.out.println("Traversing right...");
                returnValue = Search(toSearch, BST.right(curPosition));
            }
        }
        System.out.println("Returning: " + returnValue);
        return returnValue;
    }

    // Range searching methods start here

    public void SearchRange(Integer lowerInt, Integer upperInt) {
        String lowerIntString = lowerInt.toString();
        String upperIntString = upperInt.toString();
        SearchRange(lowerIntString, upperIntString);
    }

    public void SearchRange(String lowerString, String upperString) {
        // Start the search at the tree root
        Position<KVStringPair> curPosition = BST.root();
        KVStringPair lowerKVStringPair = new KVStringPair(lowerString, "Test");
        KVStringPair upperKVStringPair = new KVStringPair(upperString, "Test");
        SearchRange(lowerKVStringPair, upperKVStringPair, curPosition);
    }

    public void SearchRange(KVStringPair lowerKVStringPair,
                            KVStringPair upperKVStringPair,
                            Position<KVStringPair> curPosition) {
        if (curPosition != null) {
            KVStringPair thisKVStringPair = (KVStringPair) curPosition.getElement();
            int compareThisWithLower = thisKVStringPair.compareTo(lowerKVStringPair);
            int compareThisWithUpper = thisKVStringPair.compareTo(upperKVStringPair);
            // Traverse left if this KVStringPair is greater than lower
            if (compareThisWithLower > 0)
                SearchRange(lowerKVStringPair,
                    upperKVStringPair, BST.left(curPosition));
            // Report this element (MIDDLE) if it is within range
            if ((compareThisWithLower >= 0) && (compareThisWithUpper <= 0))
                System.out.println(thisKVStringPair.toString());
            // Traverse right if this element is less than upper
            if (compareThisWithUpper < 0)
                SearchRange(lowerKVStringPair,
                        upperKVStringPair, BST.right(curPosition));
        }
    }

    // TESTING methods start here

    public static void bookTest() {
        System.out.println("Starting bookTest");
        KVStringPairBST testKVStringPairBST =
                new KVStringPairBST();
        String treeString =
                UtilityMethodsBinaryTreeBook.printBinaryTreeInorder
                        (testKVStringPairBST.BST);
        System.out.println("The tree traversal inorder is: ");
        System.out.println(treeString);
        // Now provide a visual display of tree
        SwingBinaryTreeDisplay.showInWindow(testKVStringPairBST.BST);
    }

    // Test creation of tree with input size
    public static void sizeTest(int size) {
        KVStringPairBST testKVStringPairBST =
                new KVStringPairBST(size);
        // Now provide a visual display of tree
        SwingBinaryTreeDisplay.showInWindow(testKVStringPairBST.BST);
        // Determine the size of the tree (number of nodes)
        int treesize = testKVStringPairBST.BST.size();
        System.out.println("Tree size is :" + treesize);
        // Now search - test
    }

    // Test search on tree size of 100000
    public static void searchTest() {
        // Use tree size of 100000
        KVStringPairBST testKVStringPairBST =
                new KVStringPairBST(100000);
        // Now provide a visual display of tree
        SwingBinaryTreeDisplay.showInWindow(testKVStringPairBST.BST);
        // Determine the size of the tree (number of nodes)
        int treesize = testKVStringPairBST.BST.size();
        System.out.println("Tree size is :" + treesize);
        // Now search for an element - say 32432
        Integer keyToSearch = 32546;
        String valueString = testKVStringPairBST.Search(keyToSearch);
        System.out.println("Key is: " + keyToSearch);
        System.out.println("Value is: " + valueString);
    }

    // Test with .csv file
    public static void searchBSTTestCSV() {
        String fileName = "DataFiles/ThirdPartyDataCSVFiles/TopSongs5000Edited.csv";
        KVStringPairBST ourKVStringPairBST =
                new KVStringPairBST(fileName);
        String outputString;
        // Now provide a visual display of tree
        // Uncomment to see tree
        //SwingBinaryTreeDisplay.showInWindow(ourKVStringPairBST.BST);
        // Determine the size of the tree (number of nodes)
        int treesize = ourKVStringPairBST.BST.size();
        System.out.println("Tree size is :" + treesize);
        // Now search for an element
        String keyToSearch = "Elton John";
        String valueString = ourKVStringPairBST.Search(keyToSearch);
        System.out.println("Key is: " + keyToSearch);
        System.out.println("Value is: " + valueString);
    }

    // Test range search
    public static void testSearchRange() {
        KVStringPairBST testKVStringPairBST =
                new KVStringPairBST();
        // Now provide a visual display of tree
        SwingBinaryTreeDisplay.showInWindow(testKVStringPairBST.BST);
        testKVStringPairBST.SearchRange(4, 25);
    }

    // MAIN runs test functions with user prompt

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("\nEnter a number (1 or 2) to call a method, or 'Q' to quit:");
            System.out.println("1 is the book example, 2 is a large array");
            System.out.println("3 is search test, 4 is search on CSV file");
            System.out.println("5 is search range test");
            input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "1":
                    bookTest();
                    break;
                case "2":
                    sizeTest(100000);
                    break;
                case "3":
                    searchTest();
                    break;
                case "4":
                    searchBSTTestCSV();
                    break;
                case "5":
                    testSearchRange();
                    break;
                case "Q":
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input. Please enter '1', '2', or 'Q'.");
                    break;
            }
        } while (!input.equalsIgnoreCase("Q"));
        scanner.close();
    }

}
