package com.example.demo.repository.assignment2;

import com.example.demo.entity.HDCT;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.SPCT;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HDCT_Repository extends JpaRepository<HDCT, Integer> {
    @Query("SELECT hdct FROM HDCT hdct " +
            "JOIN hdct.idSPCT spct " +
            "WHERE spct.id = :keyword")
    Page<HDCT> findByIdSanPhamLike(@Param("keyword") Integer keyword, PageRequest p);

    @Query("SELECT hdct FROM HDCT hdct where hdct.id = :idHDCT")
    public HDCT findByTheoId(@Param("idHDCT") Integer idHDCT);

    @Query("SELECT hdct FROM HDCT hdct " +
            "JOIN hdct.idHD hd " +
            "WHERE hd.id = :keyword")
    List<HDCT> findByIdHoaDonLike(@Param("keyword") Integer keyword);

    //    @Query("SELECT hdct FROM HDCT hdct where hdct.idHD = :idHD")
//    List<HDCT> findByTheoIdHD(@Param("idHD") Integer idHD);
//    List<HDCT> findByIdHD(Integer idHD);
//    @Query("SELECT hdct FROM HDCT  hdct join hdct.idHD hd " +
//            " where hdct.idHD = :idHD")
//    List<HDCT> findByTheoIdHD(@Param("idHD") Integer idHd);
}
