import java.lang.Iterable;
import java.util.Iterator;

class IterableArraySetEasier<X>
        extends ArraySet<X>
        implements
        Iterable<X> {

    class MyIterator implements Iterator <X> {
        private int index = 0;
        public X next () {
            X x = values[index];
            index = index + 1;
            return x;
        }
        public boolean hasNext() {
            return (index < size);
        }
    }
    @Override
    public Iterator<X> iterator() {
        MyIterator myIterator = new MyIterator();
        return myIterator;
    }
}
