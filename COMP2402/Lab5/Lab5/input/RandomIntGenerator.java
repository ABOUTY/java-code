package input;

import java.util.Random;
import java.util.NoSuchElementException;
import java.io.IOException;

public class RandomIntGenerator extends InputGenerator<Integer> {
    private int k;

    public RandomIntGenerator() {
        this(10);
    }
    public RandomIntGenerator(int k) {
        this(k, 10);
    }

    public RandomIntGenerator(int k, int n) {
        this(k, n, false);
    }

    public RandomIntGenerator(int k, int n, boolean toFile) {
        super(n, toFile);
        this.k = k;
    }


    // Generate a random element from options
    public Integer nextGen() throws NoSuchElementException {
        return rand.nextInt(k)+1;
    }



    public static void main(String[] args) {
        InputGenerator<Integer> gen = new RandomIntGenerator(4, 100);
        while( gen.hasNext() ) {
            System.out.print(gen.next());
        }
        System.out.println();
    }
}
