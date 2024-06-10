package com.example.demo.securityConfig;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment2.NhanVienRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Repository
public class ExcelConfig {
    @Autowired
    private NhanVienRepository nhanVienRepository;

    public boolean importExcelNV(MultipartFile file) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Bỏ qua dòng tiêu đề
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                NhanVien nv = new NhanVien();

                int id = (int) row.getCell(0).getNumericCellValue();

                if (id < 0) {
                    return false;
                }


                nv.setId(id);
                nv.setMa(row.getCell(1).getStringCellValue());
                nv.setTen(row.getCell(2).getStringCellValue());
                nv.setTenDN(row.getCell(3).getStringCellValue());
                nv.setMatKhau(row.getCell(4).getStringCellValue());
                nv.setVaiTro(row.getCell(5).getStringCellValue());
                nv.setTrangThai((int) row.getCell(6).getNumericCellValue());

                // Lưu nhân viên vào cơ sở dữ liệu
                nhanVienRepository.save(nv);
            }
            return true; // Trả về true nếu import thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu import thất bại
        }
    }
}
