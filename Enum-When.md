# Kotlin Study

# 선택 표현과 처리: enum과 when

## enum 클래스 정의

```kotlin
enum class Color{
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}
```

enum은 class 앞에 있을 때는 특별한 의미를 지니지만 다른 곳에선 이름에 사용할 수 있다.



### * enum : 열거형

클래스처럼 보이게 하는 상수

서로 관련 있는 상수들을 모아 심볼릭한 명칭의 집합으로 정의한 것. 



**enum 클래스 안에도 프로퍼티나 메소드를 정의할 수 있다.**

```kotlin
enum class Color{
    val r: Int, val g: Int, val b: Int //상수 프로퍼티 정의
} {
    RED(255, 0, 0), ORANGE(255, 165, 0), YELLOW(255, 255, 0) GREEN(0, 255, 0), //상수를 생성할 때 그에 대한 프로퍼티 값을 지정한다.
    BLUE(0, 0, 255), INDIGO(75, 0, 130), VIOLET(238, 130, 238); //반드시 세미콜론을 사용할 것
    
    fun rgb() = (r * 256 + g) * 256 + b
}


//메인 메소드
println(Color.BLUE.rgb())
> 255
```



## when으로 enum 클래스 다루기

자바에서 사용되는 switch문은 코틀린에서 when으로 대치된다.

**when은 if와 마찬가지로 값을 만들어내는 식이다.**



when을 사용해 enum값 찾기

```kotlin
fun getMnemonic(color: Color) = 
	when(color){	// 색이 특정 enum과 같을 때 대응하는 문자열을 반환
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }
//when은 분기의 끝에 break를 넣지 않아도 된다.
//switch에서 when 차이 : [case:는 ->으로], [default는 else로] 표현한다 

// 실행
println(getMnemonic(Color.BLUE))
> Battle
```



한 분기 안에서 여러 값을 사용하려면 값 사이를 콤마(,)로 분리한다.

```kotlin
fun getWarmth(color: Color) = when(color){
    Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
    Color.GREEN -> "neutal"
    Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
}

//실행
println(getWarmth(Color.ORANGE))
> warm
```



**상수 값을 임포트 하면 위 코드를 더 간단하게 만들 수 있다.**

```kotlin
import ch02.colors.Color	//다른 패키지에서 정의한 Color 클래스를 임포트
import ch02.colors.Color.*	//짧은 이름으로 사용하기 위해 enum 상수를 모두 임포트

fun getWarmth(color: Color) = when(color){
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}
```



## when과 임의의 객체를 함께 사용

**상수만 허용하는 switch와 달리 when은 임의의 객체를 허용한다.**

```kotlin
fun mix(cl: Color, c2: Color) = when (setOf(c1, c2)) {
    setOf(RED, YELLOW) -> ORANGE
    setOf(YELLOW, BLUE) -> GREEN
    setOf(BLUE, VIOLET) -> INDIGO
    else -> throw Exception("Dirty Color") //매치되는 분기 조건이 없으면 이 문장 실행
}

//실행
println(mix(BLUE, YELLOW))
> GREEN
```

위와 같은 코드를 구현하기 위해 집합 비교를 사용한다.

**집합 비교? ** 코틀린 표준 라이브러리에 있는 함수로 인자로 전달받은 여러 객체를 그 객체들을 포함하는 집합인 Set객체로 만드는 SetOf 함수. (원소의 순서는 중요하지 않음)



## 인자 없는 when 사용

인자가 없는 when 식을 사용하면 **불필요한 객체 생성을 막을 수 있다.**

```kotlin
fun mixOptimized(c1: Color, c2: Color) = when(){
    (c1 == RED && c2 == YELLOW) ||
    (c1 == YELLOW && c2 == RED) -> ORANGE
    
    (c1 == YELLOW && c2 == BLUE) ||
    (c1 == BLUE && c2 == YELLOW) -> GREEN
    
    (c1 == BLUE && c2 == VIOLET) ||
    (c1 == VIOLET && c2 == BLUE) -> INDIGO
    
    else -> throw Exception("Dirty color")
}

//실행
println(mixOptimized(BLUE, YELLOW))
> GREEN
```

추가 객체를 만들지는 않지만 가독성이 떨어진다.



## 스마트 캐스트: 타입 검사와 타입 캐스트를 조합

클래스가 구현하는 인터페이스를 지정하기 위해 콜론( : ) 뒤에 인터페이스 이름을 사용

```kotlin
interface Expr
class Num(val value: Int) : Expr //value라는 프로퍼티만 존재하는 단순한 클래스로 Expr 인터페이스 구현
class Sum(val left: Expr, val right: Expr) : Expr //Expr 타입의 객체라면 어떤 것이나 Sum 연산의 인자가 될 수 있다.
```

left나 right는 각각 Num이나 Sum일 수 있다.

(1 + 2) + 4라는 식은 **Sum(Sum(Num(1), Num(2) ), Num(4) )** 라는 구조의 객체가 생긴다.



```kotlin
if 연쇄를 사용해 식 계산

fun eval(e: Expr) : Int {
    if(e is Num){ 
        val n = e as Num
        return n.value
    }
    if(e is Sun){
        return eval (e.right) + eval(e.left) //변수 e에 대해 스마트 캐스트 사용
    }
    throw IllegalArgumentException("Unknown expression")
}

//실행
println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
> 7
```

*is를 사용해 변수 타입을 검사하는데, is 검사는 자바의 instanceof와 비슷하다. 하지만 자바에서는 어떤 변수의 타입을 instanceof로 확인한 다음

그 타입에 속한 멤버에 접근하기 위해 명시적으로 변수 타입을 캐스팅해야 한다. (접근을 여러 번 수행해야 하면 변수에 따로 캐스팅한 결과를 저장해야 함)



**스마트 캐스팅?  **>>  코틀린에서는 **어떤 변수를 is로 검사하고 나면 굳이 캐스팅하지 않아도 컴파일러가 캐스팅을 수행해준다. **

### 스마트 캐스트가 동작하기 위한 조건

* is로 타입을 검사한 다음 그 값이 바뀔 수 있는 경우
* 클래스의 프로퍼티에 사용할 경우 프로퍼티는 반드시 val이어야 하며 커스텀 접근자를 사용한 것이어도 안 된다.

원하는 타입으로 명시적으로 타입 캐스팅을 하려면 **as 키워드**를 사용한다.

```kotlin
val n == e as Num
```

