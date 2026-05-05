package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

@Slf4j // Lombok이 자동으로 log 객체를 생성해줌, 로그 객체(@slf4j)가 선언되어 있어야함
@Controller // 어노테이션
public class ArticleController {
    // ArticleRepository 라는 class에 save라는 메소드 있다. save(Entity) => db insert
    //ArticleRepository 라는 인터페이스가 run 할 때 new된다 => new된 것을 주입해줌: @Autowired
    // private ArticleRepository articleRepository ; // new하지 않고 어노테이션으로 생성
    //new하면 코드가 길어짐
    @Autowired
    private ArticleRepository articleRepository ;

    //  1.입력화면 요청(client-URL을 통해)받으면 클라이언트에게 입력폼을 보내는 역할
    //  method명 : newArticleForm
//  어떤 요청 처리 : 요청 URL 지정
//  get방식 요청, URL 처리 지정 : @GetMapping(요청URL 지정)
    @GetMapping("/articles/new")// 사용자가 http://localhost:8080/articles/new 입력
    public String newArticleForm(){
        return "articles/new"; // articles/new.mustache에게 가라
        // .mustache 안붙여도 자동으로 파일 확장자를 붙여준다. resources/templates/ 폴더 안에서 템플릿을 찾고 설정된 템플릿 엔진의 확장자를 자동으로 붙여줘.
    }

    // 2. 추가
    //  post방식 요청  @PostMapping
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){ //form=dto
        log.info(form.toString()); //파일에 로그 남기기
        //<dto(form)를 엔티티로 변환 코드>
        // article이 엔티티 객체
        Article article = form.toEntity(); //toEntity() 메소드로 엔티티로 변환
        log.info(article.toString()); // Entity(Article) 객체의 내용을 로그로 출력하는 코드

        // <리파지토리(DAO)로 엔티티를 db에 저장하는 코드>
        //(article을)insert 하고 update한 값을 saved에 저장
    Article saved = articleRepository.save(article);
    // save() 메소드 : 새로운 데이터를 DB에 저장하거나
        // 기존 데이터를 업데이트하는 기능을 수행


    //<DB에 정상적으로 insert 되었는지 확인>
    System.out.println("insert후: "+saved.toString());
        log.info(saved.toString());
        // 저장 완료 후, 생성된 게시글 상세 페이지로 redirect
        return "redirect:/articles/"+saved.getId(); // View: 상세(/articles/{id})로 이동하게 해야한다


    }

    //3. 상세 페이지
    // 특정 ID 값을 포함한 URL로 요청했을 때,해당 ID에 해당하는 데이터를 찾아서 뷰(HTML)로 전달하는 기능
    @GetMapping("/articles/{id}") // /articles/id 요청받겠다
    //@PathVariable : URL 요청으로 들어온 전달 값을 컨트롤러의 매개변수로 가져오는 어노테이션
    public String show(@PathVariable Long id, Model model){
        // show() 라는 메소드:URL을 받아서 요청을 수행
        //Model model →찾은 데이터를 HTML로 전달하는 Spring이 제공하는 객체
        log.info("id= "+ id);
        //(1) id를 조회해서 데이터를 가져오기
        //(1.1) 글번호로 단일 데이터 조회-> 엔티티 반환
        //findById() => return Optional<Article> 반환 데이터 타입 Optional
        //findByID.orElse(null) => return Article 된다
        // orElse(null) => null의 의미: 조회 할 번호가 없으면 null로 처리 해라 (예외처리)
       Article articleEntity =
               articleRepository.findById(id).orElse(null); //글 번호로 단일값 추출 메소드(select)
        //<모델에 찾은 데이터 추가(add)하기>
        //컨트롤러에서 조회한 articleEntity(게시글 데이터)를 article이라는 이름으로 뷰에 넘겨줌
        model.addAttribute("article",articleEntity); //article: key , articleEntity(오브젝트): value
        //<view 페이지 반환(return)>
        return "articles/show"; //articles 폴더 아래,show.mustache
    }

    //4.전체 글 목록 출력
    @GetMapping("/articles") // /articles 요청이 들어 왔을 때
    public String index(Model model){


        //(1) 데이터 처리 (select = findAll)
        //(1.1) return List<Article>
        List<Article> articleEntityList =
                (List<Article>)articleRepository.findAll();
        //(2) Model에 추가
    model.addAttribute("articleList",articleEntityList);
        //(3) 뷰 설정
        return "articles/index";



    }
    // 5.게시글 수정
    @GetMapping("/articles/{id}/edit")
    //URL 매핑
    public String edit(@PathVariable Long id,Model model) {
        //수정화면 데이터 표시: 데이터 베이스 추출(데이터 조회)
    Article articleEntity=
            articleRepository.findById(id).orElse(null);
    // 모델에 찾은 데이터(수정할 데이터) 추가
    model.addAttribute("article",articleEntity);
    //게시글 수정 할 수 있도록 폼을 보여줌
        return "articles/edit";}

    // <게시글 수정 기능을 처리하는 코드>
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        //(1) 수정 폼에서 넘어온 DTO(수정 데이터) 확인
        log.info(form.toString());
        //(2)데이터 베이스 처리:수정 처리
        //(2.1) DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        //(2.2) 엔티티를 DB처리
        //(2.2.1) 존재하는 데이터인지 확인 : findById()
        Article target =
                articleRepository.findById(
                        articleEntity.getId()
                ) .orElse(null);
        //(2.2.2) null이 아니면 save : update
        if(target != null){
            articleRepository.save(articleEntity);
        }
        //(3) 상세화면이동: articles/번호
        return "redirect:/articles/"+articleEntity.getId();
    }
    //6.게시글 삭제
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id
        , RedirectAttributes rttr
    ){
        //메소드 실행 되는지 확인
        log.info("삭제 요청이 들어왔다.");
        //(1) 삭제 할 글번호{id} 추출 => @PathVariable Long 파라미터에 가져온다
        //(1.1) 번호가 있는지 없는지 확인 : selecrt(id) : findByID(id)
        Article target =
                articleRepository.findById(id).orElse(null);
        //(2) 번호로 DB 삭제 처리
        if(target !=null ){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg"
                    ,target.getId()+"번 글을 삭제");
        }
        //(3) 목록 페이지로 리다이렉트 처리
        return "redirect:/articles";
    }
}
