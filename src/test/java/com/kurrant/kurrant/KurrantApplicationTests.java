package com.kurrant.kurrant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurrant.kurrant.model.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KurrantApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();

    private BoardResponseDto registeredBoard;
    private ArticleResponseDto registeredArticle;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    @DisplayName("게시판 생성")
    void createBoard() throws JsonProcessingException {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .nameKo("호양 자유게시판")
                .build();

        String requestBody = mapper.writeValueAsString(boardRequestDto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<BoardResponseDto> response = restTemplate.postForEntity(
                "/board",
                request,
                BoardResponseDto.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BoardResponseDto boardResponse = response.getBody();
        assertThat(boardResponse.getBoard_id()).isGreaterThan(0L);
        assertThat(boardResponse.getName_ko()).isEqualTo(boardRequestDto.getNameKo());

        registeredBoard = boardResponse;
    }

    @Test
    @Order(2)
    @DisplayName("게시글 생성")
    void createArticle() throws JsonProcessingException {
        // given
        ArticleRequestDto articleRequestDto = ArticleRequestDto.builder()
                .boardId(registeredBoard.getBoard_id())
                .title("게시글 작성 제목")
                .content("<div><span>게시글 작성 내용</span></div>")
                .build();

        String requestBody = mapper.writeValueAsString(articleRequestDto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<ArticleResponseDto> response = restTemplate.postForEntity(
                "/article",
                request,
                ArticleResponseDto.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ArticleResponseDto articleResponse = response.getBody();
        assertThat(articleResponse).isNotNull();
        assertThat(articleResponse.getArticle_id()).isGreaterThan(0L);
        assertThat(articleResponse.getTitle()).isEqualTo(articleRequestDto.getTitle());
        assertThat(articleResponse.getContent_html()).isEqualTo(articleRequestDto.getContent());

        registeredArticle = articleResponse;
    }

    @Test
    @Order(3)
    @DisplayName("게시판 목록 조회")
    void getAllArticle() {
        // given
        Long boardId = registeredBoard.getBoard_id();

        // when
        ResponseEntity<ArticleResponseListDto[]> responseEntity = restTemplate.getForEntity(
                "/board/" + boardId,
                ArticleResponseListDto[].class
        );
        List<ArticleResponseListDto> response = Arrays.asList(responseEntity.getBody());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response).isNotNull();
        assertThat(response.size()).isGreaterThan(0);
        assertThat(response.get(0).getName_ko()).isEqualTo(registeredArticle.getBoard_name());
        assertThat(response.get(0).getArticle_id()).isEqualTo(registeredArticle.getArticle_id());
        assertThat(response.get(0).getTitle()).isEqualTo(registeredArticle.getTitle());
    }

    @Test
    @Order(4)
    @DisplayName("게시글 상세 조회")
    void getArticle() {
        // given
        Long articleId = registeredArticle.getArticle_id();

        // when
        ResponseEntity<ArticleResponseDto> response = restTemplate.getForEntity(
                "/article/" + articleId,
                ArticleResponseDto.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ArticleResponseDto articleResponse = response.getBody();
        assertThat(articleResponse).isNotNull();
        assertThat(articleResponse.getBoard_name()).isEqualTo(registeredBoard.getName_ko());
        assertThat(articleResponse.getTitle()).isEqualTo(registeredArticle.getTitle());
        assertThat(articleResponse.getContent_html()).isEqualTo(registeredArticle.getContent_html());
        assertThat(articleResponse.getContent_string()).isEqualTo(registeredArticle.getContent_string());
        assertThat(articleResponse.getView_count()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    @DisplayName("게시판 이름으로 게시글 검색")
    void getAllArticleByBoardName() {
        // when
        ResponseEntity<ArticleResponseListDto[]> responseEntity = restTemplate.getForEntity(
                "/article/search/boardName/" + "자",
                ArticleResponseListDto[].class
        );
        List<ArticleResponseListDto> response = Arrays.asList(responseEntity.getBody());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.size()).isGreaterThan(0);
        assertThat(response.get(0).getName_ko()).contains("자");
    }

    @Test
    @Order(6)
    @DisplayName("게시글 제목으로 게시글 검색")
    void getAllArticleByTitle() {
        // when
        ResponseEntity<ArticleResponseListDto[]> responseEntity = restTemplate.getForEntity(
                "/article/search/title/" + "제목",
                ArticleResponseListDto[].class
        );
        List<ArticleResponseListDto> response = Arrays.asList(responseEntity.getBody());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.size()).isGreaterThan(0);
        assertThat(response.get(0).getName_ko()).contains(registeredArticle.getBoard_name());
        assertThat(response.get(0).getTitle()).contains(registeredArticle.getTitle());
    }

    @Test
    @Order(7)
    @DisplayName("날짜로 게시글 검색")
    void getAllArticleByDate() throws JsonProcessingException {
        // given
        ArticleRequestByDateDto articleRequestByDateDto = ArticleRequestByDateDto.builder()
                .startDate("2022-09-01")
                .endDate("2022-09-30")
                .build();

        String requestBody = mapper.writeValueAsString(articleRequestByDateDto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<ArticleResponseListDto[]> responseEntity = restTemplate.postForEntity(
                "/article/search/date",
                request,
                ArticleResponseListDto[].class
        );
        List<ArticleResponseListDto> response = Arrays.asList(responseEntity.getBody());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.size()).isGreaterThan(0);
        assertThat(response.get(0).getTitle()).contains(registeredArticle.getTitle());
    }

    @Test
    @Order(8)
    @DisplayName("게시글 삭제")
    void deleteArticle() {
        // given
        Long articleId = registeredArticle.getArticle_id();

        // when
        ResponseEntity<Void> response = restTemplate.exchange(
                "/article/" + articleId,
                HttpMethod.DELETE,
                null,
                Void.class,
                articleId
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}