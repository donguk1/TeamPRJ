package kopo.poly.Service.impl;

import kopo.poly.DTO.BookingDTO;
import kopo.poly.Persistance.Mapper.IBookingMapper;
import kopo.poly.Service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingService implements IBookingService {

    private final IBookingMapper bookingMapper;  // 예약 관련 SQL 사용하기 위한 Mapper 가져오기

    @Transactional
    @Override
    public void insertBooking(BookingDTO pDTO) throws Exception {

        /* 함수 접근 확인용 로그 */
        log.info(this.getClass().getName() + ".getInsertBooking Start!");
        log.info("예약 정보 작성 시작");

        bookingMapper.insertBooking(pDTO);



//        /* 데이터 입력 */
//        String same = CmmUtil.nvl(pDTO.getSame());
//        String user_name = CmmUtil.nvl(pDTO.getUser_name());
//        String tel = CmmUtil.nvl(pDTO.getTel());
//        String user_email = CmmUtil.nvl(pDTO.getUser_email());
//        String startday = CmmUtil.nvl(pDTO.getStartday());
//        String lastday = CmmUtil.nvl(pDTO.getLastday());
//        String stay = CmmUtil.nvl(pDTO.getStay());
//        String room = CmmUtil.nvl(pDTO.getRoom());
//        String person = CmmUtil.nvl(pDTO.getPerson());
//        String local = CmmUtil.nvl(pDTO.getLocal());
//        String foreign = CmmUtil.nvl(pDTO.getForeign());
//        String want = CmmUtil.nvl(pDTO.getWant());
//        String user_id = CmmUtil.nvl(pDTO.getUser_id());
//
//        /* 데이터 입력 확인용 로그 */
//        log.info("same : " + same);
//        log.info("user_name : " + user_name);
//        log.info("tel : " + tel);
//        log.info("user_email : " + user_email);
//        log.info("startday : " + startday);
//        log.info("lastday : " + lastday);
//        log.info("stay : " + stay);
//        log.info("room : " + room);
//        log.info("person : " + person);
//        log.info("local : " + local);
//        log.info("foreign : " + foreign);
//        log.info("want : " + want);
//        log.info("user_id : " + user_id);
//
//        log.info(this.getClass().getName() + ".getInsertBooking End!");
//        log.info("예약 정보 작성 종료");


    }
}
