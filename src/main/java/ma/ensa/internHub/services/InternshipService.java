package ma.ensa.internHub.services;

public interface InternshipService {
    long countInternshipsByWorkMode(String workMode);
    long countAllInternships();
}