package comp2402w24l5.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l5 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l5/tests/DecodeBTest.java
// java -cp "./lib/*:." comp2402w24l5/tests/DecodeBTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l5/tests/DecodeBTest.java
// java -cp "./lib/*;." comp2402w24l5/tests/DecodeBTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l5.DecodeB;
import input.InputGenerator;
import input.AlternatingIntGenerator;
import input.RandomIntGenerator;
import input.RASIntGenerator;

import java.util.Arrays;
import java.util.Random;


public class DecodeBTest {

    private static int[] bases = {1, 2, 3, 4};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting decodeB() all same character n=" + n + ", k=" + k + "...");
        int out = 0;
        int newK = Math.min(n, k);
        for( int i=0; i < newK; i++ ) {
            out += i*i;
        }
        for( int i=newK; i <n; i++ ) {
            out += (newK)*i; // There is a formula for this but this loop may help you understand the problem more
        }
        for( int i=0; i < bases.length; i++ ) {
            int[] all_same = { bases[i] };
            InputGenerator<Integer> gen = new AlternatingIntGenerator( all_same, n, (n<100));
            if( n > 0 )
              assertEquals(out, DecodeB.decodeB(gen, k), "All same character n>0 should return " + out + " " + gen);
            else 
              assertEquals(0, DecodeB.decodeB(gen, k), "All same character n=0 should return 0 " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting decodeB() alternating characters, e.g. 1,2,3,4,1,2,3,4... n~" + n + "...");
        InputGenerator<Integer> gen = new AlternatingIntGenerator(bases, n, (n < 100));


        assertEquals((n-1)*n/2, DecodeB.decodeB(gen, 4),
                "Alternating ints 1, 2, 3, 4, with k=4 decode to frequencies 111 1111 ... 1111 and return " +
                        (n-1)*n/2 + " " + gen );
        gen = new AlternatingIntGenerator(bases, n, (n < 100));

        assertEquals(n*(n-1)-4*5/2, DecodeB.decodeB(gen, 5),
                "Alternating characters 1, 2, 3, 4, with k=5 should decode to frequencies 1111 2222 ... 222 and return " +
                        (n*(n-1)-4*5/2) + " " + gen);


        int total = 0;
        for( int i=1; i < n/4+1; i++ ) { // 1111 2222 3333 4444 .... (n/4-1)(n/4-1)(n/4-1)
            for( int j=0; j < 4; j++ ) {
                total += i*(4*(i-1)+j+1);
                //System.out.println( "i: " + i + ", index: " + (4*(i-1)+j+1) );
            }
        }
        total -= (n/4)*n;
        gen = new AlternatingIntGenerator(bases, n, (n < 100));
        assertEquals(total, DecodeB.decodeB(gen, n),
                "Alternating characters 1, 2, 3, 4, with k=n should decode to frequencies 111 1222 ... 2333 and return " +
                        6+40*(n/4-1)*(n/4)/2+20*(n/4-1) + " " + gen);

        gen = new AlternatingIntGenerator(bases, n, (n < 100));
        assertEquals(total, DecodeB.decodeB(gen, n-1),
                "Alternating characters 1, 2, 3, 4, with k=n-1 should decode to frequencies 111 1222 ... 2333 and return " +
                        total + " " + gen);

        total = 17;
        for( int i=5; i < (n/5)*5; i++ ) {
            if( i % 5 == 0 || i % 5 == 2) {
                total += i;
            } else {
                total += 2*i;
            }
        }

        int[] diff_bases = {1, 2, 1, 3, 4}; // 121341213412134...
        gen = new AlternatingIntGenerator(diff_bases, (n/5)*5, (n < 100));
        assertEquals(total, DecodeB.decodeB(gen, 4),
                "Alternating characters 1, 2, 1, 3, 4 with k=4 should decode to frequencies 1122 12122 12122... and return " +
                        total + " "  + gen);

    }

    @Test
    public static void empty() {
        System.out.println("\tTesting decodeB() empty sequence...");
        InputGenerator<Integer> gen = new AlternatingIntGenerator(bases, 0, true);
        gen = new AlternatingIntGenerator(bases, 0, true);
        assertEquals(0, DecodeB.decodeB(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }


    @Test
    public static void alternatingChar(int n, int k) {
        n = (n/k)*k; // Assumes k < n otherwise sets n = 0
        System.out.println("\tTesting decodeB() alternating characters, e.g. 1, 2, 3, 4, ..., " + k + ", with k=" + k + ", n~" + n + "...");
        int[] a = new int[k];
        for( int i=0; i < k; i++ ) { // O(k)
            a[i] = i+1;
        }


        InputGenerator<Integer> gen = new AlternatingIntGenerator(a, (n/k)*k, (n < 100));
        long out = ((long)n)*(n-1)/2;
        assertEquals((int)out, DecodeB.decodeB(gen, k),
                "Alternating characters 1,2,3,...," + k + " with k=" + k + " should return " + (int)out + " " + gen);


        gen = new AlternatingIntGenerator(a, (n/k)*k, (n < 100));
        assertEquals((int)out, DecodeB.decodeB(gen, k-1),
                "Alternating characters 1,2,3,...," + k + " with k=" + (k-1) + " should return " + (int)out + " " + gen);


        gen = new AlternatingIntGenerator(a, (n/k)*k, (n < 100));

        if( k < n ) {
            out = ((long)(n))*(n-1)-((long)k)*(k+1)/2;
            assertEquals((int)(out), DecodeB.decodeB(gen, k + 1),
                    "" + n + " alternating characters 1,2,3,...," + k + " with k=" + (k + 1) + " should return " + (int)(out) + " " + gen);
        } else {
            out = ((long)(n))*(n-1)-((long)k)*(k+1)/2 + k;
            assertEquals((int)(out), DecodeB.decodeB(gen, k + 1),
                    "" + n + " alternating characters 1,2,3,...," + k + " with k=" + (k + 1) + " should return " + (int)(out) + " " + gen);
        }

    }

    @Test
    public static void increasingPattern(int n, int k) {
        n = (n / k) * k; // Assumes k < n otherwise sets n = 0
        System.out.println("\tTesting decodeB() increasing characters performance only, e.g. 1223334444... " + k + ", with k=" + k + ", n~" + n + "...");

        InputGenerator<Integer> gen = new RASIntGenerator(k, (n / k) * k, (n < 100));
        DecodeB.decodeB(gen, k);
    }

    @Test
    public static void random(int n, int k) {
        System.out.println("\tTesting decodeB() performance only, random characters, with k=" + k + ", n~" + n + "...");


        Random rand = new Random();
        long seed = rand.nextLong();
        //System.out.println( "Seed: " + seed );
        InputGenerator<Integer> gen = new RandomIntGenerator(n, k, (n<100));
        gen.setSeed(seed);
        DecodeB.decodeB(gen, k);

        gen = new RandomIntGenerator(n, k, (n<100));
        seed = rand.nextLong();
        //System.out.println( "Seed: " + seed );
        gen.setSeed(seed);
        DecodeB.decodeB(gen, 2*k);

        gen = new RandomIntGenerator(n, k, (n<100));
        seed = rand.nextLong();
        //System.out.println( "Seed: " + seed );
        gen.setSeed(seed);
        DecodeB.decodeB(gen, k/2);

        /*long out = ((long) n) * (n - 1) / 2;
        assertEquals((int) out, DecodeB.decodeB(gen, k),
                "Alternating characters 1,2,3,...," + k + " with k=" + k + " should return " + (int) out + " " + gen);

         */
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
                System.out.println( "Testing decodeB() correctness all same character");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]/2);
                }
                break;
            case 1:
                System.out.println( "Testing decodeB() correctness input alternating chars...");
                alternatingChar(8, 4);
                alternatingChar(10, 5);
                alternatingChar(15,5);
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing decodeB() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                int size=5000;
                System.out.println( "Testing decodeB() O(n^2) time performance n~" + size + ", k=O(1)...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 3);
                alternatingChar(size, 10);
                increasingPattern(size, 10);
                random(size, 10);
                break;
            case 4:
                size=10000;
                System.out.println( "Testing decodeB() O(nk^2) time performance n~" + size + ", 50 < k < 200...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 50);
                for( int k=25; k < 150; k+=25 ) { // ~5 iterations
                    alternatingChar(size, k);
                }
                increasingPattern(size, 50);
                random(size, 50);
                break;
            case 5:
                size=20000;
                System.out.println( "Testing decodeB() O(nk) time performance n~" + size + ", 100 < k < 200...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=100; k < 200; k+=20 ) { // ~5 iterations
                    alternatingChar(size, k);
                }
                increasingPattern(size, 140);
                random(size, 140);
                break;
            case 6:
                size=50000;
                System.out.println( "Testing decodeB() O(n log k) time performance n~" + size + ", k~O(n)...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=size/2; k < size; k+=(size/10) ) { // ~5 iterations
                    alternatingChar(size, k);
                    increasingPattern(size, k);
                    random(size, k);
                    allSame(size, k);
                }
                break;
            case 7:
                size=100000;
                System.out.println( "Testing decodeB() O(n log k) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int k=size/2; k < size; k+=(size/10) ) { // ~5 iterations
                    allSame(size, k);
                }
                break;
            case 8:
                System.out.println( "If you didn't already, run this test (8) with -Xmx8m");
                size=10000;
                System.out.println( "Testing decodeB() O(n) space performance n~" + size + "~k...");
                allSame(size, 20);
                alternatingChar(size, 20);
                increasingPattern(size, 20);
                random(size, 20);
                break;
            case 9:
                System.out.println( "If you didn't already, run this test (9) with -Xmx16m");
                size=1000000;
                System.out.println( "Testing decodeB() O(k) space performance n~" + size + ", k~20...");
                System.out.println( "\tThis test may take >2 seconds.");
                allSame(size, 20);
                alternatingChar(size, 20);
                increasingPattern(size, 20);
                random(size, 20);
                break;

            default:
                System.out.println("To run an individual test: java DecodeBTest [test number]");
                System.out.println("To run all tests: java DecodeBTest");
                System.out.println("These are LIMITED local tests; add more to test more thoroughly.");
                for( int i=0; i < 10; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
