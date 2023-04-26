package edu.uneti.predictemailspam.service;

import edu.uneti.predictemailspam.model.entity.Email;

import java.io.IOException;
import java.util.List;

/**
 * Interface EmailService để viết các phương thức trừu tượng để thêm, sửa, xóa email trong DB
 * Các phương thức này được triển khai ở tầng triền khai impl
 */
public interface EmailService {
    // Gửi 1 email | lưu thông tin email này vào database
    Email sendEmail(Email email) throws IOException, ClassNotFoundException;

    // Lấy tất cả các email trong DB
    List<Email> findAllEmail();

    // Lấy 1 email trong DB dựa vào emailId
    Email findEmailById(Integer emailId);

    // Xóa email trong DB dựa vào emailId
    boolean delete(Integer emailId);
}
