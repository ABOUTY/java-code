package comp2402w24l5.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l5 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l5/tests/ReconstructTest.java
// java -cp "./lib/*:." comp2402w24l5/tests/ReconstructTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l5/tests/ReconstructTest.java
// java -cp "./lib/*;." comp2402w24l5/tests/ReconstructTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l5.Reconstruct;
import input.InputGenerator;
import input.AlternatingCharGenerator;
import input.RandomCharGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import ods.ArrayDeque;


public class ReconstructTest {

    private static char[] bases = {'A', 'C', 'G', 'U'};

    // O(nk) time where n=len(sequence)
    public static void populateListMap(String sequence, int k, ArrayDeque<String> geneList,
                                       HashMap<String,Integer> geneToCount) {
        StringBuilder sb = new StringBuilder();
        for( int i=0; i < sequence.length(); i++ ) { // O(n) iterations, O(k) iterations each
            sb.append(sequence.charAt(i));
            if( sb.length() == k ) {
                String gene = sb.toString(); // O(k)
                geneList.add(gene); // add it to the input list of genes // O(1)
                geneToCount.put( gene, geneToCount.getOrDefault(gene, 0)+1 ); // and store its count for testing later
                sb.deleteCharAt(0); // O(k)
            }
        }
    }

    public static void checkResult(HashMap<String,Integer> expected, int k, String result) {
        // Now check whether result produces the right geneList/count
        StringBuilder sb = new StringBuilder();
        for( int i=0; i < result.length(); i++ ) { // O(n) iterations, O(nk) total
            sb.append(result.charAt(i)); // O(1)
            if( sb.length() == k ) {
                // Check whether the current sb is in expected
                String gene = sb.toString(); // O(k)
                if( expected.containsKey(gene) ) { // O(1)
                    expected.put( gene, expected.get(gene)-1 ); // O(1)
                    if( expected.get(gene) == 0 ) { // O(1)
                        expected.remove(gene); // O(1)
                    }
                } else {
                    //System.out.println( "expected: " + expected);
                    // System.out.println( "result: " + result );
                    assertTrue( false, "Error: " + gene + " is not in expected" ); // O(1)
                }
                sb.deleteCharAt(0); // O(k)
            }
        }
        assertTrue( expected.isEmpty(), "Error: " + expected + " not empty" ); // O(1
    }

    @Test
    public static void uniqueInOrder(int k) {
        System.out.println("\tTesting reconstruct() gene list unique and in order k=" + k + "...");

        // Hard-code this sequence that has unique substrings of length 3, 4, 5...
        String sequence = "AAUAACAAGCCACCGACUGGCGGUGCAUUCUUGUUA";

        // From the input sequence, construct the list of genes that will be in the input
        // Then run the algorithm, and check that its String also produces the same gene list.
        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount);
        // System.out.println( "\nsequence: " + sequence);
        // System.out.println( "input: " + geneList );
        // System.out.println( geneToCount );

