package hw1;

public class Tree {
    Node root;

    // traversal : recursive function that traverses the tree and creates the output for dot
    public void traversal(Node node, StringBuffer str) {
        String temp;

        if (node != root) {
            temp = node.hashCode() + " [label=\"" + node.value + "\"]" + "\n";
            str.append(temp);
        }
        
        if (node.left != null) {
            temp = node.hashCode() + "--" + node.left.hashCode() + "\n";
            str.append(temp);
            traversal(node.left, str);
        }

        if (node.right != null) {
            temp = node.hashCode() + "--" + node.right.hashCode() + "\n";
            str.append(temp);
            traversal(node.right, str);
        }
    }

    // dotString : function that returns the string for dot
    public String dotString () {
        StringBuffer DotBuff = new StringBuffer("graph ArithmeticExpressionTree {\n");
        Node current = root;
        DotBuff.append(current.hashCode() + " [label=\"" + root.value + "\"" + "]\n" );
        traversal(current, DotBuff);
        DotBuff.append("}");
        return(DotBuff.toString());
    }

}
