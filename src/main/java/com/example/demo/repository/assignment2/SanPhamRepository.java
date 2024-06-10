package com.example.demo.repository.assignment2;

import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query("SELECT sp FROM SanPham sp where  sp.ten like :keyword or sp.ma like :keyword")
    public Page<SanPham> findByTenMaLike(@Param("keyword") String keyword, PageRequest p);

}
