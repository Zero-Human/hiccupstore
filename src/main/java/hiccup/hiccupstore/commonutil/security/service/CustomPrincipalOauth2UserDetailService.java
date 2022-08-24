package hiccup.hiccupstore.commonutil.security.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomPrincipalOauth2UserDetailService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    //구글로부터 받은 userRequest데이터에대한 후처리 함수
    //OAuth2User가 사실상 UserDetails네.
    //repository가 필요없군 google이 이미 repository한다음에 userRequest를 준것이다.

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("userRequest :  " + userRequest.getClientRegistration());
        System.out.println("AccessToken :  " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttribute :  " + super.loadUser(userRequest).getAttributes());
        //받은정보로 회원가입
        //우리같은경우 account 객체를 사용해보자.
        //한마디로 강제 회원가입
        //loadUser(userRequest).getAttributes() 통해서 회원프로필얻어낸다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //authentication.getpriciapl()하면 OAuth2User의 구현체가 들어간다는것이다.
        //OAuth2User의 .getattribute가 바로 프로필을 담고있다.
        //form쪽 User랑 헷갈리면안되는게 User에는 없어서 extends해줘서 만들어준것.
        //24분 그래서 일단은 OAuth2와 user는 그상속계층이 다르므로
        //만약에 우리가 매서드단위에서 파라미터로 받아줄때 따로따로 해줘야된다 (물론 나는 .getcontext해서 크게 문제없는듯)

//        String provider = userRequest.getClientRegistration().getClientId();
//        String providerid = oAuth2User.getAttribute("sub");
        String username = null;
        String password = null;
        String email = null;
        String role = null;
        String age = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){

            username = userRequest.getClientRegistration().getClientId()+"_"+oAuth2User.getAttribute("sub"); // google_342342353;
            password = null; //사실크게의미없다.
            email = oAuth2User.getAttribute("email");
            role = "ROLE_USER";
            age = "99";

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            Map response = (Map) oAuth2User.getAttributes().get("response");
            username = userRequest.getClientRegistration().getClientId()+"_"+(String)response.get("id"); // naver_342342353;
            password = null; //사실크게의미없다.
            email = (String) response.get("email");
            role = "ROLE_USER";
            age = "99";
        }

        UserDto user = userMapper.getUser(username);

        if( user == null ) {

            UserDto oauth2account = new UserDto(username, email,password, role);

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(role));

            Oauth2UserContext oauth2AccountContext;

            userMapper.save(oauth2account);
            user = userMapper.getUser(username);
            //가입되어있으면 true
            //가입이 안되있으면 false다.
            user.setSnsflag(false);
            roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getUserrole()));
            oauth2AccountContext = new Oauth2UserContext(user, roles, oAuth2User.getAttributes(), userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName());

            return oauth2AccountContext;

        } else {

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getUserrole()));
            Oauth2UserContext oauth2AccountContext = new Oauth2UserContext(user, roles, oAuth2User.getAttributes(), userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName());

            return oauth2AccountContext;

        }

    }


}
