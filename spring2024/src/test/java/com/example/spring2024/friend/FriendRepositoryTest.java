package com.example.spring2024.friend;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class FriendRepositoryTest {
    @Autowired
    private FriendRepository friendRepository;

    @Test
    @Transactional
    @Rollback(false)
    void friendSaveTest(){
        Friend friend = new Friend();
        friendRepository.save(friend);

        Assertions.assertThat(friend.getId()).isNotNull();
    }


    @Test
    @Transactional
    void friendFindByIdTest(){
        Friend friend = new Friend();
        friendRepository.save(friend);

        friendRepository.flushAndClear();
        Friend found = friendRepository.findById(friend.getId());
        Assertions.assertThat(found.getId()).isEqualTo(friend.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void friendDeleteTest(){
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        friendRepository.save(friend1);
        friendRepository.save(friend2);

        friendRepository.flushAndClear();

        friendRepository.deleteById(friend1.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void friendUpdateTest(){
        Friend friend1 = new Friend();
        friendRepository.save(friend1);
        friendRepository.flushAndClear();
        Friend found = friendRepository.findById(friend1.getId());
        friendRepository.updateById(1L,2L,3L);
    }
}
