package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.dto.InternshipStatisticsDTO;
import ma.ensa.pfaproject.entities.Internship;
import ma.ensa.pfaproject.entities.WorkMode;
import ma.ensa.pfaproject.repositories.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipServiceImpl implements InternshipService{

    private final InternshipRepository internshipRepository;
    private final CompanyService companyService;
    private final StudentService studentService;


    @Autowired
    public InternshipServiceImpl(InternshipRepository internshipRepository,
                                 CompanyService companyService,
                                 StudentService studentService) {
        this.internshipRepository = internshipRepository;
        this.companyService = companyService;
        this.studentService = studentService;
    }

    @Override
    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    @Override
    public Internship getInternshipById(Long id) {
        return internshipRepository.findById(id).orElse(null);
    }

    @Override
    public Internship saveInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    @Override
    public void deleteInternship(Long id) {
    internshipRepository.deleteById(id);
    }

    @Override
    public InternshipStatisticsDTO getInternshipStatistics() {
        int internshipListings = getTotalInternships();
        int remoteInternships = getRemoteInternships();
        int onSiteInternships = getOnSiteInternships();

        int verifiedCompanies = companyService.getVerifiedCompaniesCount();
        int studentApplicants = studentService.getStudentCount();

        return new InternshipStatisticsDTO(
                internshipListings,
                remoteInternships,
                onSiteInternships,
                verifiedCompanies,
                studentApplicants
        );
    }

    @Override
    public int getTotalInternships() {
        return (int)internshipRepository.count();
    }

    @Override
    public int getRemoteInternships() {
        return (int) internshipRepository.findAll().stream()
                .filter(i -> i.getWorkMode() == WorkMode.REMOTE)
                .count();
    }

    @Override
    public int getOnSiteInternships() {
        return (int) internshipRepository.findAll().stream()
                .filter(i -> i.getWorkMode() == WorkMode.ON_SITE)
                .count();
    }
}
