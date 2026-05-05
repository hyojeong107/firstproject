package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;


//<class가 new되면서 dto 객체 생성>
@AllArgsConstructor //어노테이션으로 코드 없어도 생성자 자동 생성
@ToString //어노테이션으로 코드 없어도 toString 자동 생성

public class ArticleForm {
    /*변수선언*/
    private Long id;
    private String title;
    private String content;

    //DTO를 엔티티 객체로 변환해주는 메소드 : toEntity()
    //toEntity() 메소드: 폼 데이터를 Article 엔티티 객체로 변환하는 역할
    public Article toEntity() {
        return new Article(this.id,this.title,this.content);
    }
}
