package com.example.spring2024.member;

import com.example.spring2024.common.exception.BadRequestException;
import com.example.spring2024.common.message.ErrorMessage;
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
    public MemberResponse getMember(Long memberId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        // MemberResponse DTO로 변환
        return new MemberResponse(member.getId(), member.getLoginId(), member.getPassword());
    }


    @Transactional
    public void updateMember(Long memberId,String loginId, String password) throws BadRequestException {
        Member member = memberRepository.findByLoginId(loginId);
        if(member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        memberRepository.updateById(memberId,loginId, password);
    }

    @Transactional
    public void deleteMember(Long memberId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Long loginMember(String loginId, String password) throws BadRequestException {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null || !member.getPassword().equals(password)) {
            throw new BadRequestException(ErrorMessage.INVALID_LOGIN_CREDENTIAL.getMessage());
        }
        member.signIn();
        return member.getId();  // Return the member ID if login is successful
    }
}
