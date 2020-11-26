# Kotlin Study



# 코틀린의 예외 처리

오류가 발생하면 예외를 던질 수 있다.



기본 예외 처리 구문

```kotlin
if(percentage !in 0..100){
    throw IllegalArgumentException(
    "A percentage value must be between 0 and 100: $percentage")
}

// 코틀린의 throw는 식이므로 다른 식에 포함될 수 있다.
```



## try, catch, finally

```kotlin
//자바와 마찬가지로 try 사용

fun readNumber(reader: BufferedReader): Int?{ //함수가 던질 수 있는 예외를 명시할 필요 없음
    try{
        val line = reader.readLine()
        return Integer.parseInt(line)
    }
    catch (e: NumberFormatException){ //예외 타입을 :의 오른쪽에 씀
        return null
    }
    finally{ // 자바와 똑같이 작동
        reader.close()
    }
}
```

자바와 큰 차이는 throws 절이 코드에 없다는 것이다.

자바 : IOException이 체크 예외 이기 때문에 함수 선언 뒤에 throws IOException을 붙여야 한다

**코틀린에서는 함수가 던지는 예외를 지정하지 않고 발생한 예외를 잡아도 되고 안잡아도 된다.**



## try를 식으로 사용

```kotlin
fun readNumber(reader: BufferedReader){
    val number = try{
        Integet.parseInt(reader.readLine()) //이 식의 값이 try식의 값이 된다.
    } catch(e: NumberFormatException){
        return
    }
    println(number)
}

//실행
val reader = BufferedReader(StringReader("not a number"))
readNumber(reader) //아무것도 출력되지 않는다.
```



```kotlin
//catch에서 값 반환하기
fun readNumber(reader: BufferedReader){
    val number = try{
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException){
        null
    }
    println(number)
}

//실행
val reader = BufferedReader(StringReader("not a number"))
readNumber(reader)
>> null
```

NumberFormatException이 발생하므로 함수의 결과 값이 null이다.