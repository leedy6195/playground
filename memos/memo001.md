# Collections.max() & PECS
<br/>
> Remember PECS: "Producer Extends, Consumer Super".
<br/>
### `<T extends Comparable<? super T>> T max(Collection<? extends T> coll)`
<br/>
1. `extends` in `T extends Comparable<...>` 
  - Comparable의 구현 클래스여야 함
  - 와일드카드 아님. PECS 적용 불가. 
  
<br/><br/>
  
2. `super` in `Comparable<? super T>` 
  - Comparable 구현은 부모 클래스여도 노상관. 
  - 즉, max() 구현로직에는 T고유의 메소드는 필요없고 Comparable.compareTo() 만 있으면 된다.
  - `InterfaceName<? super T>`가 흔히 쓰이는 이유이며, `Consumer Super`로 요약 가능.
  
<br/><br/>  
  
3. `extends` in `max(Collection<? extends T> coll)`
  - 제네릭 내부에 쓰인 클래스의 상속관계를 보장한다. 
    - List<Number> numberList = intList.doSomething(...)
    - numberList.addAll(intList)
  - producer의 assignable한 반환값이 필요하므로 `Producer Extends`로 요약 가능.
  
<br/><br/>  
  
  ```
  //max()는 producer지만 제네릭을 까서 반환한다. T max(Collection<T> coll) 구조여도 다음이 가능
  Number num = Collections.max(intList)
  ```
  
