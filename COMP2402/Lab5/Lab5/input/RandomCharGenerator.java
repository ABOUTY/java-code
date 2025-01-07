package input;

import java.util.Random;
import java.util.NoSuchElementException;
import java.io.IOException;

public class RandomCharGenerator extends InputGenerator<Character> {
    private char[] options;

    public RandomCharGenerator() {
        this(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
    }
    public RandomCharGenerator(char[] options) {
        this(options, 10);
    }

    public RandomCharGenerator(char[] options, int n) {
        this(options, n, false);
    }

    public RandomCharGenerator(char[] options, int n, boolean toFile) {
        super(n, toFile);
        this.options = options;
    }


    // Generate a random element from options
    public Character nextGen() throws NoSuchElementException {
        return pickRandom(options);
    }

    private char pickRandom( char[] input ) {
        return input[rand.nextInt(input.length)];
    }


    public static void main(String[] args) {
        char[] options = {'A','C','G','U'};
        InputGenerator<Character> gen = new RandomCharGenerator(options, 100);
        while( gen.hasNext() ) {
            System.out.print(gen.next());
        }
        System.out.println();
    }
}
