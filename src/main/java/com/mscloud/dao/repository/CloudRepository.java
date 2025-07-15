package com.mscloud.dao.repository;

import com.mscloud.dao.entity.PatientEntity;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CloudRepository extends JpaRepository<PatientEntity, Long> {

    @Query(value = "SELECT get_lis_record_uid_oncloud(:patientName, :secretCode, :qrCode, :uId, :sesId, :pId, :orderId, :blankId, :cId, :fileName, :registrationTime, :approveTime)", nativeQuery = true)
    Integer callLisRecordUploadFunction(
            @Param("patientName") String patientName,
            @Param("secretCode") Integer secretCode,
            @Param("qrCode") Integer qrCode,
            @Param("uId") Integer uId,
            @Param("sesId") Integer sesId,
            @Param("pId") Integer pId,
            @Param("orderId") Integer orderId,
            @Param("blankId") Integer blankId,
            @Param("cId") Integer cId,
            @Param("fileName") String fileName,
            @Param("registrationTime") Timestamp registrationTime,
            @Param("approveTime") Timestamp approveTime
    );
}
