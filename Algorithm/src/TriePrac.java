import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TriePrac {
    
    private static class Node {
        Map<Character, Node> children;
        String name;
        int count;
        
        public Node() {
            children = new HashMap<Character, Node>();
        }
    }
    
    private static class Trie {
        private Node root;
        
        public Trie() {
            root = new Node();
        }
        
        public boolean add(Node root, String name, int i) {
            Node node;
            char c = name.charAt(i);
            boolean added = false;
            if (root.children.containsKey(c)) {
                node = root.children.get(c);
            } else {
                node = new Node(); 
                root.children.put(c, node);
                added = true;
            }
            if (i == name.length() - 1) {
                node.name = name;
                node.count = 1;
                return added;
            }
            added = add(node, name, ++ i);
            if (added) {
                node.count ++;
            }
            return added;
        }
        
        public void add(String name) {
            if (name == null || name.length() == 0) {
                return;
            }
            add(root, name, 0);
        }
        
        public int find(String prefix) {
            int i = 0;
            Node node = root;
            while (i < prefix.length() && node != null) {
                char c = prefix.charAt(i ++);
                node = node.children.get(c);
            }
            if (node == null) {
                return 0;
            }
            return node.count;
        }
    }

    public static void main(String[] args) {
        Trie contacts = new Trie();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i ++) {
            switch(in.next()) {
            case "add":
                contacts.add(in.next());
                break;
            case "find":
                System.out.println(contacts.find(in.next()));
                break;
            }
        }
        in.close();
    }
}