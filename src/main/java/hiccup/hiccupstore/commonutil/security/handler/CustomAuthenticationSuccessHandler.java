package hiccup.hiccupstore.commonutil.security.handler;

import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //꺼내와서 참조
    private HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();

    //최종적으로 이동할수있도록
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        setDefaultTargetUrl("/");

        setTargetUrlParameter("/mypage");

        SavedRequest savedRequest = httpSessionRequestCache.getRequest(request,response);
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        //첫 sns가입자는 여기로온다.
        if(!user.isSnsflag()){
            setTargetUrlParameter("/join/snsjoin");
            redirectStrategy.sendRedirect(request,response,getTargetUrlParameter());
        } else if(savedRequest != null){
            String redirectUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,redirectUrl);
        }else{
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }

    }

}