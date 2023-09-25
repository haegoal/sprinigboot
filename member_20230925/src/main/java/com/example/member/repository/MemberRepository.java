package com.example.member.repository;

import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}
