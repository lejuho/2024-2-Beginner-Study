package com.example.todo_api.todo;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.todo.dto.TodoCreateRequest;
import com.example.todo_api.todo.dto.TodoResponse;
import com.example.todo_api.todo.dto.TodoUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateRequest request) throws BadRequestException {
        Long todoId = todoService.createTodo(request.getContent(),request.getMemberId());
        return ResponseEntity.created(URI.create("/todo/"+todoId)).build();
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<TodoResponse>> getTodoList(@PathVariable Long memberId) throws BadRequestException {
        List<TodoResponse> todoList = todoService.getTodoList(memberId);
        return ResponseEntity.ok().body(todoList);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) throws BadRequestException{
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}/{memberId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long todoId,@PathVariable Long memberId, @RequestBody @Valid TodoUpdateRequest request) throws BadRequestException {
        todoService.updateTodo(todoId,memberId,request.getUpdateContent());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{todoId}/{memberId}/check")
    public ResponseEntity<Void> check(@PathVariable Long todoId,@PathVariable Long memberId) throws BadRequestException {
        todoService.checkTodo(todoId);
        return ResponseEntity.ok().build();
    }
}
