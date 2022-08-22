package hiccup.hiccupstore.user.security.handler;

import hiccup.hiccupstore.user.security.common.NewSnsUserException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";

        if(exception instanceof BadCredentialsException){
            errorMessage = "Invalid Username or Password";
            setDefaultFailureUrl("/login?error=true&exception="+exception.getMessage());
        }else if(exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid secretKey";
            setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());
        }
//        }else if(exception instanceof NewSnsUserException){
//            request.setAttribute("userdto",((NewSnsUserException) exception).getUser());
//            setDefaultFailureUrl("/snsjoin");
//        }

        /** sns유저 join logic */
        super.onAuthenticationFailure(request,response,exception);

    }
}
