package edu.uneti.predictemailspam.rest;

import edu.uneti.predictemailspam.model.dto.EmailDto;
import edu.uneti.predictemailspam.model.entity.Email;
import edu.uneti.predictemailspam.model.mapper.EmailMapper;
import edu.uneti.predictemailspam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
 *CrossOrigin - Chia sẻ tài nguyên nguồn gốc chéo - Cho phep http://localhost:3000 su dung cac tai nguyen APIs
 * @RequestMapping("/api/v1/email") : entry point - khai báo địa chỉ APIs
 */
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/email")
public class EmailRestController {
    //Emailservice có nhiệm vụ xử lý các logic để cung cấp data cho các phuong thức trong controller để trả về cho user
    @Autowired
    private EmailService emailService;
    /**
     * EmailMapper có nhiệm vụ chuyển dổi dữ liệu qua lại Entity với DTO
     * Entity - dùng để lưu trữ dữ liệu các bản ghi trong table lấy từ Database
     * DTO - dùng để lưu trữ hoặc trả về data của người dùng truyền lên nó chỉ đơn giản chứa các trường, phương thức setter, getter
     */
    @Autowired
    private EmailMapper emailMapper;

    /**
     * đường dẫn APIs : http://localhost:8082/api/v1/email/emails - GET
     * @return List<EmailDto> - trả về 1 list email từ database
     */
    @RequestMapping(value = "/emails", method = RequestMethod.GET)
    public ResponseEntity<List<EmailDto>> getAllEmail() {
        // Lấy tất cả entity email từ database thông qua phương thức findAllEmail của EmailService
        List<Email> emails = emailService.findAllEmail();
        //Transfer từ list entity email --> DTO email thông qua phương thức toEmailList của class EmailMapper
        List<EmailDto> res = emailMapper.toEmailList(emails);
        // Trả về cho người dùng một list email email DTO và trạng thái OK
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * đường dẫn APIs : http://localhost:8082/api/v1/email/detail-email - GET
     * @param emailId
     * @return EmailDto - trả về thông tin chi tiet một email dựa vào tham số emailId
     */
    @RequestMapping(value = "/detail-email", method = RequestMethod.GET)
    public ResponseEntity<EmailDto> getAllEmail(@RequestParam("emailId") Integer emailId) {
        // Lấy chi tiết 1 email từ database thông qua phương thức findEmailById với tham số emailId
        Email email = emailService.findEmailById(emailId);
        //Transfer 1 entity email sang 1 email DTO thông qua phương thức toDto của class EmailMapper
        EmailDto res = emailMapper.toDto(email);
        // Trả về cho người dùng một email email DTO và trạng thái OK
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * đường dẫn APIs : http://localhost:8082/api/v1/email/send - POST
     * @param email
     * @return EmailDto - trả về thông tin email sau khi gửi thành công kèm trạng thái thành công
     */
    @PostMapping(value = "/send")
    public ResponseEntity<EmailDto> createEmail(@RequestBody EmailDto email) throws IOException, ClassNotFoundException {
        //Lấy thời gian hiện tại
        LocalDate localDate = LocalDate.now();
        //Format date thành dạng dd/MM
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM");
        String time = localDate.format(formatters);
        //Lưu lại thơi gian tạo email
        email.setTime(time);
        //Transfer email DTO -> email entity để lưu lại trong database
        Email entity = emailMapper.toEmail(email);
        //Transfer email entity(đã tạo trong database) -> email DTO để trả về cho người dùng và kèm thông báo trạng thái thành công
        EmailDto res = emailMapper.toDto(emailService.sendEmail(entity));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * đường dẫn APIs : http://localhost:8082/api/v1/email/delete - POST
     * @param emailId
     * @return - trả về nội dung xoa email thanh cong và trạng thái xoa thành công
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmail(@RequestParam("emailId") Integer emailId) {
        // Sử dụng toán tử 3 ngôi để trả về nội dung Xoa email thanh cong or Co loi xay ra khi xoa email trong trường hợp xóa thất bại
        String status = emailService.delete(emailId) == true ? "Xoa email thanh cong" : "Co loi xay ra khi xoa email";
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
