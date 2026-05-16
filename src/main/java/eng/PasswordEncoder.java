package eng;

public class PasswordEncoder {
    public PasswordEncoder(){}

    public String hashPassword(String password) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : password.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                // Shift di 1 per minuscole
                encrypted.append((char) ((c - 'a' + 1) % 26 + 'a'));
            } else if (c >= 'A' && c <= 'Z') {
                // Shift di 1 per maiuscole
                encrypted.append((char) ((c - 'A' + 1) % 26 + 'A'));
            } else {
                // Numeri e caratteri speciali rimangono uguali
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

    public boolean verifyPassword(String password, String hash) {
        String computedHash = hashPassword(password);
        return computedHash.equals(hash);
    }
}
