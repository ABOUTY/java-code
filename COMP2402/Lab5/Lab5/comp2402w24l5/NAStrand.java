package comp2402w24l5;
// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/NAStrand.java
// java comp2402w24l5/NAStrand
// This is an abstract class, so you cannot compile and run it directly.
// You have to compile and run one of its subclasses, e.g. RNAStrand, DNAStrand.
// Thus you should implement those first before trying to run this one.


// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)


public abstract class NAStrand {



    /**
     * Number of bases in the strand. Do not remove this.
     */
    protected int n;

    /**
     * Returns whether or not the pair of characters x and y
     * is a valid base pair for the particular subclass.
     * @param x
     * @param y
     */
    public abstract boolean isPair(char x, char y);

    /**
     * Constructor
     * @param bases is an array of characters that are allowable
     *              for this strand
     */
    public NAStrand(char[] bases) {
        // TODO(student): Replace this with your code.
    }

    /**
     * Returns the allowable bases.
     */
    public char[] getBases() {
        // TODO(student): Replace this with your code.
        return new char[] {'x'};
    }


    /**
     * Returns whether or not character x is a valid base for this NAStrand
     *
     * @param x
     */
    public boolean isBase(char x) {
        // TODO(student): Replace this with your code.
        return false;
    }

    /**
     * Adds the base x to the end of the strand
     * Throws an IllegalArgumentException if the base is not valid.
     * @param x
     */
    public void add(char x) throws IllegalArgumentException {
        // TODO(student): Replace this with your code.
    }

    /**
     * Adds the base x to the strand at index i.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * Throws an IllegalArgumentException if the base is not valid.
     * @param i
     * @param x
     */
    public void add(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException{
        // TODO(student): Replace this with your code.
    }

    /**
     * Gets the base at position i.
     * Throw an IndexOutOfBoundsException exception if i is out of bounds.
     * @param i
     * @return
     */
    public char get(int i) throws IndexOutOfBoundsException {
        // TODO(student): Replace this with your code.
        return 'x';
    }

    /**
     * Returns the number of bases in the strand.
     */
    public int size() {
        // TODO(student): Replace this with your code.
        return -1;
    }

    /**
     * Clears the NAStrand so that it has no bases.
     */
    public void clear() {
        // TODO(student): Replace this with your code.
    }


    /**
     * Sets the base at position i to x.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * Throws an IllegalArgumentException if the base is not valid.
     * @param i
     * @param x
     * @return
     */
    public char set(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException {
        // TODO(student): Replace this with your code.
         return 'x';
    }



    /**
     * Removes the base at position i.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * @param i
     * @return
     */
    public char remove(int i) throws IndexOutOfBoundsException {
        // TODO(student): Replace this with your code.
        return 'x';
    }





    // These are a few examples of how to use main to run local tests
    // You should test more extensively than this.
    public static void main(String[] args) {
        NAStrand r = new RNAStrand();

        System.out.println(r);

        // isPair
        System.out.println(r.isPair('A', 'U'));
        System.out.println(r.isPair('A', 'T'));

        // getBases
        System.out.println(r.getBases());

        // isBase
        System.out.println(r.isBase('A'));
        System.out.println(r.isBase('T'));
        System.out.println(r.isBase('X'));

        // add
        r.add(0,'A');
        r.add('U');
        r.add('C');
        r.add(3, 'G');
        System.out.println(r);

        // get
        System.out.println(r.get(0));
        System.out.println(r.get(1));
        System.out.println(r.get(2));
        System.out.println(r.get(3));

        // size
        System.out.println(r.size());

        // clear
        r.clear();
        System.out.println(r);

        for( int i=0; i < 10; i++ ) {
            r.add(r.getBases()[i%4]);
        }
        System.out.println(r);

        // set
        System.out.println( r.set(0, 'U') );
        System.out.println( r.set(1, 'G') );

        // remove
        System.out.println( r.remove(0) );
        System.out.println( r.remove(1) );
        System.out.println(r);

    }
}
