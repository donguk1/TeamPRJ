package kopo.poly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {

    private String same;        // 회원정보 동일 체크
    private String user_name;   // 이름
    private String tel;       // 전화번호
    private String user_email;  // 이메일
    private String startday;    // 예약 시작일
    private String lastday;     // 예약 종료일
    private String stay;        // 숙소 타입
    private String room;        // 방 개수
    private String person;      // 예약 인원
    private String local;       // 여행지(국내)
    private String foreign;     // 여행지(국외)
    private String want;        // 요청 사항
    private String user_id;     // 아이디(포린키)



}
