package com.example.spring2024.todo;

import com.example.spring2024.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Todo todo){
        em.persist(todo);
    }

    public Todo findById(Long todoId) {
        return em.find(Todo.class,todoId);
    }

    public List<Todo> findAll(){
        return em.createQuery("select t from Todo as t",Todo.class).getResultList();
    }

    public List<Todo> findAllByMember(Member member) {
        return em.createQuery("select t from Todo  as t where t.user = :todo_member ",Todo.class)
                .setParameter("todo_member",member)
                .getResultList();
    }

    public void updateTodo(Long todoId,String newContent){
        Todo todo = findById(todoId);
        todo.updateContent(newContent);
    }

    public void todoChecked(Long todoId){
        Todo todo = findById(todoId);
        if(!todo.isChecked()){
            todo.check();
        }
        else{
            System.out.println("Already done");
        }
    }

    public void deleteById(Long todoId){
        Todo todo = findById(todoId);
        em.remove(todo);
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
