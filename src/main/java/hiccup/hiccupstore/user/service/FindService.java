package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.FindUserNameByEmailCompletedDto;
import hiccup.hiccupstore.user.dto.FindUserNameByEmailDto;
import hiccup.hiccupstore.user.dto.FindUserNameByPhoneDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FindService {

    private final UserMapper userMapper;

    public HashMap<String,Object> findusernamebyemail(FindUserNameByEmailDto findUserNameByEmail){

        String username = userMapper.searchUserNameByEmail(findUserNameByEmail.getNickname(), findUserNameByEmail.getEmail());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(username != null){
            FindUserNameByEmailCompletedDto findUserNameByEmailCompletedDto = new FindUserNameByEmailCompletedDto(findUserNameByEmail.getNickname(), username);
            findUserNameByEmailCompletedMap.put("dto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    public HashMap<String,Object> findusernameByPhone(FindUserNameByPhoneDto findUserNameByPhone){

        String username = userMapper.searchUserNameByEmail(findUserNameByPhone.getNickname(), findUserNameByPhone.getPhone());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(username != null){
            FindUserNameByEmailCompletedDto findUserNameByEmailCompletedDto = new FindUserNameByEmailCompletedDto(findUserNameByPhone.getNickname(), username);
            findUserNameByEmailCompletedMap.put("dto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }


}
