package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

//    ID 중복 체크
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

//    EMAIL 주소 중복 체크 및 인증 값
    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

//    회원 가입하기(회원정보 등록하기
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

//    로그일을 위해 아이디와 비밀번호가 일치하는지 확인하기
    UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception;

//    아이디, 비밀번호 찾기에 활용
    UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO) throws Exception;

//    비밀번호 재설정
    int newPasswordProc(UserInfoDTO pDTO) throws Exception;



}
