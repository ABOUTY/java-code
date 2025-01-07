package comp2402w24l5;

// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/Reconstruct.java
// java comp2402w24l5/Reconstruct

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the countA method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.AlternatingCharGenerator;
import input.FileCharGenerator;

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
import ods.AdjacencyLists;
import ods.Graph;


import java.util.List;
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

public class Reconstruct {

    public static String reconstruct(InputGenerator<Character> gen, int k) {
        // TODO(student): Your code goes here
        while( gen.hasNext() ) { // This loops through the entire input
            Character c = gen.next();
        }
        return "bogus";
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
        InputGenerator<Character> gen = new AlternatingCharGenerator(s.toCharArray(), s.length());

        String result = reconstruct(gen, k);
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
        return geneToCount.isEmpty();
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing reconstruct() via Reconstruct.main...");
        System.out.println("You should also try testing via tests/ReconstructTest.java");


        InputGenerator<Character> gen = new FileCharGenerator();

        // The following tests reconstruct using the chars in the
        // file samples/decodeB-sample.txt, up to the first newline.
        gen = new FileCharGenerator( "samples/reconstruct-sample.txt" );
        System.out.println( "Expect UACCACUACG: " +  reconstruct(gen, 3) );

        // The validate method takes in a sequence, parses it into a list of genes
        // runs your algorithm on that list, then checks whether you found something
        // that is either the original sequence or something with the same gene list!
        validate( "AUCGAUCGAUCG", 3 );
        validate( "AAAAC", 3 );
        validate( "AAAAC", 4 );
        validate( "AAAAC", 5 );
        validate( "AUCGAAAUAACUGUGUACUGAGUACG", 3 ); // AUAAACGAGUACUGUACUGUGAAUCG is okay as output
        validate("AAAA", 3);
    }

}
