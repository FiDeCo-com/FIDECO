package project.boot.fideco.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boot.fideco.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	MemberEntity findByMemberId(String memberId);

	boolean existsByMemberId(String memberId);
}
