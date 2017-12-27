import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    
    private interface Operation {
        void doIt();
        void undoIt();
    }
    
    private static class Append implements Operation {
        private String w;
        
        public Append(String w) {
            this.w = w;
        }
        public void doIt() {
            buf.append(w);
            stack.add(this);
        }
        public void undoIt() {
            int length = buf.length();
            buf.delete(length - w.length(), length);
        }
    }
    
    private static class Erase implements Operation {
        private String w;
        private int k;
        
        public Erase(int k) {
            this.k = k;
        }
        public void doIt() {
            int length = buf.length();
            w = buf.substring(length - k, length);
            buf.delete(length - k, length);
            stack.add(this);
        }
        public void undoIt() {
            buf.append(w);
        }
    }
    
    private static class Get implements Operation {
        private int k;
        
        public Get(int k) {
            this.k = k;
        }
        public void doIt() {
            System.out.println(buf.charAt(k));
        }
        public void undoIt() {
        }
    }
    
    private static class Undo implements Operation {
        
        public Undo() {
        }
        public void doIt() {
            stack.remove(stack.size() - 1).undoIt();
        }
        public void undoIt() {
        }
    }
    
    private static Operation parse(String line) {
        Scanner scanner = new Scanner(line);
        Operation op = null;
        switch(scanner.nextInt()) {
        case 1:
            op = new Append(scanner.next());
            break;
        case 2:
            op = new Erase(scanner.nextInt());
            break;
        case 3:
            op = new Get(scanner.nextInt());
            break;
        case 4:
            op = new Undo();
            break;
        }
        scanner.close();
        return op;
    }
    
    
    private static ArrayList<Operation> stack = new ArrayList<Operation>(1000000);
    private static StringBuilder buf = new StringBuilder(1000000);
    

    public static void main(String[] args) {
        buf.append(" ");
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i ++) {
            String line = scanner.nextLine();
            Operation op = parse(line);
            op.doIt();
        }
        scanner.close();
        
    }
}