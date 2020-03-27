import java.util.Scanner;

class Stack {
    Node[] tab;
    int len;
    int maxSize;

    Stack(int size) {
        maxSize = size;
        len = 0;
        tab = new Node[maxSize];
    }

    void push(Node node) {
        tab[len++] = node;
    }

    Node pop() {
        Node temp = tab[len - 1];
        tab[len - 1] = null;
        len--;
        return temp;
    }

    Node peek() {
        return tab[len - 1];
    }

    boolean isEmpty() {
        return (len <= 0);
    }

    int size() {
        return len;
    }
}

class Queue {
    private int maxSize;
    private Node[] Elem;
    private int front;
    private int rear;
    private int currSize;

    public Queue(int size) {
        maxSize = size;
        Elem = new Node[maxSize];
        front = 0;
        rear = 0;
        currSize = 0;
    }

    private int Addone(int i) {
        return (i + 1) % maxSize;
    }

    public void enQueue(Node x) {
        if (isFull())
            return;
        else {
            Elem[rear] = x;
            rear = Addone(rear);
            currSize++;
        }
    }

    public Node deQueue() {
        if (isEmpty()) {
            return null;
        } else {
            Node temp = Elem[front];
            front = Addone(front);
            currSize--;
            return temp;
        }
    }

    public Node Front() {
        if (isEmpty()) {
            return null;
        } else
            return Elem[front];
    }

    public boolean isEmpty() {
        return (rear == front);
    }

    public boolean isFull() {
        return (Addone(rear) == front);
    }

    public int size() {
        return currSize;
    }
}

class Node {
    public int val; // element danych (klucz)
    public Node left; // lewy potomek wĂÂzÄšÂa
    public Node right; // prawy lewy potomek wĂÂzÄšÂa

    public Node() {
        val = 0;
        left = null;
        right = null;
    }

