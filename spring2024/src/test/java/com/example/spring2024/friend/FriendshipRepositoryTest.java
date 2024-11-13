package com.example.spring2024.friend;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class FriendshipRepositoryTest {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Test
    @Transactional
    @Rollback(false)
    void friendSaveTest(){
        Friendship friendship = new Friendship();
        friendshipRepository.save(friendship);

        Assertions.assertThat(friendship.getId()).isNotNull();
    }


    @Test
    @Transactional
    void friendFindByIdTest(){
        Friendship friendship = new Friendship();
        friendshipRepository.save(friendship);

        friendshipRepository.flushAndClear();
        Friendship found = friendshipRepository.findById(friendship.getId());
        Assertions.assertThat(found.getId()).isEqualTo(friendship.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void friendDeleteTest(){
        Friendship friendship1 = new Friendship();
        Friendship friendship2 = new Friendship();
        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);

        friendshipRepository.flushAndClear();

        friendshipRepository.deleteById(friendship1.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void friendUpdateTest(){
        Friendship friendship1 = new Friendship();
        friendshipRepository.save(friendship1);
        friendshipRepository.flushAndClear();
        Friendship found = friendshipRepository.findById(friendship1.getId());
        friendshipRepository.updateById(1L,2L,3L);
    }
}
