package comp2402w24l4;

// From within the Lab directory (or wherever you put the comp2402w24l4 directory):
// javac comp2402w24l4/PhyloTree.java
// java comp2402w24l4/PhyloTree


import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import ods.ArrayStack;

public class PhyloTree {

    /**
     * Array used to store allowable DNA bases. Do not remove this.
     */
    private static final char[] DNABases = {'A', 'C', 'G', 'T'};

    /**
     * Represents a single node in our tree. Do not remove this.
     * You may add to this class if you wish.
     */
    class Node {
        String s;
        Node left, right, parent;
        boolean[] set;
        boolean mark;

        public Node(String s, Node parent) {
            this.s = s;
            this.parent = parent;
        }

        public Node() {
            this(null, null);
        }
    }


    /**
     * The length of each gene/String in each node. Do not remove this.
     */
    private int k;

    /**
     *
     */
    private int n;


    /**
     * The root node. Do not remove this.
     */
    protected Node r;


    /**
     * A map between the Nodes and the Strings they represent. Do not remove this.
     */
    protected HashMap<String, Node> stringsToNodes;

    /**
     * Constructor
     */
    public PhyloTree(int k) {
        if( k < 1 )
            throw new IllegalArgumentException("k must be at least 1");
        this.k = k;
        this.n = 0;
        this.stringsToNodes = new HashMap<String, Node>();
    }

    /**
     * Constructor
     */
    public PhyloTree() {
        this(1);
    }

    /**
     * Returns the number of nodes in the tree.
     * @return
     */
    public int size() {
        return n;
    }

    /**
     * Resets the tree to be empty. Does nothing to the k value.
     */
    public void clear() {
        n = 0;
        r = null;
    }

