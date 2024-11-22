/***
 * This Java program creates and manages a Binary Search Tree. 
 * It allows users to insert values from 0–999 also allows to increment all values.
 * 
 * Owner - Uddeshya-Patidar
 * 
 * Date - 22/11/2024
 */
package practice.java;
import java.util.*;

// Main class for Binary Search Tree (BST)
public class binarySearchTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continueRunning = true; 
        BinarySearchTree bst = new BinarySearchTree();

        while (continueRunning) {
            // Display the main menu
            System.out.println(BSTConstants.MAIN_MENU);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Create a new BST
                    bst = new BinarySearchTree(); 
                    System.out.println(BSTConstants.ENTER_NUMBERS);

                    boolean acceptingInput = true;
                    while (acceptingInput) {
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("done")) {
                            acceptingInput = false;
                        } else {
                            try {
                                int num = Integer.parseInt(input);
                                if (num >= 0 && num <= 999) {
                                    if (!bst.insert(num)) {
                                        System.out.printf(BSTConstants.DUPLICATE_VALUE, num);
                                    }
                                } else {
                                    System.out.println(BSTConstants.RANGE_ERROR);
                                }
                            } catch (NumberFormatException e) {
                                System.out.printf(BSTConstants.INVALID_INPUT, 0, 999);
                            }
                        }
                    }

                    if (bst.root == null) {
                        System.out.println(BSTConstants.TREE_EMPTY);
                    } else {
                        // Print the BST structure
                        System.out.println("\nBinary Search Tree:");
                        bst.printTree();
                    }
                    break;

                case "2":
                    // Increment all values in the BST
                    if (bst.root == null) {
                        System.out.println(BSTConstants.TREE_EMPTY);
                    } else {
                        System.out.print(BSTConstants.INCREMENT_INPUT);
                        try {
                            int incrementValue = Integer.parseInt(scanner.nextLine().trim());
                            bst.incrementValues(incrementValue);
                            System.out.println(BSTConstants.UPDATED_OUTPUT);
                            bst.printTree();
                        } catch (NumberFormatException e) {
                            System.out.println(BSTConstants.INVALID_INPUT);
                        }
                    }
                    break;

                default:
                    // Handle invalid choice
                    System.out.println(BSTConstants.INVALID_MENU_INPUT);
                    break;
            }

            // Ask if the user wants to run the program again
            System.out.print(BSTConstants.PROMPT_RUN_AGAIN);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                System.out.println(BSTConstants.EXIT_MESSAGE);
                continueRunning = false; 
            }
        }

        scanner.close();
    }
}

// Represents a single node in the BST
class TreeNode {
    int value; 
    TreeNode left, right;

    // Constructor to initialize a new node
    TreeNode(int value) {
        this.value = value;
        left = right = null;
    }
}

// Encapsulates operations for the BST
class BinarySearchTree {
    TreeNode root; 
    boolean insert(int value) {
        if (contains(root, value)) {
            return false; // Value already exists
        }
        root = insertRec(root, value);
        return true;
    }

    // Recursively inserting value into the BST
    TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    // Checks if a value exists in the BST
    boolean contains(TreeNode root, int value) {
        if (root == null) return false;
        if (root.value == value) return true;
        return value < root.value ? contains(root.left, value) : contains(root.right, value);
    }

    // Increments all values in the BST
    void incrementValues(int incrementValue) {
        incrementValuesRec(root, incrementValue);
    }

    // helper to increment values of nodes
    void incrementValuesRec(TreeNode node, int incrementValue) {
        if (node == null) return;
        node.value += incrementValue;
        incrementValuesRec(node.left, incrementValue);
        incrementValuesRec(node.right, incrementValue);
    }

    // Prints the BST 
    void printTree() {
        int maxLevel = maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    // Helper method to print the BST nodes
    private void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) {
            return;
        }

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));
        int firstSpaces = (int) Math.pow(2, floor) - 1;
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

        printWhitespaces(firstSpaces);

        List<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                System.out.print(" ");
                newNodes.add(null);
                newNodes.add(null);
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (TreeNode node : nodes) {
                printWhitespaces(firstSpaces - i);
                if (node == null) {
                    printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (node.left != null) {
                    System.out.print("/");
                } else {
                    printWhitespaces(1);
                }
                printWhitespaces(i + i - 1);
                if (node.right != null) {
                    System.out.print("\\");
                } else {
                    printWhitespaces(1);
                }

                printWhitespaces(edgeLines + edgeLines - i);
            }

            System.out.println();
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    // Prints spaces
    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    // Calculates the depth of BST
    private int maxLevel(TreeNode node) {
        if (node == null) return 0;
        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    // Checks for null
    private boolean isAllElementsNull(List<TreeNode> list) {
        for (TreeNode node : list) {
            if (node != null) return false;
        }
        return true;
    }
}
