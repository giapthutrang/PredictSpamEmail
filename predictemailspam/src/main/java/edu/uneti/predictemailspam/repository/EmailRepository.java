package edu.uneti.predictemailspam.repository;

import edu.uneti.predictemailspam.model.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface EmailRepository có nhiệm vụ thao tác với DB( như thêm mới, cập nhật, xóa dữ liệu trong database
 * Interface EmailRepository kể thừa từ interface JpaRepository để kế thừa lại các phương thức thao tác với database
 * 2 tham số Email entity và Integer là kiểu dữ liệu của trường ID (key của Email entity)
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
}