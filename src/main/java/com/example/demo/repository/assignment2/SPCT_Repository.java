package com.example.demo.repository.assignment2;

import com.example.demo.entity.HDCT;
import com.example.demo.entity.SPCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SPCT_Repository extends JpaRepository<SPCT, Integer> {
    @Query("SELECT spct FROM SPCT spct " +
            "JOIN spct.idSanPham sp " +
            "WHERE sp.id = :keyword")
    Page<SPCT> findByIdSanPhamLike(@Param("keyword") Integer keyword, PageRequest p);

    @Query("SELECT a FROM SPCT a JOIN FETCH a.idSanPham JOIN FETCH a.idMauSac JOIN FETCH a.idKichThuoc")
    List<SPCT> findAllSPCTWithDetails();

    @Query("SELECT spct FROM SPCT spct where spct.id = :idSPCT")
    public SPCT findByTheoId(@Param("idSPCT") Integer idSPCT);
}
