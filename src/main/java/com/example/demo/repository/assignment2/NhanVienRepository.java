package com.example.demo.repository.assignment2;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query("SELECT nv FROM NhanVien nv where  nv.ten like :keyword or nv.ma like :keyword")
    public Page<NhanVien> findByTenMaLike(@Param("keyword") String keyword, PageRequest p);

    @Query("SELECT nv FROM NhanVien nv where nv.tenDN = :tenDN")
    public NhanVien findByRole(@Param("tenDN") String tenDN);
}
