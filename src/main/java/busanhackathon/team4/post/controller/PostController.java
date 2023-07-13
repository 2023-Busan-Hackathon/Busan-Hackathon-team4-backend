package busanhackathon.team4.post.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.post.dto.PostDto;
import busanhackathon.team4.post.service.PostService;
import busanhackathon.team4.recipe.dto.RecipeDto;
import busanhackathon.team4.recipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final RecipeService recipeService;

    @ApiOperation(value = "게시글 리스트 조회", notes = "요청 파라미터 없음")
    @GetMapping("/post")
    public ResponseEntity<Result> getPostList(@AuthenticationPrincipal User user) {
        List<PostDto> postDtoList = postService.findAllPost(user.getUsername());
        return ResponseEntity.ok(new Result(postDtoList, "게시글 리스트 조회 완료"));
    }

    @ApiOperation(value = "게시글 작성", notes = "요청 파라미터:<br>title<br>content<br>")
    @PostMapping("/post")
    public ResponseEntity<Result> enrollPost(@AuthenticationPrincipal User user, @RequestBody PostDto postDto) {
        Long postId = postService.enrollPost(user.getUsername(), postDto);
        return ResponseEntity.ok(new Result(postId, "게시글 등록 완료"));
    }

    @ApiOperation(value = "찜한 게시글 리스트 조회", notes = "요청 파마리터 없음")
    @GetMapping("/post-heart")
    public ResponseEntity<Result> getHeartPostList(@AuthenticationPrincipal User user) {
        List<PostDto> postDtoList = postService.findHeartPost(user.getUsername());
        return ResponseEntity.ok(new Result(postDtoList, "찜한 게시글 리스트 조회 완료"));
    }




}
