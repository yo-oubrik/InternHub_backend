package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;

public interface PendingUserService {
    public void processStudentEmailVerification(StudentRequest request);

    public void processCompanyEmailVerification(CompanyRequest request);

    boolean isVerificationInitiated(String email);
}
