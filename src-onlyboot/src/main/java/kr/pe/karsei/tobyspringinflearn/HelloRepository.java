package kr.pe.karsei.tobyspringinflearn;

public interface HelloRepository {
    Hello findHello(String name);

    // 일반적으로 서비스 Bean 이나, 도메인 오브젝트에 넣는게 맞음
    void increaseCount(String name);
    
    // Java 8 에서 추가됨. 잘 사용하면 좋긴 함. Comparator 보면 좋음
    default int countOf(String name) {
        Hello hello = findHello(name);
        return hello == null ? 0 : hello.getCount();
    }
}
