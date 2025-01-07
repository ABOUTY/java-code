package comp2402w24l5;

// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/DecodeA.java
// java comp2402w24l5/DecodeA

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the countA method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileIntGenerator;
import input.AlternatingIntGenerator;

import ods.ArrayDeque;
import ods.ArrayQueue;
import ods.ArrayStack;
import ods.RootishArrayStack;
import ods.SLList;
import ods.DLList;
import ods.SEList;
import ods.SkiplistList;
import ods.SkiplistSSet;
import ods.Treap;
import ods.ScapegoatTree;
import ods.SSet;
import ods.RedBlackTree;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;



public class DecodeA {

    public static int decodeA(InputGenerator<Integer> gen, int k) {
        // TODO(student): Your code goes here
        while( gen.hasNext() ) { // This loops through the entire input
            Integer c = gen.next();
        }
        return -1;
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing decodeA() via DecodeA.main...");
        System.out.println("You should also try testing via tests/DecodeATest.java");

        InputGenerator<Integer> gen = new FileIntGenerator();

        // The following tests decodeA using the ints in the
        // file samples/decodeA-sample.txt, up to the first -1.
        // gen = new FileIntGenerator( "samples/decodeA-sample.txt" );
        // System.out.println( "Expect 6: " + decodeA(gen, 3) ); // Expect 6

        // If you want to test via the command-line, i.e. if you want to input a stream of
        // integers of your own devising and then run decodeA once you input -1 and 'Enter', then
        // uncomment the next three lines.
        // gen = new FileIntGenerator();
        // System.out.println( "Enter a sequence of positive integers, then input -1 and Enter to run decodeA(gen, 3):" );
        // System.out.println( "Your result is: " + decodeA(gen, 3) );

        // The following tests use a generate that alternates between the characters listed in the
        // options array.  The second argument is the number of characters to generate.
        int[] options = {1, 2, 3, 4};
        gen = new AlternatingIntGenerator(options, 12); // Generates 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4
        System.out.println( "Expect 166 from output sequence 11112341234: " + decodeA(gen, 4) );

        options = new int[]{1, 2, 1, 4};
        gen = new AlternatingIntGenerator(options, 10, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2
        System.out.println( "Expect 113 from output sequence 112224422: " +  decodeA(gen, 4) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 153 from output sequence 1122442244: " + decodeA(gen, 3) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 107 from output sequence 1122442244: " + decodeA(gen, 2) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 109 from output sequence 1214121412: " + decodeA(gen, 1) );

    }

}
