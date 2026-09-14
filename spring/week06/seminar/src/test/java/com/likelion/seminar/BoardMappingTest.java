package com.likelion.seminar;

import com.likelion.seminar.entity.Comment;
import com.likelion.seminar.entity.Post;
import com.likelion.seminar.entity.Writer;
import com.likelion.seminar.repository.CommentRepository;
import com.likelion.seminar.repository.PostRepository;
import com.likelion.seminar.repository.WriterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardMappingTest {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // 1. 양방향 매핑 검증
    @Test
    void writerPostCommentMappingTest() {
        Writer writer = new Writer("작성자A");
        Post post = new Post("첫 게시글", "게시글 내용입니다.");
        writer.addPost(post);

        Comment comment1 = new Comment("첫 댓글");
        Comment comment2 = new Comment("두 번째 댓글");
        writer.addComment(comment1);
        writer.addComment(comment2);
        post.addComment(comment1);
        post.addComment(comment2);

        writerRepository.save(writer);
        postRepository.save(post);

        entityManager.flush();
        entityManager.clear();

        Writer findWriter = writerRepository.findById(writer.getId()).orElseThrow();
        assertThat(findWriter.getPosts()).hasSize(1);
        assertThat(findWriter.getPosts().get(0).getTitle()).isEqualTo("첫 게시글");

        Post findPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(findPost.getComments()).hasSize(2);
        assertThat(findPost.getComments())
                .extracting(Comment::getContent)
                .containsExactlyInAnyOrder("첫 댓글", "두 번째 댓글");

        Comment findComment = commentRepository.findById(comment1.getId()).orElseThrow();
        assertThat(findComment.getWriter().getName()).isEqualTo("작성자A");
        assertThat(findComment.getPost().getTitle()).isEqualTo("첫 게시글");
    }

    // 2. 게시글 삭제 시 댓글 cascade + 작성자는 안 지워짐
    @Test
    void postDeleteCascadeTest() {
        Writer writer = new Writer("작성자B");
        Post post = new Post("삭제될 게시글", "내용");
        writer.addPost(post);

        Comment comment = new Comment("같이 삭제될 댓글");
        writer.addComment(comment);
        post.addComment(comment);

        writerRepository.save(writer);
        postRepository.save(post);
        entityManager.flush();

        Long writerId = writer.getId();
        Long postId = post.getId();
        Long commentId = comment.getId();

        postRepository.delete(post);
        entityManager.flush();
        entityManager.clear();

        assertThat(postRepository.findById(postId)).isEmpty();
        assertThat(commentRepository.findById(commentId)).isEmpty();
        assertThat(writerRepository.findById(writerId)).isPresent();
    }

    // 3. 댓글 단독 삭제, Post는 그대로
    @Test
    void commentDeleteAloneTest() {
        Writer writer = new Writer("작성자C");
        Post post = new Post("게시글", "내용");
        writer.addPost(post);

        Comment comment = new Comment("삭제될 댓글");
        writer.addComment(comment);
        post.addComment(comment);

        writerRepository.save(writer);
        postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        Long commentId = comment.getId();
        Long postId = post.getId();

        commentRepository.deleteById(commentId);
        entityManager.flush();
        entityManager.clear();

        assertThat(commentRepository.findById(commentId)).isEmpty();
        assertThat(postRepository.findById(postId)).isPresent();
    }

    // 4. Post 있는 Writer 삭제 시 예외
    @Test
    void writerDeleteFailsWhenHasPostTest() {
        Writer writer = new Writer("작성자D");
        Post post = new Post("게시글", "내용");
        writer.addPost(post);

        writerRepository.save(writer);
        postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        Writer findWriter = writerRepository.findById(writer.getId()).orElseThrow();

        assertThatThrownBy(() -> {
            writerRepository.delete(findWriter);
            entityManager.flush();
        }).isInstanceOf(Exception.class);
    }

    // 5. Comment만 있는 Writer 삭제 시 예외
    @Test
    void writerDeleteFailsWhenHasCommentOnlyTest() {
        Writer postWriter = new Writer("게시글작성자");
        Writer commentWriter = new Writer("댓글작성자");
        Post post = new Post("게시글", "내용");
        postWriter.addPost(post);

        Comment comment = new Comment("댓글");
        commentWriter.addComment(comment);
        post.addComment(comment);

        writerRepository.save(postWriter);
        writerRepository.save(commentWriter);
        postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        Writer findWriter = writerRepository.findById(commentWriter.getId()).orElseThrow();

        assertThatThrownBy(() -> {
            writerRepository.delete(findWriter);
            entityManager.flush();
        }).isInstanceOf(Exception.class);
    }

    // 6. 아무 연관 없는 Writer는 정상 삭제 (대조군)
    @Test
    void writerDeleteSucceedsWhenNoRelationsTest() {
        Writer writer = new Writer("연관관계없는작성자");
        writerRepository.save(writer);
        entityManager.flush();

        Long writerId = writer.getId();

        writerRepository.delete(writer);
        entityManager.flush();
        entityManager.clear();

        assertThat(writerRepository.findById(writerId)).isEmpty();
    }

    // 7. 여러 작성자가 한 게시글에 댓글 다는 경우
    @Test
    void multipleWritersCommentOnOnePostTest() {
        Writer postWriter = new Writer("게시글작성자");
        Writer commenterA = new Writer("댓글작성자A");
        Writer commenterB = new Writer("댓글작성자B");

        Post post = new Post("인기글", "내용");
        postWriter.addPost(post);

        Comment comment1 = new Comment("댓글작성자A의 댓글");
        Comment comment2 = new Comment("댓글작성자B의 댓글");
        commenterA.addComment(comment1);
        commenterB.addComment(comment2);
        post.addComment(comment1);
        post.addComment(comment2);

        writerRepository.save(postWriter);
        writerRepository.save(commenterA);
        writerRepository.save(commenterB);
        postRepository.save(post);

        entityManager.flush();
        entityManager.clear();

        Post findPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(findPost.getComments()).hasSize(2);
        assertThat(findPost.getComments())
                .extracting(c -> c.getWriter().getName())
                .containsExactlyInAnyOrder("댓글작성자A", "댓글작성자B");
    }

    // 8. 댓글 여러 개 중 하나만 삭제해도 나머지는 안전
    @Test
    void removeOneCommentKeepsOthersTest() {
        Writer writer = new Writer("작성자E");
        Post post = new Post("게시글", "내용");
        writer.addPost(post);

        Comment comment1 = new Comment("남을 댓글");
        Comment comment2 = new Comment("지워질 댓글");
        writer.addComment(comment1);
        writer.addComment(comment2);
        post.addComment(comment1);
        post.addComment(comment2);

        writerRepository.save(writer);
        postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        Long remainingId = comment1.getId();
        Long deletedId = comment2.getId();

        commentRepository.deleteById(deletedId);
        entityManager.flush();
        entityManager.clear();

        assertThat(commentRepository.findById(deletedId)).isEmpty();
        assertThat(commentRepository.findById(remainingId)).isPresent();

        Post findPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(findPost.getComments()).hasSize(1);
    }
}