        StringBuilder sb = new StringBuilder();
        for( String gene : geneList ) {
            sb.append(gene);
        }
        String s = sb.toString();
        InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
        String result = Reconstruct.reconstruct(gen, k);
        System.out.println( "sequen: " + sequence);
        System.out.println( "result: " + result );
        checkResult(geneToCount, k, result);
        //System.out.println( "result: " + result );
    }

    @Test
    public static void uniqueShuffled(int k) {
        System.out.println("\tTesting reconstruct() gene list unique and shuffled k=" + k + "...");

        // Hard-code this sequence that has unique substrings of length 3, 4, 5...
        String sequence = "AAUAACAAGCCACCGACUGGCGGUGCAUUCUUGUUA";

        // From the input sequence, construct the list of genes that will be in the input
        // Then run the algorithm, and check that its String also produces the same gene list.
        for( int i=0; i < 5; i++ ) {
            ArrayDeque<String> geneList = new ArrayDeque<>();
            HashMap<String,Integer> geneToCount = new HashMap<>();

            populateListMap(sequence,k,geneList,geneToCount);
            Random rand = new Random(i);
            Collections.shuffle(geneList, rand);

            StringBuilder sb = new StringBuilder();
            for (String gene : geneList) {
                sb.append(gene);
            }
            String s = sb.toString();
            InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
            String result = Reconstruct.reconstruct(gen, k);
            checkResult(geneToCount, k, result);
        }
    }

    @Test
    public static void empty() {
        System.out.println("\tTesting reconstruct() empty list...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
        assertEquals("", Reconstruct.reconstruct(gen, 10),
                "Empty sequence with k>0 should return empty string " + gen);
    }


    @Test
    public static void alternatingInOrder(int k) {
        System.out.println("\tTesting reconstruct() genes not unique but in order and output unique k=" + k + "...");

        String sequence = "AUCGAUCGAUCGAUCGAUCGAUCGAUCGAUCGAUCG";

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount);

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        StringBuilder sb = new StringBuilder();
        for (String gene : geneList) {
            sb.append(gene);
        }
        String s = sb.toString();
        InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
        String result = Reconstruct.reconstruct(gen, k);
        checkResult(geneToCount, k, result);
    }

    @Test
    public static void alternatingShuffled(int k) {
        System.out.println("\tTesting reconstruct() genes not unique, shuffled, but output unique k=" + k + "...");

        String sequence = "AUCGAUCGAUCGAUCGAUCGAUCGAUCGAUCGAUCG";

        for( int i=0; i < 5; i++ ) {
            ArrayDeque<String> geneList = new ArrayDeque<>();
            HashMap<String,Integer> geneToCount = new HashMap<>();

            populateListMap(sequence,k,geneList,geneToCount);

            Random rand = new Random(i);
            Collections.shuffle(geneList, rand);
            //System.out.println( geneList );
            //System.out.println( geneToCount );

            StringBuilder sb = new StringBuilder();
            for (String gene : geneList) {
                sb.append(gene);
            }
            String s = sb.toString();
            InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
            String result = Reconstruct.reconstruct(gen, k);
            checkResult(geneToCount, k, result);
        }
    }

    @Test
    public static void notUniqueInOrder(int k) {
        System.out.println("\tTesting reconstruct() genes not unique but in order and output not unique k=" + k + "...");

        String sequence = "AAAAAAGAAAAAAGAAAAAACAAAAAACAAAAAACA";

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount);

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        StringBuilder sb = new StringBuilder();
        for (String gene : geneList) {
            sb.append(gene);
        }
        String s = sb.toString();
        InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
        String result = Reconstruct.reconstruct(gen, k);
        checkResult(geneToCount, k, result);
    }

    @Test
    public static void notUniqueShuffled(int k) {
        System.out.println("\tTesting reconstruct() genes not unique, shuffled, output not unique k=" + k + "...");

        String sequence = "AAAAAAGAAAAAAGAAAAAACAAAAAACAAAAAACA";

        for( int i=0; i < 5; i++ ) {
            ArrayDeque<String> geneList = new ArrayDeque<>();
            HashMap<String,Integer> geneToCount = new HashMap<>();

            populateListMap(sequence,k,geneList,geneToCount);
            Random rand = new Random(i);
            Collections.shuffle(geneList, rand);
            //System.out.println( geneList );
            //System.out.println( geneToCount );

            StringBuilder sb = new StringBuilder();
            for (String gene : geneList) {
                sb.append(gene);
            }
            String s = sb.toString();
            InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
            String result = Reconstruct.reconstruct(gen, k);
            checkResult(geneToCount, k, result);
        }
    }

    @Test
    public static void allSame(int k) {
        System.out.println("\tTesting reconstruct() all characters same k=" + k + "...");

        String sequence = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount);

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        StringBuilder sb = new StringBuilder();
        for (String gene : geneList) {
            sb.append(gene);
        }
        String s = sb.toString();
        InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());
        String result = Reconstruct.reconstruct(gen, k);
        checkResult(geneToCount, k, result);
    }

    @Test
    public static void alternatingChar(int n, int k, boolean checkResult) {
        System.out.println("\tTesting reconstruct() alternating characters, e.g. ACGU... n~" + n + ", k=" + k + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, n, (n < 100));

        StringBuilder sb = new StringBuilder();
        while( gen.hasNext() ) { // O(n)
            sb.append(gen.next());
        }
        String sequence = sb.toString(); // O(n)

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount); // O(nk)
        Random rand = new Random(0); // seed 0 for reproducibility
        Collections.shuffle(geneList, rand);

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        sb = new StringBuilder();
        for (String gene : geneList) { // O(nk) total
            sb.append(gene);
        }
        String s = sb.toString(); // O(nk)
        gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length()); // O(nk)
        String result = Reconstruct.reconstruct(gen, k); // Hopefully O(k^2(n-k))
        if( checkResult )
            checkResult(geneToCount, k, result); // O(nk)
        else
            System.out.println( "\t\tNote: not checking result" );
    }


    @Test
    public static void allSame(int n, int k, boolean checkResult) {
        System.out.println("\tTesting reconstruct() all same character, n~" + n + ", k=" + k + "...");
        char[] all_same = { bases[0] };
        InputGenerator<Character> gen = new AlternatingCharGenerator(all_same, n, (n < 100));

        StringBuilder sb = new StringBuilder();
        while( gen.hasNext() ) { // O(n)
            sb.append(gen.next());
        }
        String sequence = sb.toString(); // O(n)

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount); // O(nk)

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        sb = new StringBuilder();
        for (String gene : geneList) { // O(nk) total
            sb.append(gene);
        }
        String s = sb.toString(); // O(nk)
        gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length()); // O(nk)
        String result = Reconstruct.reconstruct(gen, k); // Hopefully O(k^2(n-k))
        if( checkResult )
            checkResult(geneToCount, k, result); // O(nk)
        else
            System.out.println( "\t\tNote: not checking result" );
    }


    @Test
    public static void random(int n, int k, boolean checkResult) {
        System.out.println("\tTesting reconstruct() performance, random characters, with k=" + k + ", n~" + n + "...");


        Random rand = new Random();
        long seed = rand.nextLong();
        //System.out.println( "Seed: " + seed );
        InputGenerator<Character> gen = new RandomCharGenerator(bases, k, (n<100));
        gen.setSeed(seed);
        StringBuilder sb = new StringBuilder();
        while( gen.hasNext() ) { // O(n)
            sb.append(gen.next());
        }
        String sequence = sb.toString(); // O(n)

        ArrayDeque<String> geneList = new ArrayDeque<>();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        populateListMap(sequence,k,geneList,geneToCount); // O(nk)

        //System.out.println( geneList );
        //System.out.println( geneToCount );

        sb = new StringBuilder();
        for (String gene : geneList) { // O(nk) total
            sb.append(gene);
        }
        String s = sb.toString(); // O(nk)
        gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length()); // O(nk)
        String result = Reconstruct.reconstruct(gen, k); // Hopefully O(k^2(n-k))
        if( checkResult )
            checkResult(geneToCount, k, result); // O(nk)
        else
            System.out.println( "\t\tNote: not checking result" );

    }

    public static boolean validate(String sequence, int k) {
        // From the input sequence, construct the list of genes that will be in the input
        // Then run the algorithm, and check that its String also produces the same gene list.
        ArrayDeque<String> geneList = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        HashMap<String,Integer> geneToCount = new HashMap<>();

        for( int i=0; i < sequence.length(); i++ ) {
            sb.append(sequence.charAt(i));
            if( sb.length() == k ) {
                String gene = sb.toString();
                geneList.add(gene); // add it to the input list of genes
                geneToCount.put( gene, geneToCount.getOrDefault(gene, 0)+1 ); // and store its count for testing later
                sb.deleteCharAt(0);
            }
        }

        Collections.shuffle(geneList); // Shuffle the list for good measure :-)

        System.out.println( "\nsequence: " + sequence);
        System.out.println( "input: " + geneList );

        sb = new StringBuilder();
        for( String gene : geneList ) {
            sb.append(gene);
        }

        String s = sb.toString();
        InputGenerator<Character> gen = new input.AlternatingCharGenerator(s.toCharArray(), s.length());

        String result = Reconstruct.reconstruct(gen, k);
        System.out.println( "result: " + result );

        // Now check whether result produces the right geneList/count
        sb = new StringBuilder();
        for( int i=0; i < result.length(); i++ ) {
            sb.append(result.charAt(i));
            if( sb.length() == k ) {
                // Check whether the current sb is in geneToCount
                String gene = sb.toString();
                if( geneToCount.containsKey(gene) ) {
                    geneToCount.put( gene, geneToCount.get(gene)-1 );
                    if( geneToCount.get(gene) == 0 ) {
                        geneToCount.remove(gene);
                    }
                } else {
                    System.out.println( "Error: " + gene + " not in geneToCount" );
                    return false;
                }
                sb.deleteCharAt(0);
            }
        }

        return true;
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

        int[] sizes = {12, 64, 64*8};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing reconstruct() correctness genes unique and in order...");
                for( int k=3; k < 37; k++ ) {
                    uniqueInOrder(k);
                }
                break;

            case 1:
                System.out.println( "Testing reconstruct() correctness genes unique but shuffled...");
                for( int k=3; k < 37; k++ ) {
                    uniqueShuffled(k);
                }
                break;

            case 2:
                System.out.println( "Testing reconstruct() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                System.out.println( "Testing reconstruct() correctness genes not unique but in order and output unique...");
                for( int k=3; k < 37; k++ ) {
                    alternatingInOrder(k);
                }
                break;
            case 4:
                System.out.println( "Testing reconstruct() correctness genes not unique, shuffled, but output unique...");
                for( int k=3; k < 37; k++ ) {
                    alternatingShuffled(k);
                }
                break;
            case 5:
                System.out.println( "Testing reconstruct() correctness genes not unique but in order, output not unique...");
                for( int k=3; k < 8; k++ ) {
                    notUniqueInOrder(k);
                }
                break;
            case 6:
                System.out.println( "Testing reconstruct() correctness genes not unique, shuffled, output not unique...");
                for( int k=3; k < 8; k++ ) {
                    notUniqueShuffled(k);
                }
                break;
            case 7:
                System.out.println( "Testing reconstruct() correctness all same character...");
                for( int k=3; k < 37; k++ ) {
                    allSame(k);
                }
                break;

            case 8:
                int size=5000;
                System.out.println( "Testing reconstruct() O(n^2) time performance n~" + size + ", k=O(1)...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                alternatingChar(12, 3, true);
                alternatingChar(15, 4, true);
                alternatingChar(100, 50, true );
                alternatingChar(1000, 500, true );
                alternatingChar(size, 10, false );
                allSame(size, 10, true );
                random(size, 10, true );
                break;
            case 9:
                size=10000;
                System.out.println( "Testing reconstruct() O(nk^2) time performance n~" + size + ", 50 < k < 200...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 50, true);
                random(size, 50, true );
                random(size, 100, true );
                for( int k=25; k < 150; k+=25 ) { // ~5 iterations
                    alternatingChar(size, k, false);
                }
                break;
            case 10:
                size=20000;
                System.out.println( "Testing reconstruct() O((n-k)k^2) time performance n~" + size + ", 100 < k < 200...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=100; k < 200; k+=20 ) { // ~5 iterations
                    alternatingChar(size, k, true);
                    allSame(size, k, true );
                    random(size, k, true );
                }
                break;
            case 11:
                System.out.println( "If you didn't already, run this test (11) with -Xmx8m");
                size=10000;
                System.out.println( "Testing reconstruct() O(n^2) space performance n~" + size + ", k~20...");
                allSame(size, 20, false);
                alternatingChar(size, 20, false);
                random(size, 20, false);
                break;
            case 12:
                System.out.println( "If you didn't already, run this test (12) with -Xmx16m");
                size=20000;
                System.out.println( "Testing reconstruct() O(k(n-k)) space performance n~" + size + ", k~20...");
                System.out.println( "\tThis test may take >2 seconds.");
                allSame(size, 50, false);
                alternatingChar(size, 50, false);
                random(size, 50, false);
                break;

            default:
                System.out.println("To run an individual test: java ReconstructTest [test number]");
                System.out.println("To run all tests: java ReconstructTest");
                System.out.println("These are LIMITED local tests; add more to test more thoroughly.");
                for( int i=0; i < 13; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
