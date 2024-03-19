package br.com.elotech.entrevista.util;

import java.util.regex.Pattern;

public class EmailValido {

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(email).matches();
    }
}