    public Node(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

class Index {
    int in;

    Index() {
        in = 0;
    }
}

class BST {
    public Node root;
    Index aut = new Index();

    public BST() {
        root = null;
    }

    public Node createTreePreAux(int[] tab, Index preIndex, int low, int high, int size) {
        if (preIndex.in >= size || low > high)
            return null;

        Node tempRoot = new Node(tab[preIndex.in]);
        preIndex.in++;

        if (low == high)
            return tempRoot;

        int i;
        for (i = low; i <= high; i++)
            if (tab[i] > tempRoot.val)
                break;

        tempRoot.left = createTreePreAux(tab, preIndex, preIndex.in, i - 1, size);
        tempRoot.right = createTreePreAux(tab, preIndex, i, high, size);

        return tempRoot;
    }

    public void createTreePreOrder(int[] tab) {
        root = createTreePreAux(tab, aut, 0, tab.length - 1, tab.length);
    }

    public Node createTreePostAux(int post[], Index postIndex, int key, int min, int max, int size) {
        if (postIndex.in < 0)
            return null;

        Node root = null;

        if (key > min && key < max) {
            root = new Node(key);
            postIndex.in = postIndex.in - 1;

            if (postIndex.in >= 0) 
                root.right = createTreePostAux(post, postIndex, post[postIndex.in], key, max, size);
            if(postIndex.in >= 0)
                root.left = createTreePostAux(post, postIndex, post[postIndex.in], min, key, size);
            
        }
        return root;
    }

    public void createTreePostOrder(int[] tab) {
        Index postIndex = new Index();
        postIndex.in = tab.length - 1;
        root = createTreePostAux(tab, postIndex, tab[postIndex.in], Integer.MIN_VALUE, Integer.MAX_VALUE, tab.length);
    }

    public void printInOrderIter() {
        Stack S = new Stack(1000);
        Node p = root;
        while (p != null || !S.isEmpty()) {
            if (p != null)
            {
                S.push(p);
                p = p.left;
            }
            else
            {
                p = S.pop();
                System.out.print(p.val + " ");
                p = p.right;
            }
        }
        System.out.println();
    }

    public void printPreOrderIter() {
        Stack S = new Stack(80);
        Node p = root;
        while (p != null || !S.isEmpty()) {
            if (p != null) {
                S.push(p);
                System.out.print(p.val + " ");
                p = p.left;
            } else {
                p = S.pop();
                p = p.right;
            }
        }
        System.out.println();
    }

    public void printPostOrderIter() {
        Stack S = new Stack(80);
        Node node = root;

        S.push(node);
        Node prev = null;
        while (!S.isEmpty()) {
            Node current = S.peek();
            if (prev == null || prev.left == current || prev.right == current) {
                if (current.left != null)
                    S.push(current.left);
                else if (current.right != null)
                    S.push(current.right);
                else {
                    S.pop();
                    System.out.print(current.val + " ");
                }
            } else if (current.left == prev) {
                if (current.right != null)
                    S.push(current.right);
                else {
                    S.pop();
                    System.out.print(current.val + " ");
                }
            } else if (current.right == prev) {
                S.pop();
                System.out.print(current.val + " ");
            }
            prev = current;
        }
        System.out.println();
    }

    public void printLevelOrder() {
        if (root == null)
            return;

        Queue q = new Queue(100);
        q.enQueue(root);

        while (true) {
            int nodeCount = q.size();
            if (nodeCount == 0)
                break;

            while (nodeCount > 0) {
                Node node = q.Front();
                System.out.print(node.val + " ");
                q.deQueue();
                if (node.left != null)
                    q.enQueue(node.left);
                if (node.right != null)
                    q.enQueue(node.right);
                nodeCount--;
            }
        }
        System.out.println();
    }

    public int FindParentVal(int x) {
        Node temp = root;

        if (temp == null || root.val == x)
            return -1;

        while ((temp.left != null && temp.left.val != x) || (temp.right != null && temp.right.val != x))
        {
            if((temp.left != null && temp.left.val == x) || (temp.right != null && temp.right.val == x))
                return temp.val;

            if (x < temp.val)
            {
                //System.out.println("ide w lewo");
                temp = temp.left;
            }
            else
            {
                //System.out.println("ide w prawo");
                temp = temp.right;
            }
        }

        if((temp.left != null && temp.left.val == x) || (temp.right != null && temp.right.val == x))
            return temp.val;

        return -1;
    }

    public Node FindParentNode(int x)
    {
        Node temp = root;

        if (temp == null || root.val == x)
            return null;

        while ((temp.left != null && temp.left.val != x) || (temp.right != null && temp.right.val != x))
        {
            if((temp.left != null && temp.left.val == x) || (temp.right != null && temp.right.val == x))
                return temp;

            if (x < temp.val)
            {
                //System.out.println("ide w lewo");
                temp = temp.left;
            }
            else
            {
                //System.out.println("ide w prawo");
                temp = temp.right;
            }
        }

        if((temp.left != null && temp.left.val == x) || (temp.right != null && temp.right.val == x))
            return temp;

        return null;
    }

    public void insertNode(int x)
    {
        Node s, p, prev;
        s = new Node();
        s.val = x;
        s.left= null;
        s.right= null;

        if (root == null)
            root = s;
        else 
        {
            p = root;
            prev = null;
            while (p != null)
            {
                prev = p;
                if (x < p.val)
                    p = p.left;
                else if(x > p.val)
                    p = p.right;
                else
                    return;
            }

            if (x < prev.val)
                prev.left = s;
            else
                prev.right = s;
        }
    }

    public void deleteNode(int x)
    {
        Node temp = new Node();
        Boolean left;
        Node tempParent = FindParentNode(x); //rodzic
        
        if(root.val == x)
        {
            temp = root;
            while(temp.left != null && temp.right != null)
            {
                Node s = temp.right;
                Node prev = temp;
                
                while(s.left != null)
                {
                    prev = s;
                    s = s.left;
                }
                
                temp.val = s.val;   
                temp = s;
                tempParent = prev;
            }
            x = temp.val;

            if(tempParent.right != null && tempParent.right.val == x)
            {
                left = false;
                temp = tempParent.right;
            }
            else if(tempParent.left != null && tempParent.left.val == x)
            {
                left = true;
                temp = tempParent.left;
            }
            else
            {
                return; 
            } 
            

            //1
            if(temp.left == null && temp.right == null)
            {
                if(left == true)
                tempParent.left = null;
                else
                tempParent.right = null;
                
                return;
            }

            //2
            if(temp.left != null && temp.right == null)
            {
                if(left == true)
                    tempParent.left = temp.left;
                else
                    tempParent.right = temp.left;
                return;
            }

            if(temp.left == null && temp.right != null)
            {
                if(left == true)
                    tempParent.left = temp.right;
                else
                    tempParent.right = temp.right;
                return;
            }
        }
        else
        {
            if(tempParent.right != null && tempParent.right.val == x)
            {
                left = false;
                temp = tempParent.right;
            }
            else if(tempParent.left != null && tempParent.left.val == x)
            {
                left = true;
                temp = tempParent.left;
            }
            else
            {
                return; //nie ma takiego wezla
            } 
            

            //1
            if(temp.left == null && temp.right == null)
            {
                if(left == true)
                tempParent.left = null;
                else
                tempParent.right = null;
                
                return;
            }

            //2
            if(temp.left != null && temp.right == null)
            {
                if(left == true)
                    tempParent.left = temp.left;
                else
                    tempParent.right = temp.left;
                return;
            }

            if(temp.left == null && temp.right != null)
            {
                if(left == true)
                    tempParent.left = temp.right;
                else
                    tempParent.right = temp.right;
                return;
            }

            //3
            while(temp.left != null || temp.right != null)
            {
                Node s = temp.right;
                Node prev = temp;
                
                while(s.left != null)
                {
                    prev = s;
                    s = s.left;
                }
                
                temp.val = s.val;   
                temp = s;
                tempParent = prev;
            }
            x = temp.val;

            if(tempParent.right != null && tempParent.right.val == x)
            {
                left = false;
                temp = tempParent.right;
            }
            else if(tempParent.left != null && tempParent.left.val == x)
            {
                left = true;
                temp = tempParent.left;
            }
            else
            {
                return; 
            } 
            
            //1
            if(temp.left == null && temp.right == null)
            {
                if(left == true)
                    tempParent.left = null;
                else
                    tempParent.right = null;
                
                return;
            }

            //2
            if(temp.left != null && temp.right == null)
            {
                if(left == true)
                    tempParent.left = temp.left;
                else
                    tempParent.right = temp.left;
                return;
            }

            if(temp.left == null && temp.right != null)
            {
                if(left == true)
                    tempParent.left = temp.right;
                else
                    tempParent.right = temp.right;
                return;
            }
        }
    }

    public void displayT(Node p, int h)
    {
        if(p != null)
        {
            displayT(p.right, h+1);

            for(int j=0; j<h; j++)
                System.out.print("    ");
            System.out.println(p.val);

            displayT(p.left, h+1);
        }
    }

    public void print(String prefix, Node n, boolean isLeft) {
        if (n != null) {
            print(prefix + "     ", n.right, false);
            System.out.println (prefix + ("|-- ") + n.val);
            print(prefix + "     ", n.left, true);
        }
    }

    public int findSuccesor(int x)
    {
        Node node = FindParentNode(x);
        Node tempNode;

        if(root.val == x)
        {
            if(root.right != null)
            {
                if(root.right.val < x)
                    return -1;
                return root.right.val;
            }
            else if(root.left != null)
            {
                if(root.left.val < x)
                    return -1;
                return root.left.val;
            }
            return -1;
        }

        if(node == null)
            return -1;

        if(node.left != null && node.left.val == x)
            node = node.left;
        else if(node.right != null && node.right.val == x)
            node = node.right;
        else
            return -1;
        
        if(node.right != null)
        {
            Node mini = node.right;
            while(mini.left != null)
                mini = mini.left;
            if(mini.val < x)
                return -1;
            return mini.val;
        }

        tempNode = FindParentNode(node.val);
        while(tempNode != null && tempNode.left != node)
        {
            node = tempNode;
            tempNode = FindParentNode(tempNode.val);
        }


        if(tempNode == null || tempNode.val < x)
                return -1;
        return tempNode.val;
    }

    public int findPredecessor(int x)
    {
        Node node = FindParentNode(x);
        Node tempNode;

        if(node == null)
        {
            if(root.val == x)
                node = root;
            else
                return -1;
        }
        else
        {
            if(node.left != null && node.left.val == x)
                node = node.left;
            else if(node.right != null && node.right.val == x)
                node = node.right;
            else
                return -1;
        }
        
        if(node.left != null)
        {
            Node maks = node.left;
            while(maks.right != null)
                maks = maks.right;
            if(maks.val > x)
                return -1;
            return maks.val;
        }
        tempNode = FindParentNode(node.val);
        while(tempNode != null && tempNode.right != null && tempNode.right.val != node.val)
        {
            node = tempNode;
            tempNode = FindParentNode(tempNode.val);
        }

        if(tempNode == null || tempNode.val > x)
                return -1;
        return tempNode.val;
    }
}

public class Source {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int sets = in.nextInt();

        for(int q = 1; q <= sets; q++)
        {
            int nodes = in.nextInt(); // liczba wierzcholkow
            String order = in.next(); // preorder / postorder
            int operations;
            String op;

            BST tree = new BST();
            int[] tab = new int[nodes];

            for (int i = 0; i < nodes; i++)
                tab[i] = in.nextInt();

            if(order.equals("PREORDER")) // 10, 5, 1, 7, 40, 50
                tree.createTreePreOrder(tab);
            else if(order.equals("POSTORDER"))
                tree.createTreePostOrder(tab); // 1, 7, 5, 50, 40, 10

            operations = in.nextInt();

            System.out.println("ZESTAW: " + q);
            //tree.printInOrderIter();

            while(operations-- > 0)
            {
                op = in.next();

                if(op.equals("PREORDER"))
                {
                    System.out.print("PREORDER: ");
                    tree.printPreOrderIter();
                }
                else if(op.equals("INORDER"))
                {
                    System.out.print("INORDER: ");
                    tree.printInOrderIter();
                }
                else if(op.equals("POSTORDER"))
                {
                    System.out.print("POSTORDER: ");
                    tree.printPostOrderIter();
                }
                else if(op.equals("LEVELORDER"))
                {
                    System.out.print("LEVELORDER: ");
                    tree.printLevelOrder();
                }
                else if(op.equals("PARENT"))
                {
                    int arg = in.nextInt();
                    int res = tree.FindParentVal(arg);

                    System.out.print("PARENT " + arg +  ": ");
                    
                    if(res != -1)
                        System.out.println(res);
                    else
                        System.out.println("BRAK");
                }
                else if(op.equals("INSERT"))
                {
                    int arg = in.nextInt();
                    tree.insertNode(arg);
                }
                else if(op.equals("DELETE"))
                {
                    int arg = in.nextInt();
                    tree.deleteNode(arg);
                }
                else if(op.equals("SUCCESSOR"))
                {
                    int arg = in.nextInt();
                    int res = tree.findSuccesor(arg);

                    System.out.print("SUCCESSOR " + arg +  ": ");                    

                    if(res != -1)
                        System.out.println(res);
                    else
                        System.out.println("BRAK");
                }
                else if(op.equals("PREDECESSOR"))
                {
                    int arg = in.nextInt();
                    int res = tree.findPredecessor(arg);

                    System.out.print("PREDECESSOR " + arg +  ": ");

                    if(res != -1)
                        System.out.println(res);
                    else
                        System.out.println("BRAK");
                }
                else if(op.equals("DISPLAY"))
                {
                    //tree.displayT(tree.root, 0);
                    tree.print("", tree.root, false);
                }

                //tree.printInOrderIter();
            }
        }
    }
}
