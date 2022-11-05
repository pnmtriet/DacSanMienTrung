package com.poly.helper;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MailerHelper {
    public String[] parseStringEmailToArray(String emailString) {
        String[] arrEmail = null;
        if (emailString.length() > 0) {
            emailString = removeSpace(emailString);
            arrEmail = emailString.split(",");
        }
        return arrEmail;
    }

    private String removeSpace(String string) {
        return string.replaceAll(" ", "");
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Tạo 1 thư mục tạm thời để chứa file, sau này có thể xóa thư mục tạm thời đó
        // đi
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }

    public String randomAlphaNumeric(int numberOfCharactor) {
        String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
        String alphaUpperCase = alpha.toUpperCase(); // A-Z
        String digits = "0123456789"; // 0-9
        String ALPHA_NUMERIC =alphaUpperCase + digits;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    private int randomNumber(int min, int max) {
        Random generator = new Random();
        return generator.nextInt((max - min) + 1) + min;
    }

    public String htmlMail(String taiKhoan,String matKhau){
        String htmlMail="<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n"
                + "  <tr>\r\n"
                + "    <td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0;\">\r\n"
                + "      <span style=\"font-size: 24px;\r\n"
                + "      color: #fe4c50;\r\n"
                + "      font-weight: 700;\r\n"
                + "      text-transform: uppercase;\">Đặc Sản </span><span style=\"font-size: 24px;\r\n"
                + "	color: #1e1e27;\r\n"
                + "	font-weight: 700;\r\n"
                + "	text-transform: uppercase;\r\n"
                + "  color: #fe4c50;\">Miền Trung</span>\r\n"
                + "     </td>\r\n"
                + "  </tr>\r\n"
                + "  <tr>\r\n"
                + "    <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\r\n"
                + "      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
                + "       <tr>\r\n"
                + "        <td>\r\n"
                + "         <h1 style=\"color: #f93030; font-size: 20px;\">Thông tin tài khoản của bạn</h1>\r\n"
                + "        </td>\r\n"
                + "       </tr>\r\n"
                + "       <tr>\r\n"
                + "        <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
                + "          <tr>\r\n"
                + "           <td style=\"padding: 10px;\" width=\"260\" valign=\"top\">\r\n"
                + "            <p>Tài khoản của bạn là: "+taiKhoan+"</p>\r\n"
                + "            <p>Mật khẩu của bạn là: "+matKhau+"</p>\r\n"
                + "           </td>\r\n"
                + "          </tr>\r\n"
                + "         </table>\r\n"
                + "       </tr>\r\n"
                + "      </table>\r\n"
                + "     </td>\r\n"
                + "  </tr>\r\n"
                + " </table>";
        return htmlMail;
    }

    public String htmlCode(String code){
        String htmlMail="<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n"
                + "  <tr>\r\n"
                + "    <td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0;\">\r\n"
                + "      <span style=\"font-size: 24px;\r\n"
                + "      color: #fe4c50;\r\n"
                + "      font-weight: 700;\r\n"
                + "      text-transform: uppercase;\">Đặc Sản </span><span style=\"font-size: 24px;\r\n"
                + "	color: #1e1e27;\r\n"
                + "	font-weight: 700;\r\n"
                + "	text-transform: uppercase;\r\n"
                + "  color: #fe4c50;\">Miền Trung</span>\r\n"
                + "     </td>\r\n"
                + "  </tr>\r\n"
                + "  <tr>\r\n"
                + "    <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\r\n"
                + "      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
                + "       <tr>\r\n"
                + "        <td>\r\n"
                + "         <h1 style=\"color: #f93030; font-size: 20px;\">Code verify</h1>\r\n"
                + "        </td>\r\n"
                + "       </tr>\r\n"
                + "       <tr>\r\n"
                + "        <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
                + "          <tr>\r\n"
                + "           <td style=\"padding: 10px;\" width=\"260\" valign=\"top\">\r\n"
                + "            <p>Mã xác thực tài khoản của bạn là:"+code+"</p>\r\n"
                + "           </td>\r\n"
                + "          </tr>\r\n"
                + "         </table>\r\n"
                + "       </tr>\r\n"
                + "      </table>\r\n"
                + "     </td>\r\n"
                + "  </tr>\r\n"
                + " </table>";
        return htmlMail;
    }
}
