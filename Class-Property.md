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

자바에서 필드와 접근자를 한데 묶어 프로퍼티라고 부른다.

**코틀린은 프로퍼티를 기본 제공하며 자바의 필드와 접근자 메소드를 완전히 대신한다.**

(프로퍼티 선언은 val(읽기 전용) / var(변경 가능))

```kotlin
class Person{
    val name: String //읽기 전용, (비공개)필드와 (비공개)게터 생성
    var isMarried: Boolean //변경 가능, (비공개)필드, (공개)게터, (공개)세터 생성
}
```





