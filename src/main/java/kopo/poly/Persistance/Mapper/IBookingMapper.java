package kopo.poly.Persistance.Mapper;

import kopo.poly.DTO.BookingDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBookingMapper {

    // 예약정보 저장하기
    void insertBooking(BookingDTO pDTO) throws Exception;


}
