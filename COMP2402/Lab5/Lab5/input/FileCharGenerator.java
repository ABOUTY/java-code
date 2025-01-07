package input;

// From within the Lab directory (or wherever you put the input directory):
// javac input/FileCharGenerator.java
// java input/FileCharGenerator

// YOU SHOULD NOT NEED TO MODIFY THIS FILE
// Any modifications you make will be ignored by the autograder.

import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;


/**
 * This class is used to generate character input from a file.
 * It will be used for the various labs.
 *
 */
public class FileCharGenerator extends InputGenerator<Character> {

    private BufferedReader r; // The reader we'll use to read from the file/stdin
    private int next;         // used to help determine if there is another char to read

    /**
     * Constructor that reads from standard input.
     */
    public FileCharGenerator() {
        super(-1, false); // we won't need n or toString for this generator.
        try {
            r = new BufferedReader(new InputStreamReader(System.in));
            next = -2;
        } catch( Exception e ) {
            System.err.println("Error opening input file");
            System.exit(-1);
        }
    }

    /**
     * Constructor that takes a filename as input.
     * @param filename
     */
    public FileCharGenerator(String filename) {
        super(-1, false);
        next = -2;
        try {
            r = new BufferedReader(new FileReader(filename));
        } catch( Exception e ) {
            System.err.println("Error opening input file");
            System.exit(-1);
        }
    }

    /**
     * Returns the next character from r.
     * @throws NoSuchElementException
     */
    public Character nextGen() throws NoSuchElementException {
        int curr = next;
        try {
            next = r.read();
        } catch( IOException e ) {
            System.err.println("Error reading from input file");
            System.exit(-1);
        }
        return (char)curr;
    }

    /**
     * Returns true if there is another character to read.
     * (A newline indicates the end of the stream of characters.)
     */
    public boolean hasNext() {
        if( next == -2 ) {
            try {
                next = r.read();
            } catch( IOException e ) {
                System.err.println("Error reading from input file");
                System.exit(-1);
            }
        }
        return (next != -1) && (next != (int)('\n'));
    }


    // You can run this main method to try out the FileCharGenerator
    // If you pass in an argument, it will be used as the filename.
    // Otherwise, it will read from standard input until you hit "enter".
    public static void main(String[] args) {
        InputGenerator<Character> gen;
        if (args.length == 1) {
            gen = new FileCharGenerator(args[0]); // Read from the file specified by the first argument
        } else {
            gen = new FileCharGenerator(); // Read from standard input
        }
        // Generate the next character (whether from file or standard in)
        // and print it out until there are no more characters to generate
        // (i.e. until the first newline.)
        while( gen.hasNext() ) {
            System.out.print(gen.next());
        }
        System.out.println();
    }
}
