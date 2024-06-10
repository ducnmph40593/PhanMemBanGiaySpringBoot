package com.example.demo.repository.assignment2;

import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT hd FROM HoaDon hd " +
            "JOIN hd.idKH kh " +
            "WHERE kh.id = :keyword")
    Page<HoaDon> findByIdSanPhamLike(@Param("keyword") Integer keyword, PageRequest p);


    List<HoaDon> findAllByTrangThai(Integer trangThai);
    @Query("SELECT hd FROM HoaDon hd WHERE hd.id = :idHD")
    HoaDon findByTheoIdHD(@Param("idHD") Integer idHD);
}
