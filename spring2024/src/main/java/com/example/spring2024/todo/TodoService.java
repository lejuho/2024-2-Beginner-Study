package com.example.spring2024.todo;

import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import com.example.spring2024.todo.dto.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createTodo(String content,Long memeberId) throws Exception {
        Member member = memberRepository.findById(memeberId);

        if(member == null){
            throw  new Exception("존재하지 않는 멤버입니다.");
        }

        Todo todo = new Todo(member,content);
        todoRepository.save(todo);
        return todo.getId();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getTodoList(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        List<Todo> todos = todoRepository.findAllByMember(member);
        return todos.stream()
                .map(todo -> new TodoResponse(todo.getId(), todo.getContent(), todo.isChecked(), member.getId()))
                .toList();
    }

    @Transactional
    public void updateTodo(Long todoId,Long memberId,String updateContent) throws Exception{
        Todo todo = todoRepository.findById(todoId);
        Member member = memberRepository.findById(memberId);

        if(todo == null) {
            throw new Exception("존재하지 않는 할 일 입니다.");
        }

        if(member == null){
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        if(todo.getUser()!=member){
            throw new Exception("할 일을 생성한 유저만 수정할 수 있습니다.");
        }

        todo.updateContent(updateContent);
    }

    @Transactional
    public void deleteTodo(Long todoId,Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId);
        Todo todo = todoRepository.findById(todoId);

        if(todo == null){
            throw new Exception("존재하지 않는 할 일 입니다.");
        }

        if(member == null){
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        todoRepository.deleteById(todoId);
    }
}
