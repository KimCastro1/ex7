package com.castro.ex7.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class Note extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private TestMember writer;
    public void changeTitle(String title){
        this.title=title;
    }
    public void changeContent(String content){
        this.content=content;
    }

}
