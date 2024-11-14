package com.example.spring2024.todo;

import com.example.spring2024.common.exception.BadRequestException;
import com.example.spring2024.common.message.ErrorMessage;
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
    public Long createTodo(String content,Long memberId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        if(member == null) throw  new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());

        Todo todo = new Todo(member,content);
        todoRepository.save(todo);
        return todo.getId();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getTodoList(Long memberId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        if (member == null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());

        List<Todo> todos = todoRepository.findAllByMember(member);
        return todos.stream()
                .map(todo -> new TodoResponse(todo.getId(), todo.getContent(), todo.isChecked(), member.getId()))
                .toList();
    }

    @Transactional
    public void updateTodo(Long todoId,Long memberId,String updateContent) throws BadRequestException{
        Todo todo = todoRepository.findById(todoId);
        if(todo == null) throw new BadRequestException(ErrorMessage.TODO_NOT_EXIST.getMessage());
        Member member = todo.getUser();
        if(member == null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        todoRepository.updateTodo(todoId,updateContent);
    }

    @Transactional
    public void deleteTodo(Long todoId) throws BadRequestException{
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new BadRequestException(ErrorMessage.TODO_NOT_EXIST.getMessage());
        }
        Member member = todo.getUser();
        if(member == null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        todoRepository.deleteById(todoId);
    }

    @Transactional
    public void checkTodo(Long todoId) throws BadRequestException{
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new BadRequestException(ErrorMessage.TODO_NOT_EXIST.getMessage());
        }
        Member member =todo.getUser();
        if(member == null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        todoRepository.todoChecked(todoId);
    }
}
