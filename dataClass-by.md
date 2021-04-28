# Kotlin Study



## 컴파일러가 생성한 메소드: 데이터 클래스와 클래스 위임

자바 플랫폼에서는 클래스가 equals, hashCode, toString 등의 메소드를 구현해야한다.

(코틀린은 이런 메소드를 기계적으로 생성하는 작업을 보이지 않는 곳에서 해줌)



## 모든 클래스가 정의해야 하는 메소드



### 문자열 표현: toString()

```kotlin
class Client(val name: String, val postalCode: Int){
    override fun toString() = "Client(name = $name), postalCode = $postalCode"
}

val client1 = Clien("오현석", 4122)
println(client1)

>> Client(name=오현석, postalCode=4122)
```



### 객체의 동등성: equals()

```kotlin
val client1 = Client("오현석", 4122)
val client2 = Client("오현석", 4122)
println(client1 == client2)
>> false
```

위 코드에서 두 객체는 동일하지 않다. 코틀린에서 == 연산자는 참조 동일성이 아닌 객체 동등성을 검사한다.

따라서 == 연산은 equals를 호출하는 식으로 컴파일된다. (참조 비교를 위해서는 === 연산자 사용)



```kotlin
class Client(val name: String, val postalCode: Int){
    override fun equals(other: Any?): Boolean{
        if(other == null || other !is Client)
        	return false
        return name == other.name &&
        	postalCode == other.postalCode
    }
    override fun toString() = "Client(name=$name, postalCode=$postalCode)"
}
```

코틀린의 is 검사는 자바의 instanceof와 같다.



**흔히 면접에서 질문하는 내용이 "Client가 제대로 작동하지 않는 경우를 말하고 문제가 무엇인지 설명하시오" 인데, hashCode 정의를 빠뜨려서 그렇다는 사람이 많을 것이다. 그럼 왜 hashCode가 중요할까?**



### 해시 컨테이너: hashCode()

자바는 equals을 오버라이드할 때 반드시 hashCode도 함께 오버라이드 해야한다.

(hashCode()는 두 객체가 같은 객체인지 확인하는 메소드)



원소가 '오현석'이라는 고객 하나뿐인 집합을 만들고, 원래의 '오현석'과 똑같은 프로퍼티를 포함하는 새로운 Client 인스턴스를 만들어서 그 인스턴스가 집합 안에 있는지 검사한다.

```kotlin
val processed = hashSetOf(Client("오현석", 4122))
println(processed.contains(Client("오현석", 4122)))

//프로퍼티가 모두 일치하므로 true가 반횐될거라고 예상가능
>> false
```

위와 같은 이유가 나오는 이유는 Client클래스가 hashCode 메소드를 정의하지 않았기 때문이다.

JVM언어에선 hashCode가 지켜야하는 **"equals()가 true를 반환하는 두 객체는 반드시 같은 hashCode()를 반환해야 한다."**라는 제약이 있는데, Client클래스는 이를 어기고 있다.

```kotlin
class Client(val name: String, val potalCode: Int){
    ...
    override fun hashCode() : Int = name.hashCode() * 31 + postalCode
}
//이 클래스는 예상대로 작동한다.
```



## 데이터 클래스: 모든 클래스가 정의해야 하는 메소드 자동 생성

data 라는 변경자를 클래스 앞에 붙이면 필요한 메소드를 컴파일러가 자동으로 만들어준다. (데이터 클래스)

```kotlin
data class Client(val name: String, val postalCode: Int)
```



### 데이터 클래스와 불변성: copy() 메소드

데이터 클래스의 프로퍼티는 꼭 val일 필요는 없지만 **모든 프로퍼티를 읽기 전용으로 만들어서 데이터 클래스를 불변 클래스**로 만들라고 권장한다.

데이터 클래스 인스턴스를 불변 객체로 더 쉽게 활용할 수 있게 한 가지 메소드를 제공한다.

(객체를 복사하면서 일부 프로퍼티를 바꿀 수 있게 해주는 **copy**메소드)

```kotlin
class Client(val name: String, val postalCode: Int){
    ...
    fun copy(name: String = this.name, postalCode: Int = this.postalCode)
    	= Client(name, postalCode)
}

val lee = Client("이계영", 4122)
println(lee.copy(postalCode = 4000))
>> Client(name=이계영, postalCode=4000)
```



## 클래스 위임: by 키워드 사용

시스템을 취약하게 만드는 문제는 보통 구현 상속에 의해 발생하는데, 이런 문제를 해결하기 위해

기본적으로 클래스를 final로 취급하기로 한다.

(모든 클래스가 final이라면 open 변경자로 열어둔 클래스만 확장가능)



**상속의 대안 위임**

* 특정 처리를 다른 객체에게 넘기는 것을 의미
* 다른 객체는 클래스 내부(포함)에 가지고 있음
* 코틀린 에서는 **by**키워드로 클래스 위임을 만들 수 있음



종종 상속을 허용하지 않는 클래스에 새로운 동작을 추가해야 할 때가 있는데 이 때 **데코레이터 패턴**을 사용한다.

(상속을 허용하지 않는 클래스 대신 사용 가능한 새로운 클래스를 만들되 기존 클래스와 같은 인터페이스를 데코레이터가 제공하게 만들고, 기존 클래스를 데코레이터 내부에 필드로 유지하는 것)

새로 정의해야 하는 기능 -> 데코레이터의 메소드에 새로 정의 

기존 기능이 그대로 필요 -> 데코레이터의 메소드가 기존 클래스의 메소드에게 요청을 전달

```kotlin
//이런 방법은 준비 코드가 상당히 많이 필요,
//아무 동작도 변경하지 않는 데코레이터를 만들 때 작성하는 코드
class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int get() = innerList.size
    override fun isEmpty() : Boolean = innerList.isEmpty()
    override fun contains(element: T) : Boolean = innerList.contains(elemnet)
    override fun iterator() : Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>) : Boolean = innerList.containsAll(elements)
}
```



코틀린은 인터페이스 구현시 by 키워드를 통해 그 인터페이스에 대한 구현을 다른 객체에 위임 중이라는 사실을 명시할 수 있다.

```kotlin
//위의 코드를 위임을 사용하여 재작성한 코드
class DelegatingCollection<T>{
    innerList: Collection<T> = ArrayList<T>()
} : Collection<T> by innerList{}

//컴파일러가 전달 메소드를 자동으로 생성함
```



```kotlin
//클래스 위임을 사용한 코드
class CountingSet<T>(
	val innerSet: MutableCollention<T> = HashSet<T>()
) : MutableCollection<T> by innerSet { //MutableCollection의 구현을 innerSet에 위임
    val objectsAdded = 0
    override fun add(element: T) : Boolean { // (1)
        objectAdded++
        return innerSet.add(element)
    }
    override fun addAll(c : Collection<T>): Boolean { // (2)
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}
//(1),(2) 메소드는 위임하지 않고 새로운 구현을 제공함

val cast = CountingSet<Int>()
cast.addAll(listOf(1,1,2))
println("${cset.objectsAdded) objects were added, ${cset.size) remain")
>> 3 objects were added, 2 remain

//add, addAll을 오버라이드해서 카운터를 증가, MutableCollection 인터페이스의 나머지 메소드는 내부 컨테이너에게 위임함
```