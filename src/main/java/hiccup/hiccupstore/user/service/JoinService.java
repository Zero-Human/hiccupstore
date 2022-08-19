package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.JoinFormDto;
import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.dto.SnsJoinDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserMapper userMapper;

    /** 회원가입된 유저를 저장하는 로직 */
    public Integer userSave(JoinFormDto joinFormDto){

        /** joinformdto를 userDto로 바꿉니다. */
        UserDto user = UserDto.builder().
                userName(joinFormDto.getUserName()).
                nickName(joinFormDto.getNickName()).
                password(joinFormDto.getPassword()).
                phone(joinFormDto.getPhone()).
                email(joinFormDto.getEmail()).
                address(joinFormDto.getAddress1() +"/"+ joinFormDto.getAddress2() +"/"+ joinFormDto.getAddress3()).
                birth(joinFormDto.getBirthYear()+"/"+joinFormDto.getBirthMonth()+"/"+joinFormDto.getBirthDay()).
                userrole(joinFormDto.getUserrole()).
                build();

        System.out.println(user);

        Integer save = userMapper.save(user);

        return save;

    }

    public String getUser(String userName){

        String s = userMapper.searchUserName(userName);

        return s;

    }

    public Integer userUpdate(SnsJoinDto snsJoinDto) {

        UserDto joinUserInfo = UserDto.builder().
                nickName(snsJoinDto.getNickName()).
                phone(snsJoinDto.getPhone()).
                address(snsJoinDto.getAddress1() +"/"+ snsJoinDto.getAddress2() +"/"+ snsJoinDto.getAddress3()).
                birth(snsJoinDto.getBirthYear()+"/"+snsJoinDto.getBirthMonth()+"/"+snsJoinDto.getBirthDay()).
                build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        return userMapper.updateuser(user.getUserName(),joinUserInfo.getAddress(),joinUserInfo.getNickName(),joinUserInfo.getPhone());

    }
}
