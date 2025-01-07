class ArraySet<X> implements Set<X> {
  protected X[] values;
  protected int size;
  private final int N = 100;

  public ArraySet() {
    values = (X[]) new Object[N];
    size = 0; }

  @Override
  public void insert(X x) {
    values[size] = x;
    size = size + 1; }

  @Override
  public void delete(X x) {
    assert(contains(x));
    for (int i=0; i < size; i = i+1) {
      if (values[i].equals(x)) {
        values[i] = values[size-1];
        size = size - 1;
        break;
  } } }

  @Override
  public boolean contains(X x) {
    boolean contains = false;
    for (X value : values) {
      if (value.equals(x)) {
        contains = true;
        break;
    } }
    return contains; }

  @Override
  public int size() {
    return size; }

  @Override
  public void empty() {
    size = 0;
} }
