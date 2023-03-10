package portfolio.beom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import portfolio.beom.argumentresolver.LoginUser;
import portfolio.beom.argumentresolver.MemberSession;
import portfolio.beom.argumentresolver.SessionConst;
import portfolio.beom.dto.request.LoginMemberRequest;
import portfolio.beom.dto.response.LoginMemberResponse;
import portfolio.beom.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final MemberService memberService;


    @PostMapping("/api/login")
    public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest,
                                                     HttpServletRequest request) {

        LoginMemberResponse memberResponse = memberService.login(loginMemberRequest);

        //우리가 프로젝트에서 세션으로 사용할 객체.
        MemberSession memberSession = MemberSession.builder()
                .memberId(memberResponse.getId())
                .name(memberResponse.getName())
                .email(memberResponse.getEmail())
                .build();

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberSession);

        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    @GetMapping("/api/session-test")
    public String loginArgumentResolver(@LoginUser MemberSession memberSession) {

        log.info("member session = {}", memberSession);

        if (memberSession == null) {
            return "없음";
        }
        return "로그인";
    }
}
