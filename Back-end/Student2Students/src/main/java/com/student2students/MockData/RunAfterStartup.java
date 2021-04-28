package com.student2students.MockData;


import com.student2students.model.*;
import com.student2students.repository.*;
import com.student2students.security.ApplicationUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAfterStartup {
    private Logger logger = LoggerFactory.getLogger(RunAfterStartup.class);
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final MajorRepository majorRepository;
    private final UniversityRepository universityRepository;

    @Autowired
    public RunAfterStartup(AdminRepository adminRepo,
                           StudentRepository studentRepo,
                           CountryRepository countryRepo,
                           LanguageRepository langRepo,
                           MajorRepository majorRepo,
                           UniversityRepository uniRepo) {
        this.adminRepository = adminRepo;
        this.studentRepository = studentRepo;
        this.countryRepository = countryRepo;
        this.languageRepository = langRepo;
        this.majorRepository = majorRepo;
        this.universityRepository = uniRepo;
    }

    @EventListener
    public void runAfterStartup(ApplicationStartedEvent event) {
        if(countryRepository.count() == 0)
            insertCountries();

        if(languageRepository.count() == 0)
            insertLanguages();

        if(majorRepository.count() == 0)
            insertMajors();

        if(universityRepository.count() == 0)
            insertUniversity();

        if(adminRepository.count() == 0)
            insertAdmin();

        if(studentRepository.count() == 0)
            insertStudents();



    }

    private void insertCountries() {
        Country serbia = Country.builder()
                .country("Serbia")
                .build();
        Country croatia = Country.builder()
                .country("Croatia")
                .build();
        Country italy = Country.builder()
                .country("Italy")
                .build();
        Country hungary = Country.builder()
                .country("Hungary")
                .build();
        Country norway = Country.builder()
                .country("Norway")
                .build();
        Country denmark = Country.builder()
                .country("Denmark")
                .build();
        Country austria = Country.builder()
                .country("Austria")
                .build();
        Country france = Country.builder()
                .country("France")
                .build();
        Country germany = Country.builder()
                .country("Germany")
                .build();
        Country spain = Country.builder()
                .country("Spain")
                .build();
        Country poland = Country.builder()
                .country("Poland")
                .build();
        Country bulgaria = Country.builder()
                .country("Bulgaria")
                .build();
        Country romania = Country.builder()
                .country("Romania")
                .build();
        Country montenegro = Country.builder()
                .country("Montenegro")
                .build();
        Country russia = Country.builder()
                .country("Russia")
                .build();
        Country ukraine = Country.builder()
                .country("Ukraine")
                .build();
        Country israel = Country.builder()
                .country("Israel")
                .build();
        Country turkey = Country.builder()
                .country("Turkey")
                .build();
        Country finland = Country.builder()
                .country("Finland")
                .build();

        countryRepository.save(serbia);
        countryRepository.save(italy);
        countryRepository.save(hungary);
        countryRepository.save(croatia);
        countryRepository.save(norway);
        countryRepository.save(denmark);
        countryRepository.save(austria);
        countryRepository.save(france);
        countryRepository.save(germany);
        countryRepository.save(spain);
        countryRepository.save(poland);
        countryRepository.save(bulgaria);
        countryRepository.save(romania);
        countryRepository.save(montenegro);
        countryRepository.save(russia);
        countryRepository.save(ukraine);
        countryRepository.save(israel);
        countryRepository.save(turkey);
        countryRepository.save(finland);
    }

    private void insertLanguages() {
        Language serbian = Language.builder()
                .languageName("Serbian")
                .languageCode("sr")
                .build();
        Language italian = Language.builder()
                .languageName("Italian")
                .languageCode("it")
                .build();
        Language hungarian = Language.builder()
                .languageName("Hungarian")
                .languageCode("hu")
                .build();
        Language croatian = Language.builder()
                .languageName("Croatian")
                .languageCode("hr")
                .build();
        Language norwegian = Language.builder()
                .languageName("Norwegian")
                .languageCode("nn")
                .build();
        Language danish = Language.builder()
                .languageName("Danish")
                .languageCode("da")
                .build();
        Language german = Language.builder()
                .languageName("German")
                .languageCode("de")
                .build();
        Language french = Language.builder()
                .languageName("French")
                .languageCode("fr")
                .build();
        Language spanish = Language.builder()
                .languageName("Spanish")
                .languageCode("es")
                .build();
        Language polish = Language.builder()
                .languageName("Polish")
                .languageCode("pl")
                .build();
        Language bulgarian = Language.builder()
                .languageName("Bulgarian")
                .languageCode("bg")
                .build();
        Language romanian = Language.builder()
                .languageName("Romanian")
                .languageCode("ro")
                .build();
        Language russian = Language.builder()
                .languageName("Russian")
                .languageCode("ru")
                .build();
        Language ukrainian = Language.builder()
                .languageName("Ukrainian")
                .languageCode("uk")
                .build();
        Language hebrew = Language.builder()
                .languageName("Hebrew")
                .languageCode("he")
                .build();
        Language turkish = Language.builder()
                .languageName("Turkish")
                .languageCode("tr")
                .build();

        Language finnish = Language.builder()
                .languageName("Finnish")
                .languageCode("fi")
                .build();

        languageRepository.save(serbian);
        languageRepository.save(italian);
        languageRepository.save(hungarian);
        languageRepository.save(croatian);
        languageRepository.save(norwegian);
        languageRepository.save(danish);
        languageRepository.save(german);
        languageRepository.save(french);
        languageRepository.save(spanish);
        languageRepository.save(polish);
        languageRepository.save(bulgarian);
        languageRepository.save(romanian);
        languageRepository.save(russian);
        languageRepository.save(ukrainian);
        languageRepository.save(hebrew);
        languageRepository.save(turkish);
        languageRepository.save(finnish);
    }

    private void insertMajors() {
        Major csMajor = Major.builder()
                .majorName("Computer Science")
                .build();

        Topic frontEnd = Topic.builder()
                .topicName("Front end")
                .major(csMajor)
                .build();
        Topic backEnd = Topic.builder()
                .topicName("Back end")
                .major(csMajor)
                .build();
        Topic devops = Topic.builder()
                .topicName("Devops")
                .major(csMajor)
                .build();
        Topic cSharp = Topic.builder()
                .topicName("C#")
                .major(csMajor)
                .build();
        Topic java = Topic.builder()
                .topicName("Java")
                .major(csMajor)
                .build();
        Topic javaScript = Topic.builder()
                .topicName("JavaScript")
                .major(csMajor)
                .build();
        Topic react = Topic.builder()
                .topicName("React")
                .major(csMajor)
                .build();
        Topic spring = Topic.builder()
                .topicName("Spring Boot")
                .major(csMajor)
                .build();
        Topic nodeJS = Topic.builder()
                .topicName("NodeJS")
                .major(csMajor)
                .build();
        Topic docker = Topic.builder()
                .topicName("Docker")
                .major(csMajor)
                .build();
        Topic linux = Topic.builder()
                .topicName("Linux")
                .major(csMajor)
                .build();
        Topic kernel = Topic.builder()
                .topicName("Kernel")
                .major(csMajor)
                .build();
        Topic operatingSystem = Topic.builder()
                .topicName("Operating System")
                .major(csMajor)
                .build();
        Topic openSource = Topic.builder()
                .topicName("Open Source")
                .major(csMajor)
                .build();
        Topic c = Topic.builder()
                .topicName("C")
                .major(csMajor)
                .build();

        Set<Topic> csTopics = new HashSet<>();
        csTopics.add(docker);
        csTopics.add(nodeJS);
        csTopics.add(spring);
        csTopics.add(react);
        csTopics.add(javaScript);
        csTopics.add(java);
        csTopics.add(cSharp);
        csTopics.add(devops);
        csTopics.add(backEnd);
        csTopics.add(frontEnd);
        csTopics.add(linux);
        csTopics.add(kernel);
        csTopics.add(operatingSystem);
        csTopics.add(openSource);
        csTopics.add(c);

        csMajor.setTopics(csTopics);

        Major medicineMajor = Major.builder()
                .majorName("Medicine")
                .build();

        Topic allergy = Topic.builder()
                .topicName("Allergy")
                .major(medicineMajor)
                .build();
        Topic immunology = Topic.builder()
                .topicName("Immunology")
                .major(medicineMajor)
                .build();
        Topic anesthesiology = Topic.builder()
                .topicName("Anesthesiology")
                .major(medicineMajor)
                .build();
        Topic dermatology = Topic.builder()
                .topicName("Dermatology")
                .major(medicineMajor)
                .build();
        Topic internalMedicine = Topic.builder()
                .topicName("Internal Medicine")
                .major(medicineMajor)
                .build();
        Topic virology = Topic.builder()
                .topicName("Virology")
                .major(medicineMajor)
                .build();


        Set<Topic> medicineTopics = new HashSet<>();
        medicineTopics.add(allergy);
        medicineTopics.add(immunology);
        medicineTopics.add(anesthesiology);
        medicineTopics.add(dermatology);
        medicineTopics.add(internalMedicine);
        medicineTopics.add(virology);

        medicineMajor.setTopics(medicineTopics);

        Major lawMajor = Major.builder()
                .majorName("Law")
                .build();

        Topic civilLaw = Topic.builder()
                .topicName("Civil Law")
                .major(lawMajor)
                .build();
        Topic criminalLaw = Topic.builder()
                .topicName("Criminal Law")
                .major(lawMajor)
                .build();
        Topic internationalLaw = Topic.builder()
                .topicName("International Law")
                .major(lawMajor)
                .build();
        Topic familyLaw = Topic.builder()
                .topicName("Family Law")
                .major(lawMajor)
                .build();

        Set<Topic> lawTopics = new HashSet<>();
        lawTopics.add(civilLaw);
        lawTopics.add(criminalLaw);
        lawTopics.add(internationalLaw);
        lawTopics.add(familyLaw);

        lawMajor.setTopics(lawTopics);

        Major economyMajor = Major.builder()
                .majorName("Economy")
                .build();

        Topic finance = Topic.builder()
                .topicName("Finance")
                .major(economyMajor)
                .build();

        Topic cryptocurrency = Topic.builder()
                .topicName("Cryptocurrency")
                .major(economyMajor)

                .build();

        Topic laborEconomics = Topic.builder()
                .topicName("Labor economics")
                .major(economyMajor)
                .build();

        Set<Topic> economyTopics = new HashSet<>();
        economyTopics.add(finance);
        economyTopics.add(cryptocurrency);
        economyTopics.add(laborEconomics);

        economyMajor.setTopics(economyTopics);

        majorRepository.save(csMajor);
        majorRepository.save(medicineMajor);
        majorRepository.save(lawMajor);
        majorRepository.save(economyMajor);

    }

    private void insertUniversity() {
        Country serbia = countryRepository.findByCountry("Serbia")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Country croatia = countryRepository.findByCountry("Croatia")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Country finland = countryRepository.findByCountry("Finland")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Address address1 = Address.builder()
                .city("Novi Sad")
                .country(serbia)
                .streetNumber("6")
                .streetName("Trg Dositeja Obradovica")
                .build();

        University ftn = University.builder()
                .universityName("Faculty of technical sciences")
                .universityEmail("ftn021@uns.ac.rs")
                .universityAddress(address1)
                .build();

        Address address2 = Address.builder()
                .city("Beograd")
                .country(serbia)
                .streetNumber("73")
                .streetName("Bulevar kralja Aleksandra")
                .build();

        University etf = University.builder()
                .universityName("Electrical Engineering")
                .universityEmail("etf011@etf.bg.rs")
                .universityAddress(address2)
                .build();

        Address address3 = Address.builder()
                .city("Split")
                .country(croatia)
                .streetNumber("23")
                .streetName("Velebitska")
                .build();

        University med = University.builder()
                .universityName("Faculty of Medicine")
                .universityEmail("med@uni.cro")
                .universityAddress(address3)
                .build();

        Address address4 = Address.builder()
                .city("Helsinki")
                .country(finland)
                .streetNumber("125")
                .streetName("Aleksanterinkatu")
                .build();

        University csHelsinki = University.builder()
                .universityName("Computer Science - University Of Helsinki")
                .universityEmail("helsinki011@uni.fi")
                .universityAddress(address4)
                .build();


        universityRepository.save(ftn);
        universityRepository.save(etf);
        universityRepository.save(med);
        universityRepository.save(csHelsinki);
    }

    private void insertAdmin() {
        Country italy = countryRepository.findByCountry("Italy")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Language italian = languageRepository.findByLanguageName("Italian")
                .orElseThrow(() -> new IllegalStateException("Languages not loaded"));

        Address address = Address.builder()
                .streetName("Via Condotti")
                .streetNumber("12C")
                .city("Rome")
                .country(italy)
                .build();

        Admin admin = Admin.builder()
                .email("lazar@gmail.com")
                .username("admin")
                .password("$2y$10$R0sLZ23c4WBaYSEjAtUOnunPdKB.F3dKW6D1gyHd/ZARx/38FZ9.y")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.ADMIN)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .biography("I am admin of Student2Students platform for a very long time.")
                .address(address)
                .language(italian)
                .build();
        adminRepository.save(admin);
    }

    private void insertStudents() {
        Country croatia = countryRepository.findByCountry("Croatia")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Country finland = countryRepository.findByCountry("Finland")
                .orElseThrow(() -> new IllegalStateException("Countries not loaded"));

        Language croatian = languageRepository.findByLanguageName("Croatian")
                .orElseThrow(() -> new IllegalStateException("Languages not loaded"));

        Language finnish = languageRepository.findByLanguageName("Finnish")
                .orElseThrow(() -> new IllegalStateException("Languages not loaded"));

        Major major = majorRepository.findByMajorName("Medicine")
                .orElseThrow(() -> new IllegalStateException("Majors not loaded"));

        Major major2 = majorRepository.findByMajorName("Computer Science")
                .orElseThrow(() -> new IllegalStateException("Majors not loaded"));

        Address address = Address.builder()
                .streetName("Vukovarska")
                .streetNumber("5A")
                .city("Split")
                .country(croatia)
                .build();


        Address address2 = Address.builder()
                .streetName("Aleksanterinkatu")
                .streetNumber("23")
                .city("Helsinki")
                .country(finland)
                .build();

        Student ivan91 = Student.builder()
                .email("student@gmail.com")
                .firstName("Ivan")
                .lastName("Ivanic")
                .username("ivan91")
                .password("$2y$10$43l3Zf24uUonu6UpbpxtJOPOmaHblcJysD3EMq.l5GPC6dO8tj3o6")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .address(address)
                .language(croatian)
                .major(major)
                .biography("Student of medicine currently located in Split, Croatia. " +
                        "I'm looking for students in same major around the world so we can share knowledge "+
                        "and perhaps arrange student exchange. If you find this idea interesting," +
                        " contact me via my email address and let's talk! " +
                        " I'm also interesting in number of other topics such as Computer Science and Economy." +
                        " If you have any questions please let me know!")
                .build();

        Student linus69 = Student.builder()
                .email("torvaldslinusrs@gmail.com")
                .firstName("Linus")
                .lastName("Torvalds")
                .username("linus69")
                .password("$2y$10$ADjXMrTYzCHaK8iy5SZxhuAqJwSH/N2rr4NM7XGBnrS2LAl0clFk.")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .address(address2)
                .language(finnish)
                .major(major2)
                .biography("Student of Computer science in Helsinki, Finland. " +
                        " I have passion for making open source software and I'm currently working on " +
                        "a small project called Linux. It won't be a big of a deal. " +
                        " I'm looking for students in same major around the world so we can share knowledge "+
                        "and perhaps arrange student exchange. United States, preferred. " +
                        " If you find this idea interesting," +
                        " contact me via my email address and let's talk! " +
                        " I'm also interesting in number of other topics such as Computer Science and Economy." +
                        " If you have any questions please let me know!")
                .build();

        studentRepository.save(ivan91);
        studentRepository.save(linus69);
    }
}
