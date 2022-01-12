# Collections.max()

<br/>

## `<T extends Comparable<? super T>> T max(Collection<? extends T> coll)`

- ### `T extends Comparable<...>` 
  - T는 Comparable의 구현 클래스여야 함
  
  
- ### `Comparable<? super T>` 
  - Comparable 구현은 부모 클래스에서 일어나도 노상관. 
  - 즉, max() 구현로직에는 T고유의 메소드는 필요없고 Comparable.compareTo() 만 있으면 된다.
  - Comparable<T>보다 확장적 개념(반공변성)
  
  
- ### `max(Collection<? extends T> coll)`
  - 제네릭 내부에 쓰인 클래스의 상속관계를 보장한다. 
    - 반환 타입 상속: ex) `List<Number> numberList = intList.doSomething(...)`
    - 파라미터타입 상속: ex) `numberList.addAll(intList)`
  - 일반적으로 producer함수의 파라미터 형식으로 많이 쓰인다.
  
