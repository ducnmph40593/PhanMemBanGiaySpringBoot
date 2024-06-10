package com.example.demo.repository.assignment2;

import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    @Query("SELECT kt FROM KichThuoc kt where  kt.ten like :keyword or kt.ma like :keyword")
    public Page<KichThuoc> findByTenMaLike(@Param("keyword") String keyword, PageRequest p);
}
