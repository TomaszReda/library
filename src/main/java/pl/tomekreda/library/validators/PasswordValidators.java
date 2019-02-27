package pl.tomekreda.library.validators;

public final class PasswordValidators {

    private PasswordValidators() {
        // not called
    }

    public static boolean valid(String password) {

        int small = 0;
        int big = 0;
        int dignital = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                big++;
            }
            if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                small++;
            }
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                dignital++;
            }

        }
        return big >= 1 && small >= 1 && dignital >= 1;
    }


}
