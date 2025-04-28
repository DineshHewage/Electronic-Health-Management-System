package com.sd.pms.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorUtil {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    //criteria of having a minimum of 8 characters and a combination of letters and numbers

    public static boolean isValidPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
