package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor()
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "todo_content", columnDefinition = "varchar(200)")
    private String content;

    @Column(name = "todo_is_check",columnDefinition = "tinyint(1)")
    private boolean isChecked;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member user;

    public Todo(Member user, String content) {
        this.user = user;
        this.isChecked = false;
        this.content = content;
    }

    public void check(){
        isChecked = !isChecked;
    }

    public void updateContent(String newContent){
        this.content = newContent;
    }
}
