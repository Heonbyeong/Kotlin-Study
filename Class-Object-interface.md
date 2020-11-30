# Kotlin Study

# 클래스 / 객체 / 인터페이스



# 클래스 계층 정의

## 코틀린 인터페이스

```kotlin
//코틀린 인터페이스 선언
interface Clickable{
    fun click()
}
```

위 코드는 click이라는 추상 메소드가 있는 인터페이스를 정의한다. (이 인터페이스를 구현하는 모든 비추상 클래스는 click에 대한 구현을 제공해야 함)



```kotlin
// Clickable 인터페이스 구현하기

class Button : Clickable {
    override fun click() = println("I was clicked")
}

//실향
Button().click()
> I was click
```

클래스 이름 뒤에 콜론( : )을 붙이고 인터페이스와 클래스 이름을 적는 것으로 클래스 확장, 인터페이스 구현을 모두 처리한다.



자바의 @Override 애노테이션과 비슷한 **override 변경자는 프로퍼티나 메소드를 오버라이드할 때 꼭 사용**해야 한다.



```kotlin
//인터페이스에 본문이 있는 메소드 정의하기

interface Clickable{
    fun click() // 일반 메소드 선언
    fun showOff() = println("I'm clickable") // 디폴트 구현이 있는 메소드
}
```

이 인터페이스를 구현하는 클래스는 click에 대한 구현을 제공해야 하지만, showOff 메소드의 경우 새로운 동적을 정의할 수도 있고, 정의를 생략 후 디폴트 구현을 사용할 수도 있다.



```kotlin
// 동일한 메소드를 구현하는 다른 인터페이스 정의
interface Focusable{
    fun setFocus(b: Boolean) = 
    	println("I &(if (b) "got" else "lost") focus.")
    
    fun showOff() = println("I'm focusable")
}
```

한 클래스에서 위의 두 인터페이스를 함께 구현하면 오류가 생긴다.

**클래스가 구현하는 두 상위 인터페이스에 정의된 showOff 구현을 대체할 오버라이딩 메소드를 직접 제공하지 않기 때문이다.**



```kotlin
//상속한 인터페이스의 메소드 구현 호출하기

class Button: Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff(){
        super<Clickable>.showOff() // 상위 타입의 이름을 <> 사이에 넣어 super를 지정하면 어떤 상위 타입의 멤버 메소드를 호출할지 지정할 수 있다.
        super<Focusable>.showOff()
    }
}
```

```kotlin
//showOff() 검증

fun main(args: Array<String){
    val button = Button()
    button.showOff() // I'm clickable / I'm focusable 출력
    button.setFocus(true) // I got focus 출력
    button.click() // I was clicked 출력
}
```



## open, final, abstract 변경자: 기본적으로 final

final로 상속할 경우 편리한 경우도 많지만 문제가 생기는 경우도 많다.

**취약한 기반 클래스**라는 문제는 하위 클래스가 기반 클래스에 대해 가졌던 가정이 기반 클래스를 변경함으로써 깨져버린 경우 생긴다.

(어떤 클래스가 자신을 상속하는 방법을 제공하지 않는다면 작성자의 의도와 다른 방식으로 메소드를 오버라이드 할 위험이 있다.)



이를 해결하려면 final로 만드는 것인데, 코틀린의 클래스와 메소드는 기본적으로 final 이고, **어떤 클래스의 상속을 허용하려면 클래스 앞에 open 변경자**를 붙여야한다.



```kotlin
open class RichButton : Clickable { //이 클래스는 열려있기 때문에 다른 클래스가 상속할 수 있다.
    fun disable() {} //final 메소드, 하위 클래스가 이 메소드를 오버라이드 할 수 없다.
    open fun animate() {} //이 메소드는 열려있기 때문에 하위 클래스에서 이 메소드를 오버라이드해도 된다.
    override fun click() {} //이 메소드는 열려있는 메소드를 오버라이드한다. (오버라이드 한 메소드는 기본적으로 열려있다.)
}
```



```kotlin
//오버라이드 금지하기
open class RichButton : Clickablke {
    final override fun click() {} //final 없는 override메소드나 프로퍼티는 기본적으로 열려있다.
}
```



코틀린에서도 추상 클래스를 선언할 수 있다. (인스턴스화 불가) 

추상 멤버는 항상 열려있기 떄문에 open 변경자를 명시할 필요가 없다.

```kotlin
abstract class Animated { //추상클래스이므로 인스턴스를 만들 수 없다.
    abstract fun animate() //추상 함수이므로 구현이 없고, 하위 클래스에서 반드시 오버라이드 해야한다.
   	open fun stopAnimating() {} //추상 클래스에 속해도 비추상 함수는 파이널이다. (open으로 오버라이드를 허용할 수 있다.)
    fun animateTwice() {} //stopAnimating과 동일
}
```



***인터페이스의 멤버의 경우 final, open, abstract를 사용하지 않는다. (항상 열려있기 때문에 final로 변경할 수 없다.)**



**final /  오버라이드할 수 없음  /  클래스 멤버의 기본 변경자다.**

**open / 오버라이드할 수 있음 / 반드시 open을 명시해야 오버라이드할 수 있다.**

**abstract / 반드시 오버라이드해야 함 / 추상 클래스의 멤버에만 이 변경자를 붙일 수 있다. (추상 멤버에는 구현이 있으면 안된다.)**

**override / 상위 클래스나 상위 인스턴스의 멤버를 오버라이드하는 중 / 오버라이드하는 멤버는 기본적으로 열려있고, 하위 클래스의 오버라이드를 금지하려면 final 명시해야한다.**