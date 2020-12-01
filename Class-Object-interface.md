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



## 가시성 변경자: 기본적으로 공개

**가시성 변경자는 코드 기반에 있는 선언에 대한 클래스 외부 접근을 제어한다.**

(어떤 클래스의 구현에 대한 접근을 제한함으로써 그 클래스에 의존하는 외부 코드를 깨지 않고도 클래스 내부 구현을 변경할 수 있다.)



**코틀린의 가시성 변경자 : **public / protected / private

(기본 가시성은 아무 변경자도 없는 경우 public임)

패키지 전용(package-private)은 코틀린에 없다.



코틀린과 자바 가시성 규칙의 또 다른 차이 : 코틀린에서는 외부 클래스가 내부 클래스나 중첩된 클래스의 private 멤버에 접근할 수 없다.



### 패키지 전용 가시성에 대한 대안

코틀린에서는 대한으로 internal이라는 새로운 가시성 변경자를 도입했다.

**internal : **모듈 내부에서만 볼 수 있음 

(모듈 : 한 번에 한꺼번에 컴파일되는 코틀린 파일들 >> 인텔리J / 이클립스 / 그레이들 등의 프로젝트가 모듈이 될 수 있다.)



**모듈 내부 가시성은 모듈의 구현에 대해 진정한 캡슐화를 제공한다는 것이 장점이다.**



### 코틀린의 가시성 변경자

**변경자 / 클래스 멤버 / 최상위 선언**

public(기본 가시성) / 모든 곳에서 볼 수 있다. / 모든 곳에서 볼 수 있다.

internal / 같은 모듈 안에서만 볼 수 있다. / 같은 모듈 안에서만 볼 수 있다.

protected / 하위 클래스 안에서만 볼 수 있다. / (최상위 X)

private / 같은 클래스 안에서만 볼 수 있다. / 같은 파일 안에서만 볼 수 있다.     



## 내부 클래스와 중첩된 클래스 : 기본적으로 중첩 클래스

코틀린의 중첩 클래스는 명시적으로 요청하지 않는 이상 바깥 클래스 인스턴스에 대한 접근 권한이 없다.

```kotlin
//직렬화할 수 있는 상태가 있는 뷰 선언
interface State: Serializable
interface View{
    fun getCurrentState(): State
    fun restoreState(state: State){}
}
```



```kotlin
//자바에서 내부 클래스 이용하여 View 구현하기
public class Button implements View{
    @Override
    public State getCurrentState(){
        return new ButtonState();
    }
    
    @Override
    public void restoreState(State state) { ... }
    
    public class ButtonState implements State { ... }
}
```

자바에서는 다른 클래스 안에 정의한 클래스는 자동으로 내부 클래스가 된다.

ButtonState 클래스는 바깥쪽 Button 클래스에 대한 참조를 묵시적으로 포함하기 때문에 ButtonState를 직렬화할 수 없다.

**static 클래스로 선언하면 해결된다. (묵시적인 참조가 사라지기 때문)**



***코틀린 중첩 클래스에 아무런 변경자가 붙지 않으면 자바 static 중첩 클래스와 같다.**



**클래스 B 안에 정의된 클래스 A / 자바에서 / 코틀린에서**

중첩 클래스 / static class A / class A

내부 클래스 / class A / inner class A



### 코틀린에서 바깥쪽 클래스의 인스턴스를 가리키는 참조를 표기하는 방법

*this@Outer

```kotlin
class Outer{
    inner class Inner{
        fun getOuterReference(): Outer = this@Outer
    }
}
```



## 봉인된 클래스 : 클래스 계층 정의 시 계층 확장 제한

```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
fun eval(e: Expr): Int = 
	when(e){
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else->
        	throw IllegalArgumentException("Unknown expression")
    }
```

else 같은 디폴트 분기를 항상 추가하는 것은 불편하기도 하고, 버그가 발생할 수도 있다.

**sealed 클래스를 사용하면 해결할 수 있다.** (sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야함)

```kotlin
//sealed 클래스로 식 표현하기
sealed class Expr{
    class Num(val value: Int) : Expr() // 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다.
    class Sum(val left: Expr, val right: Expr) : Expr()
}
fun eval(e: Expr) : Int = 
	when(e){ // when 식이 모든 하위 클래스를 검사하므로 else 분기는 필요 없다.
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left)
    }
```

**sealed로 표시된 클래스는 자동으로 open이다.**

