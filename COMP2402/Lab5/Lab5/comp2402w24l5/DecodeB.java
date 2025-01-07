package comp2402w24l5;

// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/DecodeB.java
// java comp2402w24l5/DecodeB

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

public class DecodeB {

    public static int decodeB(InputGenerator<Integer> gen, int k) {
        // TODO(student): Your code goes here
        while( gen.hasNext() ) { // This loops through the entire input
            Integer c = gen.next();
        }
        return -1;
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing decodeB() via DecodeB.main...");
        System.out.println("You should also try testing via tests/DecodeBTest.java");

        InputGenerator<Integer> gen = new FileIntGenerator();

        // The following tests decodeB using the chars in the
        // file samples/decodeB-sample.txt, up to the first newline.
        gen = new FileIntGenerator( "samples/decodeA-sample.txt" );
        System.out.println( "Expect 6: " +  decodeB(gen, 3) );

        // If you want to test via the command-line, i.e. if you want to input a stream of
        // characters of your own devising and then run decodeB once you hit 'Enter', then
        // uncomment the next three lines.
        // gen = new FileIntGenerator();
        // System.out.println( "Enter a sequence of positive integers, then input -1 and hit Enter to run decodeB(gen, 3):" );
        // System.out.println( "Your result is: " + decodeB(gen, 3) );

        // The following tests use a generate that alternates between the characters listed in the
        // options array.  The second argument is the number of characters to generate.
        int[] options = {1, 2, 3, 4};
        gen = new AlternatingIntGenerator(options, 12); // Generates 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4
        System.out.println( "Expect 66 from output frequencies 11111111111: " + decodeB(gen, 4) );

        options = new int[]{1, 2, 1, 4};
        gen = new AlternatingIntGenerator(options, 10, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2
        System.out.println( "Expect 155 from output frequencies 0112233445: " +  decodeB(gen, 10) );
        gen = new AlternatingIntGenerator(options, 10, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2
        System.out.println( "Expect 87 from output frequencies 0112222222: " +  decodeB(gen, 4) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 79 from output frequencies 01121212121: " + decodeB(gen, 3) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 55 from output frequencies 01111111111: " + decodeB(gen, 2) );
        gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
        System.out.println( "Expect 55 from output frequencies 01111111111: " + decodeB(gen, 1) );

        options = new int[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4};
        gen = new AlternatingIntGenerator(options, 11, true); // 12233344441
        System.out.println( "Expect 157 from output frequencies 01122233334: " + decodeB(gen, 10) );
        gen = new AlternatingIntGenerator(options, 21, true); // 122333444412233344441
        System.out.println( "Expect 777 from output frequencies 011222333344444444444: " + decodeB(gen, 10) );
        gen = new AlternatingIntGenerator(options, 21, true); // 122333444412233344441
        System.out.println( "Expect 562 from output frequencies 011222332343222233234: " + decodeB(gen, 4) );

        gen = new FileIntGenerator();
        System.out.println( "Enter a sequence of positive integers, then input -1 and hit Enter to run decodeB(gen, 3):" );
        System.out.println( "Your result is: " + decodeB(gen, 10) );
    }

}
