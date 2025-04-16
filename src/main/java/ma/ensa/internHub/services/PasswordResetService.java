package ma.ensa.internHub.services;

public interface PasswordResetService {
    void generateAndSendResetToken(String email);
    void resetPassword(String token, String newPassword);
}