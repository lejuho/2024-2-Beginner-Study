# 서비스 계층
할 일을 뜻하는 Todo, 유저를 뜻하는 Member, 친구관계를 뜻하는 Friend를 만들고 이 엔티티들에 대한 레포지토리 계층을 작성했었다. 하지만 저장소에 접근해서 유저가 원하는 행위를 하려면 **서비스 계층**을 만들어 상호작용이 가능하게 사이를 이어줘야 한다. 예를 들면 저장소에 직접 접근해 엔티티를 생성하는 게 아니라 서비스 계층에서 엔티티 생성 메서드를 가져와 유저가 원하는 매개변수값을 집어넣어 생성을 중개하는 방식이다. 이것은 계층간 관심사를 분리하고 행위 묘사적인 클래스명/메서드명을 쓰게 됨에 따라 가이드라인이 되기도 한다. 객체지향과 마찬가지로 프로젝트의 유지보수와 확장성에 기여하는 것이다.
# 단위 테스트
저번에 했던 것처럼 영속성 컨텍스트에 직접 객체를 생성해 집어넣고 _assert_ 하는 방법은 주로 **통합 테스트**, 즉 프로젝트 전체의 흐름에서 정상 작동하는지를 판단할 때 쓴다. 그렇다면 작은 메서드나 코드를 시험하려면 어떻게 할까? 바로 **단위 테스트**를 사용한다. 단위 테스트를 도와주는 라이브러리로 **Mock**이 있다. 이 라이브러리는 의존성이나 객체 생성같은 테스트를 번거롭게 만드는 단계를 모두 모방형으로 이뤄 영향이 만들어지지 않게 한다. 
Mock의 메서드가 작동하는 형태는 BDD(Behavior Driven Development)를 따르는데, 이는 TDD(Test Driven Development)의 일종으로, TDD 중 잘 만들어진 걸 BDD라고 보면 된다.
> BDD의 흐름
> **GIVEN -> WHEN -> THEN**

사용자의 행위를 중점으로 개발하는 방법론으로, 어떤 상황이 주어질 때(Given) 테스트 대상이 어떻게 되면(When) 앞선 과정의 결과가 나온다(Then)는 흐름을 중점으로 생각하고 테스트 코드와 프로젝트 개발을 지속하는 방식이다.