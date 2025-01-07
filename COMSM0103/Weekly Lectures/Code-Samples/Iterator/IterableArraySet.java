import java.lang.Iterable;
import java.util.Iterator;

class IterableArraySet<X> 
        extends ArraySet<X>
        implements
        Iterable<X> {
  @Override
  public Iterator<X> iterator() {
    return new Iterator<X>() {
      private int index = 0;
      public X next () {
        X x = values[index];
        index = index + 1;
        return x;
      }
      public boolean hasNext() {
        return (index < size);
  } };}
}
