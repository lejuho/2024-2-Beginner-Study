package com.example.spring2024.member;

import com.example.spring2024.friend.Friend;
import com.example.spring2024.todo.Todo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor()
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_login_id", nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(name = "user_password", nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();


    // 친구 추가 메서드
    public void addFriend(Member friendMember) {
        Friend friendship = new Friend(this, friendMember);
        friends.add(friendship);
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    // 할 일 추가 메서드
    public void addTodo(Todo todo) {
        todos.add(todo);
        todo.setUser(this);  // 연관관계 편의 메서드
    }

    // 할 일 삭제 메서드
    public void removeTodo(Todo todo) {
        todos.remove(todo);
        todo.setUser(null);  // 연관관계 해제
    }

    public void updateLogin(String newLoginId,String newPassword) {
        this.loginId = newLoginId;
        this.password = newPassword;
    }
}
