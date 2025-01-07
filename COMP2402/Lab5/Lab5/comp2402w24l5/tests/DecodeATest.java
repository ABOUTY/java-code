package comp2402w24l5.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l5 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l5/tests/DecodeATest.java
// java -cp "./lib/*:." comp2402w24l5/tests/DecodeATest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l5/tests/DecodeATest.java
// java -cp "./lib/*;." comp2402w24l5/tests/DecodeATest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l5.DecodeA;
import input.InputGenerator;
import input.AlternatingIntGenerator;


public class DecodeATest {

    private static int[] bases = {1, 2, 3, 4};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting decodeA() all same character n=" + n + ", k=" + k + "...");
        for( int i=0; i < bases.length; i++ ) {
            int[] all_same = { bases[i] };
            InputGenerator<Integer> gen = new AlternatingIntGenerator( all_same, n, (n<100));
            if( n > 0 )
              assertEquals(bases[i]*n/2*(n-1), DecodeA.decodeA(gen, k), "All same character n>0 should return " + bases[i]*n/2*(n-1) + " " + gen);
            else 
              assertEquals(0, DecodeA.decodeA(gen, k), "All same character n=0 should return 0 " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting decodeA() alternating characters, e.g. 1,2,3,4,1,2,3,4... n~" + n + "...");
        InputGenerator<Integer> gen = new AlternatingIntGenerator(bases, n, (n < 100));


        assertEquals(6+40*(n/4-1)*(n/4)/2+20*(n/4-1), DecodeA.decodeA(gen, 4),
                "Alternating ints 1, 2, 3, 4, with k=4 decode to 111 1234 1234 ... 1234 and return " +
                        6+40*(n/4-1)*(n/4)/2+20*(n/4-1) + " " + gen );
        gen = new AlternatingIntGenerator(bases, n, (n < 100));

        assertEquals(6+40*(n/4-1)*(n/4)/2+20*(n/4-1), DecodeA.decodeA(gen, 5),
                "Alternating characters 1, 2, 3, 4, with k=5 should decode to 111 1234 1234 ... 1234 and return " +
                        6+40*(n/4-1)*(n/4)/2+20*(n/4-1) + " " + gen);

        gen = new AlternatingIntGenerator(bases, n, (n < 100));
        assertEquals(6+40*(n/4-1)*(n/4)/2+20*(n/4-1), DecodeA.decodeA(gen, n),
                "Alternating characters 1, 2, 3, 4, with k=n should decode to 111 1234 1234 ... 1234 and return " +
                        6+40*(n/4-1)*(n/4)/2+20*(n/4-1) + " " + gen);
        gen = new AlternatingIntGenerator(bases, n, (n < 100));
        assertEquals(6+40*(n/4-1)*(n/4)/2+20*(n/4-1), DecodeA.decodeA(gen, n-1),
                "Alternating characters 1, 2, 3, 4, with k=n should decode to 111 1234 1234 ... 1234 and return " +
                        6+40*(n/4-1)*(n/4)/2+20*(n/4-1) + " " + gen);


        int[] diff_bases = {1, 2, 1, 3, 4};
        gen = new AlternatingIntGenerator(diff_bases, (n/5)*5, (n < 100));
        assertEquals(17 + 14*5*(n/5-1)*(n/5)/2 + 33*(n/5-1), DecodeA.decodeA(gen, 4),
                "Alternating characters 1, 2, 1, 3, 4 with k=4 should decode to 1122 23344 2344 ... and return " +
                        (17 + 14*5*(n/5-1)*(n/5)/2 + 33*(n/5-1)) + " "  + gen);


        diff_bases = new int[]{1, 1, 1, 1, 2};
        gen = new AlternatingIntGenerator(diff_bases, (n/5)*5, (n < 100));
        assertEquals(10 + 5*(n/5-1)*(n/5)/2 + 2*4*5*(n/5-1)*(n/5)/2 + 2*10*(n/5-1), DecodeA.decodeA(gen, 3),
                    "Alternating characters 1, 1, 1, 1, 2 with k=10 should decode to 1111 12222 12222 ... 12222 and return " +
                            (10 + 5*(n/5-1)*(n/5)/2 + 2*4*5*(n/5-1)*(n/5)/2 + 2*10*(n/5-1)) + " "  + gen);

    }

    @Test
    public static void empty() {
        System.out.println("\tTesting decodeA() empty sequence...");
        InputGenerator<Integer> gen = new AlternatingIntGenerator(bases, 0, true);
        gen = new AlternatingIntGenerator(bases, 0, true);
        assertEquals(0, DecodeA.decodeA(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }


    @Test
    public static void alternatingChar(int n, int k) {
        n = (n/k)*k;
        System.out.println("\tTesting decodeA() alternating characters, e.g. 1, 2, 3, 4, ..., " + k + ", with k=" + k + ", n~" + n + "...");
        int[] a = new int[k];
        for( int i=0; i < k; i++ ) { // O(k)
            a[i] = i+1;
        }

        int total = 0;
        for( int i=1; i < n; i++ ) { // O(n)
            if( i <= k-1 ) {
                total += i;
            } else {
                total += (i * (i % k+1));
            }
        }

        InputGenerator<Integer> gen = new AlternatingIntGenerator(a, (n/k)*k, (n < 100));
        assertEquals(total, DecodeA.decodeA(gen, k),
                "Alternating characters 1,2,3,...," + k + " with k=" + k + " should return " + total + " " + gen);

        total = 0;
        for( int i=1; i < n; i++ ) { // O(n)
            if( i <= k-2 ) {
                total += i;
            } else {
                if( i % k == k-1) {
                    total += (i * 1);
                } else {
                    total += (i * (i % k + 2));
                }
            }
        }
        gen = new AlternatingIntGenerator(a, (n/k)*k, (n < 100));
        assertEquals(total, DecodeA.decodeA(gen, k-1),
                "Alternating characters 1,2,3,...," + k + " with k=" + (k-1) + " should return " + total + " " + gen);

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
                System.out.println( "Testing decodeA() correctness all same character");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]+2);
                }
                break;
            case 1:
                System.out.println( "Testing decodeA() correctness input alternating chars...");
                alternatingChar(8, 4);
                alternatingChar(10, 5);
                alternatingChar(15,5);
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing decodeA() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                int size=5000;
                System.out.println( "Testing decodeA() O(n^2) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 3);
                alternatingChar(size);
                break;
            case 4:
                size=100000;
                System.out.println( "Testing decodeA() O(nk^2) time performance n~" + size + ", k < 200...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 100);
                //alternatingChar(size, 100);
                for( int k=100; k < 200; k+=20 ) { // ~5 iterations
                    alternatingChar(size, k);
                }
                break;
            case 5:
                size=13000;
                System.out.println( "Testing decodeA() O(nk) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=size/2; k < size; k+=(size/20) ) { // ~10 iterations
                    alternatingChar(size, k);
                }
                break;
            case 6:
                size=100000;
                System.out.println( "Testing decodeA() O(n log k) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=size/2; k < size; k+=(size/10) ) { // ~5 iterations
                    alternatingChar(size, k);
                }
                break;
            case 7:
                size=1000000;
                System.out.println( "Testing decodeA() O(n log d) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=size/2; k < size; k+=(size/10) ) { // ~5 iterations
                    allSame(size, k);
                }
                break;
            case 8:
                System.out.println( "If you didn't already, run this test (8) with -Xmx8m");
                size=10000;
                System.out.println( "Testing decodeA() O(n) space performance n~" + size + "~k...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            case 9:
                System.out.println( "If you didn't already, run this test (9) with -Xmx16m");
                size=1000000;
                System.out.println( "Testing decodeA() O(k) space performance n~" + size + ", k~20...");
                System.out.println( "\tThis test may take >2 seconds.");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            case 10:
                System.out.println( "If you didn't already, run this test (9) with -Xmx8m");
                size=1000000;
                System.out.println( "Testing decodeA() O(d) space performance n~" + size + "~k, d~O(1)...");
                System.out.println( "\tThis test may take >2 seconds.");
                allSame(size,  size/2);
                break;

            default:
                System.out.println("To run an individual test: java DecodeATest [test number]");
                System.out.println("To run all tests: java DecodeATest");
                System.out.println("These are LIMITED local tests; add more to test more thoroughly.");
                for( int i=0; i < 11; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
