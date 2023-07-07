package kopo.poly.persistance.mapper;

import kopo.poly.dto.BookingDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBookingMapper {

    // 예약정보 저장하기
    void insertBooking(BookingDTO pDTO) throws Exception;


}