    /**
     * Returns a basic string representation of this tree.
     * Lists the nodes in in-order.
     */
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Iterator<String> it = iterator();
        while (it.hasNext()) {
            String next = it.next();
            s.append(next == null ? "null" : next + (it.hasNext() ? ", " : ""));
        }
        s.append("]");
        return s.toString();
    }


    /**
     * Returns a pretty-printed string representation of this tree.
     * It's not perfect, but it works for the small local tests.
     * It spans multiple lines, with the root node on the top line,
     * the root's children on the next line and so on.
     * This is not particularly efficient, but since you're likely to
     * only use it on small-ish trees, that's fine.
     * @return
     */
    public String prettyPrint() {
        if( r == null ) return "";
        int h = height();
        ArrayStack<StringBuilder> levels = new ArrayStack<StringBuilder>();
        for( int i=0; i <= h; i++ ) {
            levels.add(new StringBuilder());
        }

        int gap = (int)Math.pow(2, h-1)*(k+1)-1; // was -1
        Node u = r, prev = null, next;
        int level = 0;
        while (u != null) {
            if (prev == u.parent) {
                StringBuilder thisLevel = levels.get(level);
                if( thisLevel.length() == 0 )
                    thisLevel.append(gapString(gap));
                else
                    thisLevel.append(gapString(Math.max(2*gap+2-k, 1)));
                levels.get(level).append(u.s);
                if (u.left != null) {
                    next = u.left;
                    level++;
                    gap = (gap == k? 0 : (gap+1)/2-1);
                }
                else if (u.right != null) {
                    // Left node was missing but right is not so we need to add in stuff
                    int g = (gap+1)/2-1;
                    int missing = 1;
                    for( int j = level+1; j <= h; j++ ) {
                        thisLevel = levels.get(j);
                        if( thisLevel.length() == 0 )
                            thisLevel.append(gapString(g));
                        thisLevel.append(gapString((2*g+1)*(missing-1)+k*missing));
                        g = (g == k? 0 : (g+1)/2-1);
                        missing *= 2;
                    }
                    next = u.right;
                    level++;
                    gap = (gap == k? 0 : (gap+1)/2-1);
                }
                else { // Both children are missing
                    int g = (gap+1)/2-1;
                    int missing = 2;
                    for( int j = level+1; j <= h; j++ ) {
                        thisLevel = levels.get(j);
                        if( thisLevel.length() == 0 )
                            thisLevel.append(gapString(g));
                        else thisLevel.append(gapString(2*g+1) );
                        thisLevel.append(gapString((2*g+1)*(missing-1)+k*missing));
                        g = (g == k? 0 : (g+1)/2-1);
                        missing *= 2;
                    }
                    next = u.parent;
                    level--;
                    gap = (gap == 0? k : (gap+1)*2-1);
                }
            } else if (prev == u.left) {
                if (u.right != null) {
                    next = u.right;
                    level++;
                    gap = (gap == k? 0 : (gap+1)/2-1);
                }
                else {
                    // R was missing but left was not so we need to add in stuff
                    int g = (gap+1)/2-1;
                    int missing = 1;
                    for( int j = level+1; j <= h; j++ ) {
                        StringBuilder thisLevel = levels.get(j);
                        if( thisLevel.length() == 0 )
                            thisLevel.append(gapString(g));
                        thisLevel.append(gapString((2*g+1)*missing+k*missing));
                        g = (g == k? 0 : (g+1)/2-1);
                        missing *= 2;
                    }
                    next = u.parent;
                    level--;
                    gap = (gap == 0? k : (gap+1)*2-1);
                }
            } else {
                next = u.parent;
                level--;
                gap = (gap == 0? k : (gap+1)*2-1);
            }
            prev = u;
            u = next;
        }

        StringBuilder s = new StringBuilder();
        for( int i=0; i < levels.size(); i++ ) {
            s.append(levels.get(i).toString() + "\n");
        }
        return s.toString();
    }

    private String gapString(int k) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < k; i++) {
            s.append(" ");
        }
        return s.toString();
    }


    public int height() {
        return height(r);
    }

    /**
     * @return the size of the subtree rooted at u
     */
    protected int height(Node u) {
        if (u == null) return -1;
        return 1 + Math.max(height(u.left), height(u.right));
    }



    /**
     * Returns an iterator over the Strings in the tree in in-order.
     * @param u
     * @return
     */
    public Iterator<String> iterator(Node u) {
        class BTI implements Iterator<String> {
            protected Node w, prev;
            public BTI(Node iw) {
                w = iw;
            }
            public boolean hasNext() {
                return w != null;
            }
            public String next() {
                String x = w.s;
                prev = w;
                w = nextNode(w);
                return x;
            }
        }
        return new BTI(u);
    }

    public Iterator<String> iterator() {
        return iterator(firstNode());
    }


    /**
     * Find the first node in an in-order traversal
     * @return the first node reported in an in-order traversal
     */
    private Node firstNode() {
        Node w = r;
        if (w == null) return null;
        while (w.left != null)
            w = w.left;
        return w;
    }

    /**
     * Find the node that follows w in an in-order traversal
     * @param w
     * @return the node that follows w in an in-order traversal
     */
    private Node nextNode(Node w) {
        if (w.right != null) {
            w = w.right;
            while (w.left != null)
                w = w.left;
        } else {
            while (w.parent != null && w.parent.left != w)
                w = w.parent;
            w = w.parent;
        }
        return w;
    }


    /**
     * Returns a string representation of the set of bases in the node u.
     * Used by the autograder; do not modify.
     */
    private String setToBases(Node u) {
        ArrayStack<Character> s = new ArrayStack<Character>();
        for( int i=0; i < u.set.length; i++ ) {
            if( u.set[i] ) s.add(DNABases[i]);
        }
        return u.s + ": " + s.toString();
    }

    /**
     * Returns a string representation of the set of bases in the node u.
     * Used by the autograder; do not modify.
     *
     * @return
     */
    public ArrayStack<String> allSets() {
        ArrayStack<String> s = new ArrayStack<String>();
        Node w = firstNode();
        while( w != null ) {
            s.add(setToBases(w));
            w = nextNode(w);
        }
        return s;
    }



    // You should not need to modify anything above this line.
    // If you find it helpful to add fields to the Node class, you may do so.

    /**
     * Adds a new node with String child as a child of the node with String parent
     * Throws an IllegalArgumentException if the parent is not found or if the
     * parent already has 2 children.
     * Tries to add as left child, but if left child exists, adds as right child.
     * if parent == null then we are adding the root node and current root becomes left child.
     */
    public void addChild(String parent, String child) throws IllegalArgumentException {
        // TODO(student): implement this method
    }


    /**
     * Constructs a PhyloTree out of elements in the ArrayStack a.
     * @param a
     */
    public PhyloTree(ArrayStack<String> a) {
        this();
        // TODO(student): implement this method
    }




    /**
     * For each node u, compute the set of likely bases at specific index of node's String.
     * For a node u, u.set[i] is true if the i-th base is a likely base for the node u.
     * For a leaf u, u.set[i] == true if the i-th base is the same as the i-th base of u.s
     * For an internal node u with children left and right, if left and right overlap,
     * u.set will be the elements in left and right that are in common (their intersection)
     * If left and right do not overlap, then u.set will be their combination (their union).
     *
     * @param index
     * @throws IllegalArgumentException
     */
    public void computeSets(int index) throws IllegalArgumentException {
        // TODO(student): implement this method
    }


    /**
     * Computes the most likely Phylogenetic Tree for the current leaf nodes.
     * See the Lab 5 Specifications for details.
     */
    public void likelyTree() {
        return;
    }




    // Here are some *limited* local tests.
    // You can and should add your own to test your code!

    private static void testAddChild() {
        testAddChildVariety(true);

        for( int n=0; n < 8; n++ ) {
            testAddChildLongSkinny(n, 1, true);
        }
        for( int n=0; n < 9; n++ ) {
            testAddChildBalanced(n, 1);
        }

        // Now test k > 1
        testAddChildLongSkinny(5, 2, true);
        testAddChildBalanced(5, 2);
        testAddChildBalanced(6, 2);
        testAddChildBalanced(6, 3);

        testAddChildBalanced(15, 2);

        testAddChildBalanced(26,3);
        testAddChildBalanced(30, 4);


        /*
        // Now try the exceptions
        testAddChildExceptions();
        */
    }


    private static PhyloTree testAddChildVariety(boolean print) {
        System.out.println("Testing PhyloTree addChild..." );
        PhyloTree t = new PhyloTree(1);
        t.addChild(null, "A");
        if( print ) {
            System.out.println(t.prettyPrint());
            System.out.println("Expect [A]: " + t);
        }

        t.addChild( "A", "B");
        if( print ) {
            System.out.println(t.prettyPrint());
            System.out.println("Expect [B, A]: " + t);
        }

        t.addChild( "A", "C");
        if( print ) {
            System.out.println("Expect [B, A, C]: " + t);
            System.out.println(t.prettyPrint());
        }

        t.addChild( "C", "F");
        if( print ) {
            System.out.println("Expect [B, A, F, C]: " + t);
            System.out.println(t.prettyPrint());
        }
        t.addChild( "B", "D");
        if( print ) {
            System.out.println("Expect [D, B, A, F, C]: " + t);
            System.out.println(t.prettyPrint());
        }

        t.addChild( "F", "L");
        if( print ) {
            System.out.println("Expect [D, B, A, L, F, C]: " + t);
            System.out.println(t.prettyPrint());
        }


        t.addChild( "C", "G");
        if( print ) {
            System.out.println("Expect [D, B, A, L, F, C, G]: " + t);
            System.out.println(t.prettyPrint());
        }


        t.addChild( "F", "M");
        if( print ) {
            System.out.println("Expect [D, B, A, L, F, M, C, G]: " + t);
            System.out.println(t.prettyPrint());
        }


        t.addChild( "B", "E");
        if( print ) {
            System.out.println("Expect [D, B, E, A, L, F, M, C, G]: " + t);
            System.out.println(t.prettyPrint());
        }

        return t;
    }

    private static PhyloTree testAddChildLongSkinny(int n, int k, boolean print) {
        System.out.println( "Testing PhyloTree addChildLongSkinny with n=" + n + " and k=" + k + "..." );
        PhyloTree t = new PhyloTree(k);
        ArrayStack<String> a = new ArrayStack<String>();
        String parent = null;
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append((char)('a'+i));
            }
            String child = sb.toString();
            a.add(child);
            t.addChild(parent, child);
            parent = sb.toString();
        }
        if( print ) {
            System.out.println(t.prettyPrint());
            System.out.println("Expect reverse of: " + a + ": " + t);
        }
        return t;
    }

    private static PhyloTree testAddChildBalanced(int n, int k) {
        System.out.println( "Testing PhyloTree addChildBalanced with n=" + n + " and k=" + k + "..." );
        PhyloTree t = new PhyloTree(k);
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append((char)('a'+i));
            }
            a.add(sb.toString());
        }
        for( int i=0; i < n; i++ ) {
            String parent = null;
            if( i > 0 ) parent = a.get( (i-1)/2);
            t.addChild(parent, a.get(i));
        }
        System.out.println(t.prettyPrint());
        return t;
    }

    private static void testAddChildExceptions() {
        System.out.println( "Testing PhyloTree addChildExceptions..." );
        System.out.println( "\tYou have to uncomment each potential exception-throwing line one at a time in order to test");
        PhyloTree t = new PhyloTree();
        t.addChild(null, "A"); // this is okay
        t.addChild("A", null ); // this is okay
        //t.addChild(null, "A"); // should throw IllegalArgumentException for duplicate
        //t.addChild("B", "C"); // should throw IllegalArgumentException for parent not existing
        //t.addChild("A", "CC" ); //should throw IllegalArgumentException for wrong length
        t.addChild("A", "B"); // this is okay
        //t.addChild("A", "C"); // this is not okay as A already has 2 children
    }



    private static void testArrayConstructor(int n, int k) {
        System.out.println( "Testing PhyloTree array constructor with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append((char)('a'+i));
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        System.out.println(t.prettyPrint());
        Iterator<String> it = t.iterator();
        int i = 0;
        while( it.hasNext() ) {
            String next = it.next();
            System.out.print( next + " " );
            i++;
        }
        System.out.println();
        if( i != n ) {
            System.out.println( "Error: expected " + n + " nodes, but got " + i + " nodes." );
        }
    }

    private static void testArrayConstructor() {
        for( int n=1; n < 10; n++ ) {
            testArrayConstructor(n, 1);
        }
        testArrayConstructor(7, 2);
        testArrayConstructor( 8, 3);
        testArrayConstructor( 9, 4 );
        testArrayConstructor( 10, 3);
        testArrayConstructor( 15, 4);
        testArrayConstructor( 26, 1 );
    }


    private static void testComputeSets() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("AAA");
        PhyloTree t = new PhyloTree(a);
        System.out.println(t.prettyPrint());
        t.computeSets(0);   // should be [A] for the only node
        System.out.println("Expect: [AAA: [A]]: " + t.allSets() );
        t.computeSets(1);   // should be [A] for the only node
        System.out.println("Expect: [AAA: [A]]: " + t.allSets() );
        t.computeSets(2);   // should be [A] for the only node
        System.out.println("Expect: [AAA: [A]]: " + t.allSets() );


        a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
        t = new PhyloTree(a);
        t.computeSets(0);
        System.out.println("Expect: [ACA: [A], AAA: [A]]: " + t.allSets() );
        t.computeSets(1);
        System.out.println("Expect: [ACA: [C], AAA: [C]]: " + t.allSets() );
        t.computeSets(2);
        System.out.println("Expect: [ACA: [A], AAA: [A]]: " + t.allSets() );


        a.add("AGA");
        a.add("ATA");
        t = new PhyloTree(a);
        System.out.println(t.prettyPrint());
        t.computeSets(0);
        System.out.println( "Expect [ATA: [A], ACA: [A], AAA: [A], AGA: [A]] since all index 0's are As: " +  t.allSets() );
        t.computeSets(1);
        System.out.println( "Expect [ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]] for index 1: " + t.allSets() ); // left child but no right, root union
        a.add("ATC");
        t = new PhyloTree(a);
        System.out.println(t.prettyPrint());
        t.computeSets(2);
        System.out.println( "Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets() ); // left child but no right

        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("A");
        a.add("C");
        a.add("G");
        a.add("T");
        t = new PhyloTree(a);
        System.out.println(t.prettyPrint());
        t.computeSets(0);
        System.out.println( "Expect [A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]: " + t.allSets() ); // left child but no right

    }

    private static void testLikelyTree() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("AAA");
        comp2402w24l5.PhyloTree t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before: \n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect AAA: " + t.prettyPrint());

        a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
        t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before: \n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect [ACA, ACA]: " + t);


        a.add("AGA");
        a.add("ATA");
        t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before: \n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect [ATA, ATA, AGA, AGA]: " + t);


        a.add("ATC");
        t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before: \n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect [ATA, ATA, ATC, AGA, AGA]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("A");
        a.add("C");
        a.add("G");
        a.add("T");
        t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before:\n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect [A, A, C, A, G, G, T]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("AA");
        a.add("CC");
        a.add("GG");
        a.add("TT");
        t = new comp2402w24l5.PhyloTree(a);
        System.out.println("before:\n" + t.prettyPrint());
        t.likelyTree();
        System.out.println("after, expect [AA, AA, CC, AA, GG, GG, TT]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add("CCC");
        a.add("AAA");
        t = new comp2402w24l5.PhyloTree(a);
        t.addChild("AAA", "AAC" );
        t.addChild( "AAC", "AAG");
        t.addChild( "AAG", "AAT");
        t.addChild("AAT", "ACA");
        t.addChild("ACA", "AGA" );
        System.out.println("before:\n" + t.prettyPrint()); // Long and skinny tree
        t.likelyTree();
        System.out.println("after, expect [AGA, AGA, AGA, AGA, AGA, AGA, AGA, ACA, CCC]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add("CC");
        a.add("GG");
        a.add("UU");
        t = new comp2402w24l5.PhyloTree(a);
        t.addChild("GG", "AT");
        t.addChild("GG", "AG");
        t.addChild("UU", "AC");
        t.addChild("UU", "AA");
        t.likelyTree();
        System.out.println( "after, expect [AT, AG, AG, AC, AC, AC, AA, AC, CC]: " + t );
    }



    // These are a few examples of how to use main to run local tests
    // You should test more extensively than this.
    public static void main(String[] args) {
        System.out.println("Testing PhyloTree via PhyloTree.main...");
        System.out.println("These are limited local tests; add more tests to test more thoroughly.");
        System.out.println("You should also try testing via tests/PhyloTreeTest.java");

        int n = 7;

        // Comment out any of the following methods to stop testing that method.
        //testAddChild();
        //testArrayConstructor();
        // testComputeSets();
        testLikelyTree();

    }


}
