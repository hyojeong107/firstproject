package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//CLASS의 용도1. 테이블 생성
//2.한 개의 글을 저장(생성자,set), 꺼내기(get) 하는 객체
//3.테스트: toString 메소드 필요
@AllArgsConstructor// 생성자 생성
@NoArgsConstructor // 디폴트 생성자 추가(객체 생성 할 때 기본 생성자 없으면 데이터 가져 올 때 에러 발생)
@ToString// toString 생성
//private변수는 직접 접근 할 수 없기 때문에 변수(id,title,content) 값을 가져오려면 getId(), getTitle() 같은 메서드가 필요함
//따라서 한번에 처리 할 수 있는 어노테이션 준다
@Getter
@Entity //엔티티 선언
public class Article { //한개의 글을 저장하는 객체다
    @Id // PK로 선언해라
    @GeneratedValue // 자동증가 처리
    private Long id; //글번호 컬럼, PK,자동증가

    @Column
    private String title; //제목 컬럼

    @Column
    private String content; //내용 컬럼


}
