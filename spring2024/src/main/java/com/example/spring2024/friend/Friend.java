package com.example.spring2024.friend;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.spring2024.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Friend {

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

    public Friend(Member user, Member friend) {
        this.user = user;
        this.friend = friend;
    }
}

