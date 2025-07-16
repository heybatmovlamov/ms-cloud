package com.mscloud.dao.repository;

import com.mscloud.dao.entity.PatientEntity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CloudRepository extends JpaRepository<PatientEntity, Long> {

    @Query(value = "SELECT get_lis_record_uid_oncloud(:patientName, :secretCode, :qrCode, :uId, :sesId, :pId, :orderId, :blankId, :cId, :fileName, :registrationTime, :approveTime)", nativeQuery = false)
    Integer callLisRecordUploadFunction(
            @Param("patientName") String patientName,
            @Param("secretCode") String secretCode,
            @Param("qrCode") String qrCode,
            @Param("uId") Integer uId,
            @Param("sesId") Integer sesId,
            @Param("pId") Integer pId,
            @Param("orderId") Integer orderId,
            @Param("blankId") Integer blankId,
            @Param("cId") Integer cId,
            @Param("fileName") String fileName,
            @Param("registrationTime") LocalDateTime registrationTime,
            @Param("approveTime") LocalDateTime approveTime
    );
}
