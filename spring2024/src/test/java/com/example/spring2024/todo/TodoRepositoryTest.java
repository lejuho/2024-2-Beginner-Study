package com.example.spring2024.todo;

import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    @Rollback(false)
    void todoSaveTest(){
        // 트랜잭션의 시작
        Todo todo =new Todo(null,false,"todo content");
        todoRepository.save(todo);

        // 트랜잭션 종료 => 커밋
        // 에러 발생시 자동 롤백
        Assertions.assertThat(todo.getId()).isNotNull();
    }


    @Test
    @Transactional
    void todoFindOneByIdTest(){
        Todo todo = new Todo(null,false,"todo content");
        todoRepository.save(todo);

        todoRepository.flushAndClear();

        Todo findTodo = todoRepository.findById(todo.getId());

        Assertions.assertThat(findTodo.getId()).isEqualTo(todo.getId());
    }


    @Test
    @Transactional
    void todoFindAllTest(){
        Todo todo1 = new Todo(null,false,"todo content");
        Todo todo2 = new Todo(null,false,"todo content");
        Todo todo3 = new Todo(null,false,"todo content");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> todoList = todoRepository.findAll();

        Assertions.assertThat(todoList).hasSize(3);
    }

    @Test
    @Transactional
    void todoFindAllByMemberTest(){
        Member member1 = new Member("member1","1111");
        Member member2 = new Member("member2","2222");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Todo todo1 = new Todo(member1,false,"todo content");
        Todo todo2 = new Todo(member1,false,"todo content");
        Todo todo3 = new Todo(member2,false,"todo content");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> member1TodoList = todoRepository.findAllByMember(member1);
        List<Todo> member2TodoList = todoRepository.findAllByMember(member2);

        Assertions.assertThat(member1TodoList).hasSize(2);
        Assertions.assertThat(member2TodoList).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void todoUpdateTest(){
        Todo todo1 = new Todo(null,false,"todo content");
        todoRepository.save(todo1);

        todoRepository.flushAndClear();

        Todo findTodo1 = todoRepository.findById(todo1.getId());
        findTodo1.updateContent("new content");
    }

    @Test
    @Transactional
    @Rollback(false)
    void todoDeleteTest(){
        Todo todo1 = new Todo(null,false,"todo content1");
        Todo todo2 = new Todo(null,false,"todo content2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        todoRepository.flushAndClear();

        todoRepository.deleteById(todo1.getId());
    }
    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while(true){

        }
    }
}
