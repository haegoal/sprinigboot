package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long savedId = memberRepository.save(memberEntity).getId();
        return savedId;
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdate(memberDTO);
        memberRepository.save(memberEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }


    public boolean login(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException());
        if(memberEntity!=null){
            return true;
        }else{
            return false;
        }
    }

    public boolean findByEmail(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new NoSuchElementException());
        if(memberEntity!=null){
            return true;
        }else{
            return false;
        }
    }
}
