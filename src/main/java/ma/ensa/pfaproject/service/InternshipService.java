package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.dto.InternshipStatisticsDTO;
import ma.ensa.pfaproject.entities.Internship;

import java.util.List;

public interface InternshipService {
    List<Internship> getAllInternships();
    Internship getInternshipById(Long id);
    Internship saveInternship(Internship internship);
    void deleteInternship(Long id);

    InternshipStatisticsDTO getInternshipStatistics();
    int getTotalInternships();
    int getRemoteInternships();
    int getOnSiteInternships();
}
