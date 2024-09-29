![alt text](image.png)

# 스프링 빈과 스프링 컨테이너의 개념
우선 컨테이너를 보자. 스프링 컨테이너의 또다른 이름은 **ApplicationContext**인데, 여기서 컨테이너의 개념이 무엇인지 추측하는 것이 가능하다. 컨텍스트는 맥락을 뜻한다. 그렇다면 객체와 맥락이 무슨 상관이냐인데, 이 맥락의 역할을 하는 객체가 바로 컨테이너인 것이다. 컨테이너에 등록되지 않은 빈(객체)는 의존성 주입을 할 수도 당할 수도 없으니 완전히 개발에서 배제된다. 즉 현재 프로젝트에서 자신의 역할이 주어지고 런타임동안 일을 하게 되는 객체를 위한 공간이 바로 컨테이너라고 난 알아들었다. 이런 컨테이너라는 좋은 기능을 두고 계속 객체를 필요에 따라 다시 생성하면 리소스 낭비가 심해지니 아예 몇몇 객체를 맥락에 올려놓고 필요할 때마다 지들끼리 엮고 풀고 서로 이용하고 적재적소에 활용할 수 있게 독립시킨 것이 바로 빈이다. 처음엔 연결되어 있지 않으니 다른 빈과 협업할 수 없지만, 의존성을 형성하면 다른 기능도 사용할 수 있게 된다. 새로운 객체가 곧 기능의 확장 또는 프로젝트의 확장이 되며 동시에 많은 자원을 빼앗지도 않는 방법이다.
# 스프링 빈을 컨테이너에 등록하는 법
이러한 빈을 컨테이너에 등록하려면 두 가지 방법이 있는데, 컨테이너에게 어떤 빈을 쓰라고 직접 **업무지시서같이 설정 파일**을 주는 방법과, 컨테이너를 감옥의 등대처럼 만들어놓고 프로젝트 안에서 새로운 빈이 생길때마다 주민등록번호마냥 **컴포넌트화시켜 스캔시 보일 수 있게 추가**하는 방법이 있다. 설정 파일은 물론 잘 정리하면 편하겠지만 컴포넌트는 자동 등록이라는 엄청난 이점이 있기에 컴포넌트가 훨씬 편리한 것 같다.
# 의존성 주입 개념과 스프링에서 의존성을 주입하는 법
의존성을 자세히 말하자면, 트랜스포머라고 할 수 있다. 트랜스포머1에선 옵티머스 프라임이 날지 못했지만 2에서 마지막에 제트엔진을 동료로부터 얻으며 날수 있게 되었다. 여기서 옵티머스와 제트엔진이 각각 빈인 셈이다. 옵티머스는 제트엔진에 "의존"해야 날 수 있다. 날아다니는 기능은 옵티머스에게 없고 제트엔진에만 있기 때문이다. 반대로 제트엔진은 스스로 움직이는 기능이 없다. 그래서 제트엔진에게 의식이 있다면(비유) 움직이기 위해 옵티머스에게 의존해야 할 것이다. 의존성은 무조건 쌍방으로 이루어져야 하는 것도 아니고 단방향으로 이루어져야 하는 것도 아니다. 적재적소에 적절한 방식을 써야 한다. 내 개인적인 생각으로는 의존성 주입 개념은 상속과 인터페이스의 혼종인 것 같다. 서로 동등한 레벨에서 원한다면 기능을 가져올 수 있기 때문이다. 아무튼 의존성을 주입한다는 것은 어떤 이미 있는 빈에 새로 생성한 빈의 기능을 넣어줄 수 있다는 걸 뜻하고 그 반대도 가능하다. 빈은 모두 객체이기 때문이다.
이러한 의존성을 스프링에서 주입하려면 세가지 방법이 있다.
**생성자 / 필드 / 세터(메서드)**
이 중 세터는 거의 안 쓰니 있다고 알아만 놓으면 된다. 생성자는
```java
@Controller
public class PetController{

    private final PetService petService;

	@Autowired
    public PetController(PetService petService){
    	this.petService = petService;
    }
}
```
이런 식으로 생성자에 의존성을 주입할 객체를 전달하는 것이다.
만약 **생성자가 1개이고 주입받을 객체가 빈에 등록되어있다면**
```java
@Controller
@RequiredArgsConstructor
public class PetController{
    private final PetService petService;
}
```
이런 식으로 어노테이션을 활용해 더 간단히 쓸 수도 있다.

두번째 방식인 필드는
```java
@Controller
public class PetController{
	@Autowired
    private PetService petService;
}
```
이런 식으로 생성된 객체에 필드로 의존성을 전달하는 방식이다. 첫번째 방식과 차이는 **final 키워드가 사용불가**하다는 점이다.