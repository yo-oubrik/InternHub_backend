package ma.ensa.internHub.domain.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailWithAttachmentsRequest {
    @Email(message = "Invalid email format")
    String to;
    @NotNull(message = "Subject cannot be null")
    String subject;
    @NotNull(message = "Recipient name cannot be null")
    String recepientName;
    @NotNull(message = "HTML body cannot be null")
    String htmlBody;
    MultipartFile[] attachments;
}