package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final IUserInfoService userInfoService;

    /**
     * 회원가입 화면으로 이동
     */
    @GetMapping(value = "/user/userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm");

        return "/user/userRegForm";
    }

    /**
     * 회원가입 로직 처리
     */
    @PostMapping(value = "/user/insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");
        int res;
        String msg = ""; // 회원가입 결과에 대한 메시지를 전달할 변수
        String url = ""; // 회원가입 결과에 대한 URL 을 전달할 변수

        // 웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {


            /*
             * *******************************************************************************
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *        무조권 웹으로 받은 정보는 DTO 에 저장하기 위해 임시로 표현
             * *******************************************************************************
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id"));      //id
            String user_name = CmmUtil.nvl(request.getParameter("user_name"));  //name
            String password = CmmUtil.nvl(request.getParameter("password"));    //password
            String email = CmmUtil.nvl(request.getParameter("email"));          //email
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));          //주소
            String addr2 = CmmUtil.nvl(request.getParameter("addr"));           //상세주소


            /*
             * *******************************************************************************
             *          반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                          반드시 작성할것
             * *******************************************************************************
             */
            log.info("user_id : " + user_id);
            log.info("user_name : " + user_name);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1  : " + addr1);
            log.info("addr2 : " + addr2);
            // 로그를 찍어주어야 나중에 에러찾기 편함
            // 위치 확인용


            /*
             * *******************************************************************************
             *          웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *          무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * *******************************************************************************
             */

            // 웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_name(user_name);


            // 비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));
            // encryp : 암호화


            // 민감 정보힌 이메일은 AES128 - CBC로 암호화함
            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);


            /*
             * *******************************************************************************
             *          웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!
             *
             *          무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * *******************************************************************************
             */

            /*
             * 회원가입
             */
            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
                url = "/user/login";
//                추후 회원가입 입력화면에서 ajax를 활용해서 아이디와 이메일 중복을 체크하길 바람
//                완료
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
//                url = "/user/userRegForm";  일단 풀지 말것
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
//                url = "/user/userRegForm";  일단 풀지 말것
            }

//        } catch (DuplicateKeyException e) {     //PK인 USER_ID가 중복되어 에러가 발생시
//            msg = "이미 가입된 아이디입니다. 다른 아이디로 변경 후 다시 시도해주세요.";
////            url = "/user/userRegForm";
//            log.info(e.toString());
//            e.printStackTrace();

        } catch (Exception e) {
//            저장이 실패되면 사용자에게 보여줄 메시지
//            msg = "실패하였습니다. : " + e;
            msg = "시스템 오류로 실패하였습니다. 다시 시도해주세요.";
//            url = "/user/userRegForm";
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info("출력할 메세지 : " + msg);
            log.info("이동할 경로 : " + url);
            modelMap.addAttribute("msg", msg);
            modelMap.addAttribute("url", url);

            log.info(this.getClass().getName() + ".insertUserInfo End!");
        }

        return "/redirect";
    }

    /*
     * 로그인 처리 및 결과 알려주는 화면으로 이동
     */
    @PostMapping(value = "/user/loginProc")
    public String loginProc(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        log.info(this.getClass().getName() + ".loginProc Start!");

        String msg = "";    //로그인 결과에 대한 메시지를 전달할 변수
        String url = "";
        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            String user_id = CmmUtil.nvl(request.getParameter("user_id"));   //아이디
            String password = CmmUtil.nvl(request.getParameter("password")); //비밀번호

            log.info("user_id : " + user_id);
            log.info("password : " + password);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));
            //로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 userInfoService 호출하기
            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);
            /*
             * 로그인을 성공했다면, 회원아이디 정보를 session 에 저장함
             *
             * 세션은 톰켓(was)의 메모리에 존재, 웹사이트에 접속한 사람(연결된 객체)마다 메모리에 값을 올림
             *            *
             * 예) 톰켓에 100명의 사용자가 로그인했다면, 사용자 각각 회원아이디를 메모리에 저장하며
             *     메모리에 저장된 객체의 수는 100개이다.
             *     따라서 과도한 세션은 톰켓의 메로리 부하를 발생시켜 서버가 다운되는 현상이 있을 수 있기 때문에,
             *     최소한으로 사용하는 것을 권장
             *
             * 스프링에서 세션을 사용하기 위해서는 함수명의 파라미터에 HttpSession session 존재해야 한다.
             * 그냥 메모리에서 부르면 되기 때문에 화면, controller 에서 쉽게 불러서 쓸 수 있다.
             */
            if (CmmUtil.nvl(rDTO.getUser_id()).length() > 0) {

                /*
                 * 세션에 회원아이디 저장하기, 추후 로그인여부를 체크하기 위해 세션에 값이 존재하는지 체크
                 * 일반적으로 세션에 저장되는 키는 대문자 입력, 앞에 SS를 붙임
                 *
                 * Session 단어에서 SS를 가져온 것
                 */
                session.setAttribute("SS_USER_ID", user_id);
                session.setAttribute("SS_USER_NAME", CmmUtil.nvl(rDTO.getUser_name()));

                //로그인 성공 메세지와 이동할 경로의 url
                msg = "로그인이 성공했습니다. \n" + rDTO.getUser_name() + "님 환영합니다.";
                url = "/notice/noticeList";
            } else {
                msg = "로그인이 실패했습니다. 다시 시도하세요.";
                url = "/user/login";
            }


        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 문제로 로그인이 실패했습니다.";
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            modelMap.addAttribute("msg", msg);
            modelMap.addAttribute("url", url);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return "/redirect";

    }

    /*
     * 로그인을 위한 입력 화면으로 이동
     */
    @GetMapping(value = "/user/login")
    public String login() {

        log.info(this.getClass().getName() + ".user/login Start!");
        log.info("로그인 입력화면");
        log.info(this.getClass().getName() + ".user/login End!");
        return "/user/login";
    }

    /*
     * 회원 가입 전 아이디 중복체크하기(Ajax 를 통해 입력한 정보 받음)
     */
    @ResponseBody
    @PostMapping(value = "/user/getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start!");
        log.info("아이디 중복확인");

        String user_id = CmmUtil.nvl(request.getParameter("user_id"));  // 회원아이디

        log.info("user_id : " + user_id);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUser_id(user_id);

        // 회원아이디를 통해 중복되 아이디인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }

    /*
     * 회원 가입 전 이메일 중복체크하기(Ajax 를 통해 입력한 아이디 정보 받음)
     * 유효한 이메일인지 확인하기 위해 입력된 이메일에 인증번호 포함하여 메일 발송
     */
    @ResponseBody
    @PostMapping(value = "/user/getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists Start!");
        log.info("이메일 중복확인");

        String email = CmmUtil.nvl(request.getParameter("email")); // 회원아이디

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        // 입력된 이메일이 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getEmailExists End!");

        return rDTO;
    }


}
