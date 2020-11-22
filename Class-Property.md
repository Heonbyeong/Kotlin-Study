# Kotlin Study

## 클래스와 프로퍼티

자바 Person 클래스 >> 코틀린으로 변환

```java
public class Person{
    private final String name;
    
    public Person(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}

코틀린으로 변환한 Person 클래스
class Person (val name: String)
```



## 프로퍼티

자바에서 필드와 접근자(게터,세터)를 한데 묶어 프로퍼티라고 부른다.

**코틀린은 프로퍼티를 기본 제공하며 자바의 필드와 접근자 메소드를 완전히 대신한다.**

(프로퍼티 선언은 val(읽기 전용) / var(변경 가능))

```kotlin
class Person{
    val name: String //읽기 전용, (비공개)필드와 (비공개)게터 생성
    var isMarried: Boolean //변경 가능, (비공개)필드, (공개)게터, (공개)세터 생성
}
```



**자바에서 Person 클래스를 사용하는 방법**

```java
Person person = new Person("Bob", true);
System.out.println(person.gerName());
System.out.println(person.isMarried());

//실행 결과
> Bob
> true
```



**코틀린에서 Person 클래스를 사용하는 방법**

```kotlin
val person = Person("Bob", true) // new 키워드를 사용하지 않고 생성자 호출

//프로퍼티 이름을 직접 사용해도 자동으로 게터 호출
println(person.name)
println(person.isMarried)

//실행 결과
> Bob
> true
```



대부분의 프로퍼티에는 그 프로퍼티의 값을 저장하기 위한 필드가 있다. **(뒷받침하는 필드)**



## 커스텀 접근자

프로퍼티의 접근자를 직접 작성할 수 있다.

```kotlin
class Rectangle(val height: Int, val width: Int){
    val isSquare: Boolean
    	get(){ //프로퍼티 게터 선언
            return height == width
        }
}

//main 메소드
val rectangle = Rectangle(41, 43)
println(rectangle.isSquare)

//실행 결과
> false
```



## 디렉터리와 패키지

클래스 임포트와 함수 임포트에 차이가 없고, 모든 선언을 import로 가져올 수 있다.

```kotlin
package geometry.example
import geometry.shapes.createRandonRectangle //이름으로 함수 임포트하기        import geometry.shapes.*를 사용해도 문제 없음
fun main(args: Array<String>){
    println(createRandomRectangle().isSquare)
}
```



패키지 이름 뒤에 .*을 추가하면 패키지 안의 모든 선언을 임포트할 수 있다. (**최상위의 정의된 함수, 프로퍼티까지 모두 불러오니 유의**)

코틀린에서는 디스크상의 어느 티렉터리에 소스코드 파일을 위치시키든 관계없다.



# 참고

<Kotlin in Action - 클래스와 프로퍼티>