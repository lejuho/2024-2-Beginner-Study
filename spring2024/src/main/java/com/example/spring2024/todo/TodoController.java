package com.example.spring2024.todo;

import com.example.spring2024.todo.dto.TodoCreateRequest;
import com.example.spring2024.todo.dto.TodoResponse;
import com.example.spring2024.todo.dto.TodoUpdateRequest;
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
    public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateRequest request) throws Exception {
        Long todoId = todoService.createTodo(request.getContent(),request.getMemberId());
        return ResponseEntity.created(URI.create("/todo/"+todoId)).build();
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<TodoResponse>> getTodoList(@PathVariable Long memberId) throws Exception {
        List<TodoResponse> todoList = todoService.getTodoList(memberId);
        return ResponseEntity.ok().body(todoList);
    }

    @DeleteMapping("/{todoId}/{memberId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId,@PathVariable Long memberId) throws Exception{
        todoService.deleteTodo(todoId,memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}/{memberId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long todoId,@PathVariable Long memberId, @RequestBody TodoUpdateRequest request) throws Exception {
        todoService.updateTodo(todoId,memberId,request.getUpdateContent());
        return ResponseEntity.ok().build();
    }


}
