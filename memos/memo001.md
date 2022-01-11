# Collections.max()
```
public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)
//다음과 같이 간소화할 수 있다.
<T extends Comparable<? super T>> T max(Collection<? extends T> coll)
```
