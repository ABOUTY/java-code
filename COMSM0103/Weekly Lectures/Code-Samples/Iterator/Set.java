interface Set<X> {
  public void insert(X x);
  public void delete(X x);
  public void empty();
  public boolean contains(X x);
  public int size();
}
