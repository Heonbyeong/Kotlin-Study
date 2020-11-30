# Kotlin Study

# 코틀린에서 컬렉션 만들기

```kotlin
//숫자로 이뤄진 집합
val set = hashSetOf(1, 7, 53)

val list = arrayListOf(1, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```

to가 언어가 제공하는 특별한 키워드가 아니라 일반 함수다.



```kotlin
//위에서 만든 객체가 어떤 클래스에 속하는지 확인하는 코드
println(set.javaClass)
> class java.util.HashSet

println(list.javaClass)
> class java.util.ArrayList

println(map.javaClass)
class java.util.HashMap
```

코틀린은 자신만의 컬렉션 기능을 제공하지 않는다. (자바 개발자가 기존 자바 컬렉션을 활용 가능)

**컬렉션을 제공하지 않는 이유? - **표준 자바 컬렉션을 활용하면 자바 코드와 상호작용하기가 더 쉽다.



# 함수를 호출하기 쉽게 만들기

자바 컬렉션에는 디폴트 toString 구현이 들어있다. (하지만 toString 출력 형식은 고정돼 있다.)

```kotlin
val list = listOf(1,2,3)
println(list)
> {1, 2, 3}
```



***디폴트 구현과 달리 (1; 2; 3)처럼 원소 사이를 세미콜론으로 구분하고 괄호로 리스트를 둘러싸고 싶을때는 표준 라이브러리에 들어있는 함수를 쓰면 된다.**

```kotlin
fun<T>joinToString(
collection: Collection<T>, 
separator: String,
prefix: String,
postfix: String
): String{
    val result = StringBuilder(prefix)
    for((index, element) int collection.withIndex()){
        if(index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}
```

위 코드는 제네릭한 함수이다. (어떤 타입의 값을 원소로 하는 컬렉션이든 처리할 수 있다.)

```kotlin
val list = listOf(1, 2, 3)
println(joinToString(list, "; ","(", ")"))
> (1; 2; 3)
```



## 이름 붙인 인자

```kotlin
joinToString(collection, " ", " ", ".")
```

인자로 전달한 문자열이 어떤 역할을 하는지 알기 어렵다.



```kotlin
//위 코드의 가독성을 해결한 코드
joinTOString(collection, separator = " ", prefix = " ", postfix = ".")
```

코틀린으로 작성한 함수를 호출할 때는 함수에 전달하는 인자 중 일부(전체)의 이름을 명시할 수 있다.



## 디폴트 파라미터 값

자바에서는 일부 클래스에서 오버로딩한 메소드가 너무 많아지고, 어떤 함수가 불릴지 모호한 문제가 있다.

코틀린에서는 함수 선언에서 **파라미터의 디폴트 값을** 지정할 수 있으므로 위와 같은 문제를 피할 수 있다.

```kotlin
fun<T>joinToString{
    collection: Collection<T>,
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
    //디폴트 값이 지정된 파라미터
}: String
//함수를 호출할 때 모든 인자를 쓸 수도, 일부를 생략할 수도 있다.

//실행
joinToString(list, ", ", "", "")
> 1, 2, 3
joinToString(list)
> 1, 2, 3
joinToString(list, "; ")
> 1; 2; 3
```



이름 붙은 인자를 사용하는 경우, **지정하고 싶은 인자를 이름을 붙여 순서와 관계없이 지정**할 수 있다.

```kotlin
joinToString(list, postfix = ";", prefix = "# ")
> # 1, 2, 3;
```



## 정적인 유틸리티 클래스 없애기:  최상위 함수와 프로퍼티

다양한 정적 메소드를 모아두는 역할만 담당하고, 특별한 상태나 인스턴스 메소드는 없는 클래스가 생겨날 수 있다.

코틀린에선 이런 무의미한 클래스가 필요 없다. >> 함수를 직접 소스 파일의 최상위 수준, 모든 클래스의 밖에 위치시키면 된다.

```kotlin
//joinToString() 함수를 최상위 함수로
package strings
fun joinToString(...): String { ... }
```

위 함수가 실행될 수 있는 이유는 컴파일할 때 새로운 클래스를 JVM이 정의해주기 때문이다.

```kotlin
//위 코드를 컴파일 한 결과와 같은 클래스

package strings;
public class JoinKt{
    public static String joinToString(...) { ... }
}

//호출
import strings.joinKt;
...
Joinkt.joinToString(list, ", ", "", "");
```



**최상위 프로퍼티**

프로퍼티도 파일의 최상위에 놓을 수 있다.

```kotlin
val opCount = 0

fun performOperation(){
    opCount++
    //...
}
fun re4portOperationCount(){
    println("Operation performed $opCount times") //최상위 프로퍼티의 값을 읽는다.
}

//최상위 프로퍼티를 활용해 상수를 추가할 수 있다.
val UNIX_LINE_SEPARATOR = "\n"
```



***최상위 프로퍼티도 접근자 메소드를 통해 노출되는데, 겉으론 상수처럼 보이지만 실제로 게터를 사용해야 한다면 자연스럽지 못하다. 더 자연스럽게 사용하기 위해 public static final 필드로 컴파일해야 한다.** << const 변경자를 추가하면 된다.

```kotlin
const val UNIX_LINE_SEPARATOR = "\n"
```



## 메소드를 다른 클래스에 추가:  확장 함수와 확장 프로퍼티



