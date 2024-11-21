package com.example.spring2024.todo;

import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import com.example.spring2024.todo.dto.TodoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

public class TodoServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

//    @Test
//    public void createTodoTest() throws Exception{
//        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
//        todoService.createTodo("content",1L);
//        Mockito.verify(todoRepository,Mockito.times(1)).save(Mockito.any(Todo.class));
//    }

    @Test
    public void createTodoTest_When_MemberDoesNotExist() throws Exception{
        BDDMockito.given(memberRepository.findById(Mockito.anyLong())).willReturn(null);
        assertTrue(Assertions.assertThrows(Exception.class,
                ()->todoService.createTodo("content",1L))
                .getMessage()
                .contains("존재하지 않는 멤버입니다."));
    }

//    @Test
//    public void getTodoListTest() throws Exception {
//        // given
//        Member member = new Member(); // 임의의 Member 객체 생성
//        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
//        BDDMockito.given(todoRepository.findAllByMember(member)).willReturn(List.of(new Todo(), new Todo()));
//
//        // when
//        List<TodoResponse> todoList = todoService.getTodoList(1L);
//
//        // then
//        assertEquals(2, todoList.size());
//        Mockito.verify(todoRepository, Mockito.times(1)).findAllByMember(member);
//    }

    @Test
    public void getTodoListTest_When_MemberDoesNotExist() {
        // given
        BDDMockito.given(memberRepository.findById(Mockito.anyLong())).willReturn(null);

        // when & then
        Exception exception = Assertions.assertThrows(Exception.class, () -> todoService.getTodoList(1L));
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

//    @Test
//    public void updateTodoTest() throws Exception {
//        // given
//        Member member = new Member();
//        Todo todo = new Todo();
//        todo.setUser(member); // todo 객체에 user 설정
//
//        BDDMockito.given(todoRepository.findById(1L)).willReturn(todo);
//        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
//
//        // when
//        todoService.updateTodo(1L, 1L, "updated content");
//
//        // then
//        Mockito.verify(todoRepository, Mockito.times(1)).findById(1L);
//        assertEquals("updated content", todo.getContent());
//    }

//    @Test
//    public void updateTodoTest_When_TodoDoesNotExist() {
//        // given
//        BDDMockito.given(todoRepository.findById(Mockito.anyLong())).willReturn(null);
//
//        // when & then
//        Exception exception = Assertions.assertThrows(Exception.class, () -> todoService.updateTodo(1L, 1L, "content"));
//        assertTrue(exception.getMessage().contains("존재하지 않는 할 일 입니다."));
//    }

//    @Test
//    public void updateTodoTest_When_MemberDoesNotExist() {
//        // given
//        Todo todo = new Todo();
//        BDDMockito.given(todoRepository.findById(Mockito.anyLong())).willReturn(todo);
//        BDDMockito.given(memberRepository.findById(Mockito.anyLong())).willReturn(null);
//
//        // when & then
//        Exception exception = Assertions.assertThrows(Exception.class, () -> todoService.updateTodo(1L, 1L, "content"));
//        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
//    }

//    @Test
//    public void updateTodoTest_When_TodoMemberMismatch() throws Exception {
//        // given
//        Member member1 = new Member();
//        Member member2 = new Member();
//        Todo todo = new Todo();
//        todo.setUser(member1); // 할 일을 다른 멤버가 생성했다고 가정
//
//        BDDMockito.given(todoRepository.findById(1L)).willReturn(todo);
//        BDDMockito.given(memberRepository.findById(2L)).willReturn(member2);
//
//        // when & then
//        Exception exception = Assertions.assertThrows(Exception.class, () -> todoService.updateTodo(1L, 2L, "content"));
//        assertTrue(exception.getMessage().contains("할 일을 생성한 유저만 수정할 수 있습니다."));
//    }
}
