package com.example.demo.repository.assignment2;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    @Query("SELECT nv FROM KhachHang nv where  nv.ten like :keyword or nv.ma like :keyword")
    public Page<KhachHang> findByTenMaLike(@Param("keyword") String keyword, PageRequest p);


    public KhachHang findBySdtLike( String sdt);

    @Query("SELECT kh FROM KhachHang kh WHERE kh.sdt LIKE %:sdt%")
    public KhachHang findBysdtKH(@Param("sdt") String sdt);


    @Query("SELECT kh FROM KhachHang kh WHERE kh.id = 1")
    public KhachHang findDefaultKhachHang();
    @Query("SELECT kh FROM KhachHang kh where kh.id = :idKH")
    public KhachHang findByTheoId(@Param("idKH") Integer idKH);
}
