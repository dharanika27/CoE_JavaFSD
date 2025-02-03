package Week1;

public class Task8 {

    // TreeNode class representing each node in the binary tree
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // Serialization function: Converts the tree into a string
    public static String serialize(TreeNode root) {
        StringBuilder result = new StringBuilder();
        serializeHelper(root, result);
        return result.toString();
    }

    private static void serializeHelper(TreeNode node, StringBuilder result) {
        if (node == null) {
            result.append("#,"); // Using "#" to denote null nodes
            return;
        }
        result.append(node.val).append(","); // Append node value
        serializeHelper(node.left, result); // Recur for left subtree
        serializeHelper(node.right, result); // Recur for right subtree
    }

    // Deserialization function: Converts the string back into the tree
    public static TreeNode deserialize(String data) {
        String[] values = data.split(",");
        int[] index = { 0 }; // Use an array to keep track of current index in the array
        return deserializeHelper(values, index);
    }

    private static TreeNode deserializeHelper(String[] values, int[] index) {
        if (index[0] >= values.length || values[index[0]].equals("#")) {
            index[0]++; // Move to the next value
            return null; // Return null if the value is "#"
        }

        TreeNode node = new TreeNode(Integer.parseInt(values[index[0]]));
        index[0]++; // Move to the next value
        node.left = deserializeHelper(values, index); // Recur for left subtree
        node.right = deserializeHelper(values, index); // Recur for right subtree

        return node;
    }

    // Helper method to print the tree (for visualization)
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.print("null ");
            return;
        }
        System.out.print(root.val + " ");
        printTree(root.left);
        printTree(root.right);
    }

    public static void main(String[] args) {
        // Example usage:
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.println("Original Tree (Pre-order): ");
        printTree(root);
        System.out.println();

        // Serialize the tree to a string
        String serializedData = serialize(root);
        System.out.println("Serialized Data: " + serializedData);

        // Deserialize the string back to a tree
        TreeNode deserializedRoot = deserialize(serializedData);

        System.out.println("Deserialized Tree (Pre-order): ");
        printTree(deserializedRoot);
        System.out.println();
    }
}
