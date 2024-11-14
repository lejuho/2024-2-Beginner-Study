package com.example.spring2024.friendRequest;

import com.example.spring2024.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING; // 기본값을 PENDING으로 설정

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    public FriendRequest(Member sender, Member receiver) {
        this.sender = sender;
        this.receiver = receiver;
        // 생성 시 PENDING으로 초기화
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }
}

