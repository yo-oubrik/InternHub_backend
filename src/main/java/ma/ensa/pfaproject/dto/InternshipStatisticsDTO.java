package ma.ensa.pfaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class InternshipStatisticsDTO {

    private int internshipListings;
    private int remoteInternships;
    private int onSiteInternships;
    private int verifiedCompanies;
    private int studentApplicants;

}
