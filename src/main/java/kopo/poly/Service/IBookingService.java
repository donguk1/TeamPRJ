package kopo.poly.Service;

import kopo.poly.DTO.BookingDTO;

public interface IBookingService {

    /*  예약정보 저장하기 */
    void insertBooking(BookingDTO pDTO) throws Exception;

}
