package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleApiController {

//    @Autowired
//    private ArticleRepository articleRepository;
//    // 1.전체 조회 처리(Get)
//    @GetMapping("/api/articles")
//    public List<Article>index(){
//        return (List<Article>) articleRepository.findAll();
//    }
//    //1.1단일 조회(Get)
//    @GetMapping("/api/articles/{id}")
//    public Article show(@PathVariable Long id){
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    // 2.추가 처리
//    @PostMapping("/api/articles") //전체조회와 url같아도 post방식이여서 ㄱㅊ음
//    public Article create(@RequestBody ArticleForm dto){ //입력한 값이 자동으로 ArticleForm으로 안들어감
//        //@RequestBody라는 어노테이션이 입력한 값 dto에 자동으로 들어가게 해준다
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//    // 3.수정 처리
//    @PatchMapping("api/articles/{id}")
//    public ResponseEntity<Article> update(
//        @PathVariable Long id,
//        @RequestBody ArticleForm dto
//    ) {
//        //(1) dto => Entity 변환
//        Article article = dto.toEntity();
//        //(2) 해당 글번호가 존재하는지 확인: id가 존재 확인
//        Article target =
//                articleRepository.findById(id).orElse(null);
//        //(3) 존재x => 수정 x : 잘못요청했다 => 처리
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        //(4) 존재o => 수정처리, 정상응답(200)
//        Article updated =
//                articleRepository.save(article); // 존재하는경우 save => update
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//        //4. 삭제 처리
//    }
    @Autowired //@Service 어노테이션으로 run될 때 new 돼서 따로 new해서 대입 하지 않고 달라고 하면 된다 => @Autowired

    private ArticleService articleService;
    //1. 전체 목록 조회
    @GetMapping("/api/articles")
    private List<Article> index(){
       return articleService.index();
    }

    //2. 단일 글 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //3. 게시글 생성 요청
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
    //3.1 dto를 entity로 바꿔주는 작업 필요없다
        //created는 insert후의 값
        //삼항연산자 : (조건식) ? true 처리 : false 처리;
        Article created = articleService.create(dto);
        return (created !=null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                ;
    }
  } // class end
