package com.example.spring2024.friend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.spring2024.member.Member;

@Entity
@Getter
@NoArgsConstructor()
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Member friend;

    public Friendship(Member user, Member friend) {
        this.user = user;
        this.friend = friend;
    }

    public void updateFriendship(Member user,Member friend) {
        if(this.user==user) {
            this.friend = friend;
        }
    }

    public void createFriendship(Member user,Member friend) {
        updateFriendship(user,friend);
    }
}

