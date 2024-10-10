package com.example.spring2024.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void memberSaveTest(){
        Member member = new Member("login", "password");
        memberRepository.save(member);

        Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    @Transactional
    void memberFindByIdTest(){
        Member member = new Member("login", "password");
        memberRepository.save(member);

        memberRepository.flushAndClear();

        Member found = memberRepository.findById(member.getId());
        Assertions.assertThat(found.getId()).isEqualTo(member.getId());
    }

    @Test
    @Transactional
    void memberFindAllTest(){
        Member Member1 = new Member("login1", "password");
        Member Member2 = new Member("login2", "password");
        Member Member3 = new Member("login3", "password");
        memberRepository.save(Member1);
        memberRepository.save(Member2);
        memberRepository.save(Member3);

        List<Member> MemberList = memberRepository.findAll();

        Assertions.assertThat(MemberList).hasSize(4);
    }

    @Test
    @Transactional
    @Rollback(false)
    void memberUpdateTest(){
        Member member = new Member("login", "password");
        memberRepository.save(member);

        memberRepository.flushAndClear();

        Member member1 = memberRepository.findById(member.getId());
        member1.updateLogin("newlogin","newpassword");
    }

    @Test
    @Transactional
    @Rollback(false)
    void memberDeleteTest(){
        Member member1 = new Member("login", "password");
        Member member2 = new Member("login2", "password2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        memberRepository.flushAndClear();

        memberRepository.deleteById(member1.getId());
    }
}
