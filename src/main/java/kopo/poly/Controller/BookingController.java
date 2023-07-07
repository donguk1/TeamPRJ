package kopo.poly.Controller;

import kopo.poly.Service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookingController {


    private final IBookingService BookingService;


    /*  예약화면으로 이동  */
    @GetMapping(value = "/siso/booking")
    public String booking() {

        // 확인용 로그(실행 된건지)
        log.info(this.getClass().getName() + "./siso/booking");
        log.info("예약화면 접속");

        return "/siso/booking";
    }




}
