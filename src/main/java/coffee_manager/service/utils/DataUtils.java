package coffee_manager.service.utils;

import java.util.Random;

public class DataUtils {

    public static String generatePwd(int length) {
        String numbers = "0123456789";
        char otp[] = new char[length];
        Random getOtpNum = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(getOtpNum.nextInt(numbers.length()));
        }
        String otpCode = new String(otp);
        return otpCode;
    }


}
