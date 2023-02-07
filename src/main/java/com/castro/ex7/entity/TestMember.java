package com.castro.ex7.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class TestMember extends BaseEntity{
    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TestMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(TestMemberRole testMemberRole){
        roleSet.add(testMemberRole);
    }
}
