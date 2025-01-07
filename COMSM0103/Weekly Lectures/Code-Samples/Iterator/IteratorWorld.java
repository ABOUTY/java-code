import java.util.Iterator;

class IteratorWorld {

  public static void main (String[] args) {
    int sum = 0;
    IterableArraySet<Integer> set = new IterableArraySet<>();
    set.insert(1);
    set.insert(2);
    for (Integer i : set) {
      sum += i.intValue();
      System.out.println(i);
    }
    System.out.println("sum = "+sum);

    sum = 0;
    IterableArraySetEasier<Integer> setEasier = new IterableArraySetEasier<>();
    setEasier.insert(1);
    setEasier.insert(2);
    for (Integer i : setEasier) {
      sum += i.intValue();
      System.out.println(i);
    }
    System.out.println("sumEasier = "+sum);

    sum = 0;
    Iterator<Integer> iterator = setEasier.iterator();
    while (iterator.hasNext()) {
      Integer i = iterator.next();
      sum += i;
      System.out.println(i);
    }
    System.out.println("sumEasier = "+sum);

} }
