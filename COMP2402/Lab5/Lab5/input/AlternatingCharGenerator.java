package input;

// From within the Lab directory (or wherever you put the input directory):
// javac input/AlternatingCharGenerator.java
// java input/AlternatingCharGenerator

// YOU SHOULD NOT NEED TO MODIFY THIS FILE
// Any modifications you make will be ignored by the autograder.

import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * This class is used to generate alternating character input.
 * You supply the characters it alternates through (in order) in the constructor.
 * It will be used for the various labs.
 */
public class AlternatingCharGenerator extends InputGenerator<Character> {
    private char[] options; // The characters we'll alternate through

    /**
     * Constructor that uses the characters a, b, c, d, e, f, g, h, i, j
     */
    public AlternatingCharGenerator() {
        this(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'}, 100);
    }

    /**
     * Constructor that takes an array of characters to alternate through.
     * and the number of characters to generate.
     * @param options
     * @param n
     */
    public AlternatingCharGenerator(char[] options, int n) {
        this(options, n, false);
    }

    /**
     * Constructor that takes an array of characters to alternate through,
     * the number of characters to generate, and a boolean indicating whether
     * to generate a string representation.
     * @param options
     * @param n
     * @param toString
     */
    public AlternatingCharGenerator(char[] options, int n, boolean toString) {
        super(n, toString);
        this.options = options;
    }

    /**
     * Returns the next character in the sequence.
     * @return
     * @throws NoSuchElementException
     */
    public Character nextGen() throws NoSuchElementException {
        return options[count % options.length];
    }


    // You can run this main method to try out the ALternatingCharGenerator
    public static void main(String[] args) {
        // This tests out the AlternatingCharGenerator.
        char[] options = {'A','U','C','G'};
        InputGenerator<Character> gen = new AlternatingCharGenerator(options, 100);
        while( gen.hasNext() ) {
            System.out.print(gen.next());
        }
        System.out.println();
        gen = new AlternatingCharGenerator(options, 25, true); // stores the generated characters in a string
        while( gen.hasNext() ) {
            gen.next();
        }
        System.out.println(gen); // prints out the generated characters. Useful for debugging purposes.
    }
}
