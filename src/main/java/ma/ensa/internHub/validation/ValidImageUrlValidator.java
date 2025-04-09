package ma.ensa.internHub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class ValidImageUrlValidator implements ConstraintValidator<ValidImageUrl, String> {
    private String[] allowedExtensions;
    // private long maxFileSize;

    @Override
    public void initialize(ValidImageUrl constraintAnnotation) {
        this.allowedExtensions = constraintAnnotation.allowedExtensions();
        // this.maxFileSize = constraintAnnotation.maxFileSize();
    }

    @Override
    public boolean isValid(String urlString, ConstraintValidatorContext context) {
        if (urlString == null || urlString.isEmpty()) {
            return true;
        }
        try {
            URL url = URI.create(urlString).toURL();
            String lowercaseUrl = urlString.toLowerCase();

            boolean hasValidExtension = Arrays.stream(allowedExtensions)
                    .anyMatch(ext -> lowercaseUrl.endsWith(ext.toLowerCase()));

            if (!hasValidExtension) {
                return false;
            }

            URLConnection conn = url.openConnection();
            String contentType = conn.getContentType();
            long contentLength = conn.getContentLengthLong();

            return contentType != null &&
                    contentType.startsWith("image/") &&
                    contentLength > 0;

        } catch (Exception e) {
            return false;
        }
    }
}