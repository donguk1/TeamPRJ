package kopo.poly.service;

import kopo.poly.dto.BookingDTO;

public interface IBookingService {

    /*  예약정보 저장하기 */
    void insertBooking(BookingDTO pDTO) throws Exception;

}
