# Kotlin Study

## 선택 표현과 처리: enum과 when

**when은 자바의 switch를 대치한다.**



## enum 클래스 정의

```kotlin
enum class Color{
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}
```

enum은 class 앞에 있을 때는 특별한 의미를 지니지만 다른 곳에선 이름에 사용할 수 있다.



**enum 클래스 안에도 프로퍼티나 메소드를 정의할 수 있다.**

```kotlin
enum class Color{
    val r: Int, val g: Int, val b: Int //상수 프로퍼티 정의
} {
    RED(255, 0, 0), ORANGE(255, 165, 0), YELLOW(255, 255, 0) GREEN(0, 255, 0), //상수를 행성할 때 그에 대한 프로퍼티 값을 지정한다.
    BLUE(0, 0, 255), INDIGO(75, 0, 130), VIOLET(238, 130, 238); //반드시 세미콜론을 사용할 것
    
    fun rgb() = (r * 256 + g) * 256 + b
}


//메인 메소드
println(Color.BLUE.rgb())
> 255
```

