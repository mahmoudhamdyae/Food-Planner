package com.mahmoudhamdyae.foodplanner.utils;

import com.mahmoudhamdyae.foodplanner.R;

import java.util.regex.Pattern;

public class Validation {

    public boolean isValidUserName(String userName) {
        return !userName.isEmpty();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+");
        return !email.isEmpty() && emailRegex.matches(email);
    }
    
    public int passwordErrorMessage(String password) {
        int MIN_PASS_LENGTH = 6;
        String PASS_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
        if (password.isEmpty()) {
            return R.string.empty_password_error;
        } else if (password.length() < MIN_PASS_LENGTH) {
            return R.string.password_length_error;
        } else if (!Pattern.compile(PASS_PATTERN).matcher(password).matches()) {
            return R.string.password_error;
        } else {
            return -1;
        }
    }

    public boolean passwordMatches(String password, String repeated) {
        return password.equals(repeated);
    }
}
