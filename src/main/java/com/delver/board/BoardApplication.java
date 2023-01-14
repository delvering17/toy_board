package com.delver.board;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.domain.post.Post;
import com.delver.board.service.MemberService;
import com.delver.board.service.PostService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}

//@Profile("test")
@RequiredArgsConstructor
@Component
@Slf4j
class InitDB {
	private final PostService postService;
	private final MemberService memberService;

	private Member member;
	@EventListener(ApplicationReadyEvent.class)
	public void init() throws InterruptedException {
		Thread.sleep(3000);
		log.info("야호야호야호");
		member = createTestMember();
		createTestPosts();
	}

	private Member createTestMember() {
		MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
				.userName("test")
				.email("test")
				.password("test")
				.passwordConfirm("test")
				.role(Role.USER)
				.joinRoot(JoinRoot.LOCAL)
				.build();
		Long memberId = memberService.save(dto);
		return memberService.findById(memberId);
	}

	private void createTestPosts() {
		for (int i = 1; i <= 258; i++) {
			PostSaveRequestDto dto = createPostSaveDto("제목" + i, "내용" + i, "카테고리");
			postService.savePost(dto, member.getId());
		}
	}

	private PostSaveRequestDto createPostSaveDto(String title, String content, String category) {
		return PostSaveRequestDto.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
	}

}