# Kotlin Study

# 대상을 이터레이션 : While과 for문

## 범위와 수열

코틀린의 for문은 초깃값, 증가값, 최종값을 사용한 루프를 대신하기 위해 범위(Range)를 사용한다.

**범위? - **두 값으로 이뤄진 구간,  두 값은 숫자 타입의 값이며 ( .. ) 연산자로 시작과 끝 값을 연결

***코틀린의 범위는 양 끝을 포함하는 구간(1..10 이라면 1과 10을 포함)**

**수열? - ** 어떤 범위에 속한 값을 일정한 순서로 이터레이션하는 경우

**이터레이션? - **특정 횟수만큼, 어떤 조건이 만족될 때까지 명령을 반복하는 것

```kotlin
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz"
    i % 5 == 0 -> "Buzz"
    else -> "$i"
}


for (i in 1..100){ // 1~100 범위의 정수에 이터레이션
    print(fizzBuzz(i))
    ...
	}
}

> 1 2 Fizz 4 Buzz Fizz 7...

//증가 값을 가지고 이터레이션 하기
for(i in 100 downTo 1 step 2){
    print(fizzBuzz(i))
    ...
	}
}

> Buzz 98 Fizz 94 FizzBuzz 88 ...
```

100 downTo 1은 역방향 수열을 만들고(역방향이므로 증가 값은 -1), step 2를 붙이면 증가 값이 절댓값 2(-2)로 바뀐다

끝 값을 포함하지 않는 범위를 이터레이션 할 경우 untill 함수 사용 (for(x in 0 untill size))



## 맵에 대한 이터레이션

```kotlin
//맵을 초기하하고 이터레이션하기


val binaryReps = TreeMap<Char, String>() //키에 대해 정렬하기 위해 TreeMap 사용
for(c in 'A'..'F'){ //A~F까지 이터레이션
    val binary = Integer.toBinaryString(c.toInt()) //아스키 코드 -> 2진 표현
    binaryReps[c] = binary
}
for((letter, binary) in binaryReps){ //맵에 대해 이터레이션, 맵의 키와 값을 두 변수에 대입
    println("$letter = $binary")
}
```



## in으로 컬렉션이나 범위의 원소 검사

in 연산자 : 어떤 값이 범위에 속하는가

!in 연산자 : 어떤 값이 범위에 속하지 않는가

```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

//실행
println(isLetter('q'))
> true
println(inNotDigit('X'))
> true
```

**c in 'a'..'z'  는  'a' <= c && c <= 'z' 와 같다**