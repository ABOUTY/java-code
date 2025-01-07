package comp2402w24l5.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l5 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l5/tests/PhyloTreeTest.java
// java -cp "./lib/*:." comp2402w24l5/tests/PhyloTreeTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l5/tests/PhyloTreeTest.java
// java -cp "./lib/*;." comp2402w24l5/tests/PhyloTreeTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import ods.ArrayStack;
import ods.ArrayDeque;

import comp2402w24l5.PhyloTree;


public class PhyloTreeTest {
    static char[] bases = {'A', 'C', 'G', 'T'};

    @Test
    public static void testEmptyConstructor() {
        PhyloTree t = new PhyloTree();
        assertEquals(0, t.size(), "PhyloTree should be empty after construction");
        assertEquals("[]", t.toString(), "PhyloTree should be empty after construction");
        Iterator<String> it = t.iterator();
        assertFalse(it.hasNext(), "PhyloTree iterator should be empty after construction");
    }

    @Test
    public static void addChildExceptions() {
        PhyloTree t = new PhyloTree();

        t.addChild(null, null); // no exception
        t.addChild(null, "A");  // no exception // Now A has one child
        t.addChild("A", null ); // no exception // Now A has two children

        assertThrows(IllegalArgumentException.class,
                () -> t.addChild(null, "A"),
                "Should throw IllegalArgumentException when adding a duplicate String");
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("B", "C"),
                "Should throw IllegalArgumentException when parent doesn't exist");
        t.addChild(null, "B" ); // no exception // Now B has one child
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("B", "CC"),
                "Should throw IllegalArgumentException when child String is not length k");
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("A", "B"),
                "Should throw IllegalArgumentException when parent already has 2 children");
    }

    private static PhyloTree addChildLongSkinny(int n, int k) {
        System.out.println( "Testing PhyloTree addChildLongSkinny with n=" + n + " and k=" + k + "..." );
        String tester = "" + (10*n);
        PhyloTree t = new PhyloTree(tester.length()*k);
        ArrayDeque<String> d = new ArrayDeque<>();
        String parent = null;
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i));
            }
            String child = sb.toString();
            d.add(0, child);
            t.addChild(parent, child);
            parent = sb.toString();
        }
        assertEquals( d.toString(), t.toString(),
                "PhyloTree toString should be " + d + " after construction");

        Iterator<String> it = t.iterator();
        for( int i=0; i < n; i++ ) {
            assertTrue(it.hasNext(), "PhyloTree iterator should have next after construction");
            assertEquals(d.get(i), it.next(), "PhyloTree iterator should have " + d.get(i) + " next after construction");
        }

        assertEquals( n, t.size(), "PhyloTree should have size " + n + " after construction");

        return t;
    }

    private static PhyloTree addChildBalanced(int n, int k) {
        System.out.println( "Testing PhyloTree addChildBalanced with n=" + n + " and k=" + k + "..." );
        String tester = "" + (10*n);
        PhyloTree t = new PhyloTree(tester.length()*k);

        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i));
            }
            a.add(sb.toString());
        }
        for( int i=0; i < n; i++ ) {
            String parent = null;
            if( i > 0 ) parent = a.get( (i-1)/2);
            t.addChild(parent, a.get(i));
        }

        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        // This is a very limited test, as there is no easy way to test
        // that you've put the elements in the right spot in the tree.
        return t;
    }

    @Test
    public static void testArrayConstructor() {
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

    @Test
    public static void testArrayConstructorExceptions() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("A");
        a.add(null);
        a.add("B");
        new PhyloTree(a); // no exceptions here
        a.add("XYZ"); // left child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding Strings of different lengths, left child");

        a.set(3, "C");
        a.set(0, "");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at root");

        a.set(0,"A");
        a.set(3, "");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at left child");

        a.set(3, "C");
        a.add(""); // right child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at right child");
        a.set(4, "XYZ");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding Strings of different lengths");

        a.set(4, "C");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a), // right child
                "Should throw IllegalArgumentException when adding duplicate Strings");

        a.set(3, "B"); // left child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding duplicate Strings");

        a.clear();
        a.add("");
        a.add("");
        a.add("");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at root");

    }

    public static void testArrayConstructor(int n, int k) {
        System.out.println( "Testing PhyloTree array constructor with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append((char)('a'+i));
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a); // should not throw exceptions
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");

        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        int i = 0;
        while( it.hasNext() ) {
            String next = it.next();
            i++;
        }
        assertEquals( n, i, "PhyloTree iterator should have " + n + " nexts after construction" );

        // Hard-code some toStrings for basic, basic testing
        if( n == 1 && k==1 ) {
            assertEquals("[a]", t.toString(), "PhyloTree toString should be [a] after construction");
        } else if( n == 2 && k==1 ) {
            assertEquals("[b, a]", t.toString(), "PhyloTree toString should be [b, a] after construction");
        } else if( n == 3 && k==1 ) {
            assertEquals("[b, a, c]", t.toString(), "PhyloTree toString should be [b, a, c] after construction");
        } else if( n == 4 && k==1 ) {
            assertEquals("[d, b, a, c]", t.toString(), "PhyloTree toString should be [d, b, a, c] after construction");
        } else if ( n == 8 && k == 3 ) {
            assertEquals("[hhh, ddd, bbb, eee, aaa, fff, ccc, ggg]", t.toString(), "PhyloTree toString should be [hhh,ddd,bbb,eee,aaa,fff,ccc,ggg] after construction");
        }
    }

    public static void testConstructorPerformance(int n, int k) {
        System.out.println( "Testing PhyloTree constructor with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");

        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        int i = 0;
        while( it.hasNext() ) {
            String next = it.next();
            i++;
        }
        assertEquals( n, i, "PhyloTree iterator should have " + n + " nexts after construction" );

    }


    @Test
    private static void testComputeSetsVarious() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("AAA");
        PhyloTree t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 0: " + t.allSets());
        t.computeSets(1);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 1: " + t.allSets());
        t.computeSets(2);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 2: " + t.allSets());


        a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
        t = new PhyloTree(a);
        t.computeSets(0);
        assertEquals("[ACA: [A], AAA: [A]]", t.allSets().toString(),
                "Expect [ACA: [A], AAA: [A]] for index 0: " + t.allSets());
        t.computeSets(1);
        assertEquals("[ACA: [C], AAA: [C]]", t.allSets().toString(),
                "Expect [ACA: [C], AAA: [C]] for index 1: " + t.allSets());
        t.computeSets(2);
        assertEquals("[ACA: [A], AAA: [A]]", t.allSets().toString(),
                "Expect [ACA: [A], AAA: [A]] for index 2: " + t.allSets());

        a.add("AGA");
        a.add("ATA");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);
        assertEquals("[ATA: [A], ACA: [A], AAA: [A], AGA: [A]]", t.allSets().toString(),
                "Expect [ATA: [A], ACA: [A], AAA: [A], AGA: [A]] since all index 0's are As: " + t.allSets());
        t.computeSets(1);
        assertEquals("[ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]]", t.allSets().toString(),
                "Expect [ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]] for index 1: " + t.allSets());
        a.add("ATC");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(2);
        assertEquals("[ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]]", t.allSets().toString(),
                "Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets());
        //System.out.println( "Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets() ); // left child but no right

        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("A");
        a.add("C");
        a.add("G");
        a.add("T");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);
        assertEquals("[A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]", t.allSets().toString(),
                "Expect [A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]: " + t.allSets());
    }

    public static void testComputeSetsExceptions() {
        PhyloTree t = new PhyloTree();
        t.addChild(null, "A");
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(1),
                "Should throw IllegalArgumentException when computeSet with index > k");
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(-1),
                "Should throw IllegalArgumentException when computeSet with index < 0");
        t.addChild("A", null);
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(0),
                "Should throw IllegalArgumentException when computeSet has null leaf nodes");
    }


    @Test
    private static void testLikelyTreeVarious() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("AAA");
        PhyloTree t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.likelyTree();
        assertEquals("[AAA]", t.toString(),
                "Expect [AAA] for single node AAA: ");


        a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
        t = new PhyloTree(a);
        t.likelyTree();
        assertEquals("[ACA, ACA]", t.toString(),
                "Expect [ACA, ACA] when only leaf is ACA: " + t);


        a.add("AGA");
        a.add("ATA");
        t = new PhyloTree(a);
        t.likelyTree();
        assertEquals("[ATA, ATA, AGA, AGA]", t.toString(),
                "Expect [ATA, ATA, AGA, AGA] with leaf nodes AGA, ATA " + t);


        a.add("ATC");
        t = new PhyloTree(a);
        t.likelyTree();
        assertEquals("[ATA, ATA, ATC, AGA, AGA]", t.toString(),
                "Expect [ATA, ATA, ATC, AGA, AGA] when leaf nodes conflict " + t);


        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("A");
        a.add("C");
        a.add("G");
        a.add("T");
        t = new PhyloTree(a);
        t.likelyTree();
        assertEquals("[A, A, C, A, G, G, T]", t.toString(),
                "None of the leaf nodes agree: " + t);


        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("AA");
        a.add("CC");
        a.add("GG");
        a.add("TT");
        t = new PhyloTree(a);
        t.likelyTree();
        assertEquals("[AA, AA, CC, AA, GG, GG, TT]", t.toString(),
                "Expect [AA, AA, CC, AA, GG, GG, TT]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add("CCC");
        a.add("AAA");
        t = new PhyloTree(a);
        t.addChild("AAA", "AAC" );
        t.addChild( "AAC", "AAG");
        t.addChild( "AAG", "AAT");
        t.addChild("AAT", "ACA");
        t.addChild("ACA", "AGA" );
        t.likelyTree();
        assertEquals("[AGA, AGA, AGA, AGA, AGA, AGA, AGA, ACA, CCC]", t.toString(),
                "Expect [AGA, AGA, AGA, AGA, AGA, AGA, AGA, ACA, CCC]: " + t);

        a.clear();
        a.add(null);
        a.add(null);
        a.add("CC");
        a.add("GG");
        a.add("UU");
        t = new PhyloTree(a);
        t.addChild("GG", "AT");
        t.addChild("GG", "AG");
        t.addChild("UU", "AC");
        t.addChild("UU", "AA");
        t.likelyTree();
        assertEquals("[AT, AG, AG, AC, AC, AC, AA, AC, CC]", t.toString(),
                "Pass down the label of the parent if possible " + t);
    }

    public static void testLikelyTreeExceptions() {
        PhyloTree t = new PhyloTree();
        t.addChild(null, "A");
        t.addChild("A", null);
        assertThrows(IllegalArgumentException.class, () -> t.likelyTree(),
                "Should throw IllegalArgumentException when likelyTree has null leaf nodes");
    }

    private static ArrayDeque<String> generate(int k) { // O(4^k) time
        // Generate all length-k strings over bases
        ArrayDeque<String> a = new ArrayDeque<String>();
        if( k == 0 ) {
            a.add(new String(""));
            return a;
        }
        ArrayDeque<String> b = generate(k-1);
        for( int i=0; i < b.size(); i++ ) { // O(4^{k-1})
            for( int j=0; j < bases.length; j++ ) {
                a.add(b.get(i) + bases[j]); // O(k)
            }
        }
        return a;
    }

    private static void testLikelyTreePerformance(int k) {
        System.out.println( "Testing PhyloTree likelyTree performance with k=" + k + "..." );
        System.out.println( "\tAround 4^k leaf nodes will be generated.");
        ArrayDeque<String> b = generate(k);
        ArrayStack<String> a = new ArrayStack<>();
        for( int i=0; i < b.size()-1; i++ ) {
            a.add(null);
        }
        for( int i=0; i < b.size(); i++ ) {
            a.add(b.get(i));
        }
        PhyloTree t = new PhyloTree(a);
        t.likelyTree();
    }

    private static void testLikelyTreeIterativePerformance(int k) {
        System.out.println( "Testing PhyloTree likelyTree iterative performance with k=" + k + "..." );
        System.out.println( "\tAround 4^k tall tree will be generated.");
        PhyloTree t = new PhyloTree(k);
        ArrayDeque<String> b = generate(k);
        for( int i=0; i < b.size(); i++ ) {
            t.addChild(null, b.get(i));
        }
        t.likelyTree();
    }

    public static void main(String[] args) {
        int n=1000;
        try {
            if (args.length == 1) {
                n = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            System.exit(-2);
        }

        int[] sizes = {6, 18, 100, 1000, 10000, 100000, 1000000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println("Testing PhyloTree empty constructor...");
                testEmptyConstructor();
                break;
            case 1:
                System.out.println( "Testing addChild exceptions...");
                addChildExceptions();
                break;
            case 2:
                System.out.println( "Testing addChild correctness long skinny small k...");
                for( int i=0; i < sizes.length; i++) {
                    addChildLongSkinny(sizes[i], 1);
                }
                break;
            case 3:
                System.out.println( "Testing addChild correctness balanced small k...");
                for( int i=0; i < sizes.length; i++) {
                    addChildBalanced(sizes[i], 1);
                }
                break;
            case 4:
                System.out.println( "Testing addChild correctness long skinny...");
                for( int i=0; i < sizes.length; i++) {
                    addChildLongSkinny(sizes[i], 2);
                }
                break;
            case 5:
                System.out.println( "Testing addChild correctness balanced...");
                for( int i=0; i < sizes.length; i++) {
                    addChildBalanced(sizes[i], 3);
                }
                break;
            case 6:
                System.out.println( "Testing PhyloTree ArrayStack constructor...");
                testArrayConstructor();
                break;
            case 7:
                System.out.println( "Testing PhyloTree ArrayStack constructor exceptions...");
                testArrayConstructorExceptions();
                break;
            case 8:
                System.out.println( "Testing PhyloTree ArrayStack constructor O(n) time performance...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=0; i < sizes.length; i++) {
                    testConstructorPerformance(sizes[i], i%4+1);
                }
                break;
            case 9:
                System.out.println( "Testing PhyloTree ArrayStack constructor O(1) call stack...");
                System.out.println( "\tRun this test with -Xss144k to limit the call stack.");
                testConstructorPerformance(4000000, 1);
                break;
            case 10:
                System.out.println( "Testing computeSets various...");
                testComputeSetsVarious();
                break;
            case 11:
                System.out.println( "Testing computeSets exceptions...");
                testComputeSetsExceptions();
                break;
            case 12:
                System.out.println( "Testing likelyTree various...");
                testLikelyTreeVarious();
                break;
            case 13:
                System.out.println( "Testing likelyTree exceptions...");
                testLikelyTreeExceptions();
                break;
            case 14:
                int k = 6; // 4^k=4096
                System.out.println( "Testing likelyTree() O(n^2) time performance n~" + 8000 + ", k=O(1)...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                testLikelyTreePerformance(k);
                break;
            case 15:
                k = 9; // 4^k=262144
                System.out.println( "Testing likelyTree() O(nk) time performance n~" + 500000 + ", k=O(log n)...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                testLikelyTreePerformance(k);
                break;
            case 16:
                k = 6;
                System.out.println( "Testing likelyTree() iterative performance n~" + 4000 + ", k=O(1)...");
                System.out.println( "Run this test with the -Xss256k flag to limit call stack size.");
                testLikelyTreeIterativePerformance(k);
                break;
            case 17:
                k = 9; // 4^k=262144
                System.out.println( "Testing likelyTree() iterative performance n~" + 250000 + ", k=O(log n)...");
                System.out.println( "Run this test with the -Xss256k flag to limit call stack size.");
                testLikelyTreeIterativePerformance(k);
                break;
            default:
                System.out.println("To run an individual test: java PhyloTree [test number]");
                for( int i=0; i < 14; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");    }

}
