package comp2402w24l5;

// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/RNAStrand.java
// java comp2402w24l5/RNAStrand

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)

public class RNAStrand extends NAStrand {

    /**
     * Constructor
     */
    public RNAStrand() {
        //TODO(Student): Replace with your code
        super(null);
    }


    /**
     * Returns whether or not the base pair (x,y) is valid.
     * Valid pairs are 'A' and 'U', and 'C' and 'G'.
     *
     * @param x
     * @param y
     */
    @Override
    public boolean isPair(char x, char y) {
        // TODO(Student): Replace with your code.
        return false;
    }

    // These are a few examples of how to use main to run local tests
    // You should test more extensively than this.
    public static void main(String[] args) {
        RNAStrand r = new RNAStrand();
        System.out.println(r);
        System.out.println(r.isPair('A', 'U'));
        System.out.println(r.isPair('U', 'A'));
        System.out.println(r.isPair('C', 'G'));
        System.out.println(r.isPair('G', 'C'));
        System.out.println(r.isPair('A', 'C'));
        System.out.println(r.isPair('C', 'A'));
        System.out.println(r.isPair('A', 'G'));
        System.out.println(r.isPair('G', 'A'));
        System.out.println(r.isPair('U', 'C'));
        System.out.println(r.isPair('C', 'U'));
        System.out.println(r.isPair('U', 'G'));
        System.out.println(r.isPair('G', 'U'));
        System.out.println(r.isPair('A', 'A'));
        System.out.println(r.isPair('C', 'C'));
        System.out.println(r.isPair('G', 'G'));
        System.out.println(r.isPair('U', 'U'));
    }
}
