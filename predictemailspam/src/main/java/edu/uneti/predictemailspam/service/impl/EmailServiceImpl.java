package edu.uneti.predictemailspam.service.impl;

import edu.uneti.predictemailspam.algorithm.NaiveBayesFilter;
import edu.uneti.predictemailspam.algorithm.KNNFilter;
import edu.uneti.predictemailspam.algorithm.NaiveBayesFilter;
import edu.uneti.predictemailspam.model.entity.Email;
import edu.uneti.predictemailspam.repository.EmailRepository;
import edu.uneti.predictemailspam.service.EmailService;
import edu.uneti.predictemailspam.untils.ConverterString;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Class EmailServiceImpl để triển khai các phương thức trừu tượng để thêm, sửa, xóa email trong DB được triển khai từ interface EmailService
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    // Khai báo emailRepository để dùng các phương thức trong interface này để thao tác với database
    @Autowired
    private EmailRepository emailRepository;
    // Lấy ra giá trị trong file application.properties để xác định dùng thuật toán nào(KNN, Naive Bayes) để dự đoán nội dung email
    @Value("${use.algorithm}")
    private String algorithm;

    /**
     *  Phương thức này dùng để lưu lại một bản ghi email trong table Email trong Database
     * @param email
     * @return Email
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public Email sendEmail(Email email) throws IOException, ClassNotFoundException {
        //Check noi dung email va them co danh dau 1 email spam
        NaiveBayesFilter fcf = new NaiveBayesFilter();
        KNNFilter knn = new KNNFilter("src/main/resources/static/train/TrainingData.txt");
        boolean isSpam = false;
        if ("knn".equals(algorithm)) {
            isSpam = knn.predict(ConverterString.deAccent(email.getDescription()));
        } else {
            isSpam = fcf.isSpamEmail(email.getDescription());
        }
        // Lưu lại trạng thái là spam/non-spam sau khi sử dụng thuật toán KNN or Naive Bayes ở bước trên
        email.setIsSpam(isSpam);
        // Lưu lại nội dung email vào database thông qua phương thức save của interface emailRepository
        return emailRepository.save(email);
    }

    /**
     * Lay tat ca email trong table Email trong database
     * @return List<Email>
     */
    @Override
    public List<Email> findAllEmail() {
        // Sử dụng phương thức findAll của interface emailRepository để lấy tất cả email lưu trong bảng
        return emailRepository.findAll();
    }

    /**
     * Lay 1 email trong table Email thong qua emailId trong database
     * @return Email
     */
    @Override
    public Email findEmailById(Integer emailId) {
        // Sử dụng phương thức findById của interface emailRepository để lấy thông tin 1 email từ trong DB thông qua emailId
        return emailRepository.findById(emailId).get();
    }

    /**
     * Xóa 1 email thông qua emailId trong database
     * @return List<Email>
     */
    @Override
    public boolean delete(Integer emailId) {
        //Tìm 1 email theo ID(emailId) truyền vào
        Email email = emailRepository.findById(emailId).get();
        try {
            //Xóa email ở trong database vừa tìm được ở trên
            emailRepository.delete(email);
        } catch (Exception ex) {
            //Có lỗi gì đó trong quá trình xóa thì tra về false
            return false;
        }
        //Trả về true nếu xóa thành công
        return true;
    }
}