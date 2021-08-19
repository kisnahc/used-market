package com.kisnahc.usedmarket.usedmarket.domain.member;

import com.kisnahc.usedmarket.usedmarket.config.AppProperties;
import com.kisnahc.usedmarket.usedmarket.mail.EmailMessage;
import com.kisnahc.usedmarket.usedmarket.mail.EmailService;
import com.kisnahc.usedmarket.usedmarket.web.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;

    private Member createMember(SignUpForm form) {
        Member member = Member.builder()
                .email(form.getEmail())
                .nickname(form.getNickname())
                .password(passwordEncoder.encode(form.getPassword()))
                .emailVerified(false)
                .build();
        member.generateEmailToken();
        return memberRepository.save(member);
    }


    public Member processNewMember(SignUpForm form) {
        Member saveMember = createMember(form);
        sendSignUpCheckEmail(saveMember);
        return saveMember;
    }

    public void sendSignUpCheckEmail(Member member) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + member.getEmailCheckToken() +
                "&email=" + member.getEmail());
        context.setVariable("nickname", member.getNickname());
        context.setVariable("linkName", "회원가입 이메일 인증");
        context.setVariable("text", "회원가입을 완료하려면 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());

        String text = templateEngine.process("mail/email-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(member.getEmail())
                .subject("Used-Market, 회원가입 인증 이메일")
                .text(text)
                .build();

        emailService.sendEmail(emailMessage);

    }

    public void completeSignUp(Member member) {
        member.completeSignUp();
    }

    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                //AuthenticationPrincipal
                new LoginMember(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(emailOrNickname);
        if (findMember == null) {
            findMember = memberRepository.findByNickname(emailOrNickname);
        }
        if (findMember == null) {
            throw new UsernameNotFoundException(emailOrNickname);
        }
        return new LoginMember(findMember);
    }
}
