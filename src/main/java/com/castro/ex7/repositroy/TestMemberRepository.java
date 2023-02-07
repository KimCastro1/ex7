package com.castro.ex7.repositroy;

import com.castro.ex7.entity.TestMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestMemberRepository extends JpaRepository<TestMember, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from TestMember m where m.fromSocial = :social and m.email =:email")
    Optional<TestMember> findByEmail(@Param("social") boolean social, @Param("email") String email);

}
