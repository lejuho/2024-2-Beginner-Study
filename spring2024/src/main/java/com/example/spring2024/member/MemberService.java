package com.example.spring2024.member;

import com.example.spring2024.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(String loginId, String password) {
        Member member = new Member(loginId, password);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        // MemberResponse DTO로 변환
        return new MemberResponse(member.getId(), member.getLoginId(), member.getPassword());
    }


    @Transactional
    public void updateMember(Long memberId,String loginId, String password) throws Exception {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        member.updateLogin(loginId, password);
    }

    @Transactional
    public void deleteMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public Long loginMember(String loginId, String password) throws Exception {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null || !member.getPassword().equals(password)) {
            throw new Exception("Invalid login credentials.");
        }
        return member.getId();  // Return the member ID if login is successful
    }

}
