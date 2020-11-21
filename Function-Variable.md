# Kotlin Study

## 함수와 변수





**함수를 선언할 때 fun 키워드 사용 **(꼭 클래스 안에 함수를 넣을 필요가 없음)

**파라미터 이름 뒤에 그 파라미터의 타입을 씀** (변수 선언도 마찬가지)

```kotlin
fun main(args: Array<String){
    println("Hello World!")
}
```



배열 처리를 위한 문법이 따로 존재하지 않는다

세미콜론을 붙이지 않아도 된다



## 함수

함수 선언은 fun키워드로 시작, 다음에는 함수 이름, 함수 이름 뒤에는 괄호 안에 파라미터 목록, 반환 타입은 닫는 괄호 뒤에 온다

```kotlin
fun max(a: Int, b: Int) : Int{
  //이름     파라미터     반환타입
  return if(a > b) a else 
  // <-----함수 본문----->
}
```

***코틀린 if는 값을 만들어내지 못하는 문장이 아니고 결과를 만드는 식이다**

### 문(statement)과 식(expression)의 구분

식 : 값을 만들어 내며 다른 식의 하위 요소로 계산에 참여 가능

문 : 자신의 둘러싸고 있는 가장 안쪽 블록의 최상위 요소로 아무런 값을 만들어내지 않음

**대입문은 자바에서 식이었지만 코틀린에서는 문이 됐다**



### 식이 본문인 함수

```kotlin
fun max(a: Int, b: Int) Int = if(a > b) a else b

반환 타입 생략 >> fun max(a: Int, b: Int) = if(a > b) a else b

// 타입 추론(컴파일러가 타입을 분석해 프로그래머 대신 프로그램 구성 요소의 타입을 정해주는 기능) 기능 덕에 생략(식이 본문인 함수 일때만) 가능
```



본문이 중괄호로 둘러싸인 함수를 **블록이 본문인 함수**, 등호와 식으로 이뤄진함수를 **식이 본문인 함수(코틀린에서 자주 쓰임)** 라고 한다



## 변수

키워드로 변수 선언을 시작하는 대신 이름 뒤에 타입 몇시 및 생략을 허용한다

```kotlin
//타입 표기 생략
val question = "오늘 저녁 뭐먹지?"
val answer = 20

//명시
val answer: Int = 20

//초기화 식을 사용하지 않는다면 반드시 변수 타입 명시할 것
val answer: Int
answer = 20
```



### 변경 가능한 변수와 불가능한 변수

* val  - 변경 불가능한 변수로 final과 같음
* var - 변경 가능한 변수로 일반 변수와 같음

**기본적으로 모든 변수를 val으로 선언하고 나중에 꼭 필요할 때만 var로 변경할 것.  (함수형 코드에 가까워지기 위함)**

val 변수는 블록을 실행할 때 정확히 한 번만 초기화돼야 하는데, 어떤 블록이 실행될 때 오직 한 초기화 문장만 실행된다면 다른 여러 값으로 초기화가 가능

``` kotlin
val message: String
if(canPerformOperation()){
    message = "Success"
    //... 연산 수행
}
else{
    message = "Failed"
}
```



val 참조 자체는 불변이지만 그 참조가 가리키는 객체의 내부 값은 변경될 수 있다.

```kotlin
val languages = arrayListOf("Java") // 불변 참조 선언
languages.add("Kotlin") // 참조가 가리키는 객체 내부 변경
```



var 키워드를 사용하면 변수의 값을 변경할 수 있지만 타입은 고정된다.

```kotlin
var answer = 42
answer = "no answer" // 오류발생
```



## 문자열 템플릿

```kotlin
fun main(args: Array<String>){
    val name = if(args.size > 0) args[0] else "Kotlin"
    println("Hello, $name!") // "Bob"을 인자로 넘기면 "Hello, Bob!" 을, 아무 인자도 없으면 "Hello, World!" 출력 
    
    //복잡한 식도 중괄호로 둘러싸서 사용할 수 있다
    println("Hello, ${args[0]}!")
}
//자바의 문자열 접합 연산 기능과 동일하다
println("Hello, " + name + "!");


```

변수를 문자열 안에 사용할 수 있다. 문자열 리터럴의 필요한 곳에 변수를 넣고 변수 앞에 $를 추가해야 한다.



# 참고

<Kotlin in Action - 함수와 변수> 