package comp2402w24l5.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l5 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l5/tests/BondTest.java
// java -cp "./lib/*:." comp2402w24l5/tests/BondTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l5/tests/BondTest.java
// java -cp "./lib/*;." comp2402w24l5/tests/BondTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import comp2402w24l5.Bond;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class BondTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        System.out.println("\tTesting bond() all same character n=" + n + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator(all_same, n, (n<100));
            assertEquals(0, Bond.bond(gen),
                    "All same character should have 0 bonds" + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting bond() alternating characters short bonds, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n<100));
        assertEquals((n-6)/2, Bond.bond(gen),
                "Alternating characters A, U, C, G, length div by 4 " + gen); // Expect property

        gen = new AlternatingCharGenerator(bases, (n/4)*4-2, (n<100));
        assertEquals((n-6)/2, Bond.bond(gen),
                "Alternating characters A, U, C, G, length div by 2 " + gen); // Expect property

        gen = new AlternatingCharGenerator(bases, (n/4)*4-1, (n<100));
        assertEquals((n-5)/2, Bond.bond(gen),
                "Alternating characters A, U, C, G, odd length " + gen); // Expect not property

        char[] reordered_bases = {'U', 'A', 'G', 'C'};
        gen = new AlternatingCharGenerator(reordered_bases, (n/4)*4, (n<100));
        assertEquals((n-6)/2, Bond.bond(gen),
                "Alternating characters U, A, G, C length div by 4 " + gen); // Expect not property
    }

    @Test
    public static void nestedBonds(int n) {
        System.out.println("\tTesting bond() nested bonds, e.g. AACCCCUU n~" + n + "...");
        char[] reordered_bases = {'A', 'A', 'C', 'C', 'C', 'C', 'U', 'U'};
        n = (n/8)*8;
        InputGenerator<Character> gen = new AlternatingCharGenerator(reordered_bases, n, (n<100));
        assertEquals((n-4)/2, Bond.bond(gen),
                "Nested bonds length div by 8 most bonded " + gen); // Expect property

        gen = new AlternatingCharGenerator(reordered_bases, n+2, (n<100));
        assertEquals((n-4)/2, Bond.bond(gen),
                "Nested bonds length =2 % 8 " + gen); // Expect not property

        n = (n+10)/14*14;
        String seq = "ACAAAAGUGAAAAC";
        gen = new AlternatingCharGenerator(seq.toCharArray(), (n/14)*14, (n<100));
        assertEquals((n/14)*3, Bond.bond(gen),
                "Nested bonds " + gen);
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

        int[] sizes = {8, 100, 400};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing bond() correctness input all same char...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing bond() correctness input alternating char...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing bond() correctness input nested bonds...");
                nestedBonds(10);
                break;
            case 3:
                System.out.println( "Testing bond() correctness edge case empty strand...");
                allSame(0);
                break;
            case 4: // performance time tests
                int size=256;
                System.out.println( "Testing bond() O(n^3) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 5:// performance time tests
                size=1024;
                System.out.println( "Testing bond() O(n^3) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 4 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 6: // performance space tests
                size=256;
                System.out.println( "Testing bond() O(n^2) space performance size=" + size + "...");
                System.out.println( "If you didn't already, run this test (6) with -Xmx4m");
                allSame(size);
                alternatingChar(size);
                break;
            case 7: // performance space tests
                size=1024;
                System.out.println( "Testing bond() O(n^2) space performance size=" + size + "...");
                System.out.println( "If you didn't already, run this test (7) with -Xmx8m");
                allSame(size);
                alternatingChar(size);
                break;
            default:
                System.out.println("To run an individual test: java BondTest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);

        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
