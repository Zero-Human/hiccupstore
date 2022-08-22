package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.find.*;
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
            FindUserNameCompletedDto findUserNameByEmailCompletedDto = new FindUserNameCompletedDto(findUserNameByEmail.getNickname(), username);
            findUserNameByEmailCompletedMap.put("dto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    public HashMap<String,Object> findusernameByPhone(FindUserNameByPhoneDto findUserNameByPhone){

        String username = userMapper.searchUserNameByPhone(findUserNameByPhone.getNickname(), findUserNameByPhone.getPhone());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(username != null){
            FindUserNameCompletedDto findUserNameByEmailCompletedDto = new FindUserNameCompletedDto(findUserNameByPhone.getNickname(), username);
            findUserNameByEmailCompletedMap.put("dto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    public HashMap<String,Object> findpasswordbyemail(FindPasswordByEmailDto findPasswordByEmail){

        String password = userMapper.searchPasswordByEmail(findPasswordByEmail.getUsername(), findPasswordByEmail.getEmail());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(password != null){
            FindPasswordCompletedDto findPasswordCompletedDto = new FindPasswordCompletedDto(password, findPasswordByEmail.getUsername());
            findUserNameByEmailCompletedMap.put("dto",findPasswordCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    public HashMap<String,Object> findpasswordByPhone(FindPasswordByPhoneDto findPasswordByPhone){

        String password = userMapper.searchPasswordByPhone(findPasswordByPhone.getUsername(), findPasswordByPhone.getPhone());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(password != null){
            FindPasswordCompletedDto findPasswordCompletedDto = new FindPasswordCompletedDto(password, findPasswordByPhone.getUsername());
            findUserNameByEmailCompletedMap.put("dto",findPasswordCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }


}
