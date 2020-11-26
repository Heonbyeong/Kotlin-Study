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



