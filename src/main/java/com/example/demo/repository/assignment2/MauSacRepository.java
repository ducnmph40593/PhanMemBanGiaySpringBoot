package com.example.demo.repository.assignment2;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    @Query("SELECT ms FROM MauSac ms where  ms.ten like :keyword or ms.ma like :keyword")
    public Page<MauSac> findByTenMaLike(@Param("keyword") String keyword, PageRequest p);
}
