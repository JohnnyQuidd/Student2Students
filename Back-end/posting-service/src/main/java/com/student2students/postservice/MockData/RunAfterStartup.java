package com.student2students.postservice.MockData;

import com.student2students.postservice.model.Major;
import com.student2students.postservice.model.Post;
import com.student2students.postservice.model.Topic;
import com.student2students.postservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAfterStartup {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ExchangeRepository exchangeRepository;
    private final MajorRepository majorRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public RunAfterStartup(PostRepository postRepository,
                           CommentRepository commentRepository,
                           ExchangeRepository exchangeRepository,
                           MajorRepository majorRepository,
                           TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.exchangeRepository = exchangeRepository;
        this.majorRepository = majorRepository;
        this.topicRepository = topicRepository;
    }

    @EventListener
    public void runAfterStartup(ApplicationStartedEvent event) {
        if(majorRepository.count() == 0)
            insertMajors();

        if(postRepository.count() == 0)
            insertPosts();
        if(commentRepository.count() == 0)
            insertComments();
        if(exchangeRepository.count() == 0)
            insertExchanges();
    }

    // TODO: Implement all these things after you add at least one more user in Zuul & Student2Students
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

    private void insertPosts() {
        Major csMajor = majorRepository.findByMajorName("Computer Science")
                .orElseThrow(() -> new IllegalStateException("Majors not loaded"));

        Topic kernel = topicRepository.findByTopicName("Kernel")
                .orElseThrow(() -> new IllegalStateException("Topics not loaded"));
        Topic c = topicRepository.findByTopicName("C")
                .orElseThrow(() -> new IllegalStateException("Topics not loaded"));

        Topic openSource = topicRepository.findByTopicName("Open Source")
                .orElseThrow(() -> new IllegalStateException("Topics not loaded"));

        Topic linux = topicRepository.findByTopicName("Linux")
                .orElseThrow(() -> new IllegalStateException("Topics not loaded"));

        Set<Topic> csTopics = new HashSet<>();
        csTopics.add(kernel);
        csTopics.add(c);
        csTopics.add(openSource);
        csTopics.add(linux);

        Post post1 = Post.builder()
                .studentUsername("linus69")
                .headline("Please review my Open Source software")
                .body("This is some small software I'm currently working on. I don't know " + "" +
                        "if it has some potential or not, but i hope you can review it and " +
                        "perhaps give me some feedback as what i can improve and in what direction " +"" +
                        "this project should be developing. How many of you work on " + "" +
                        "Open Source software? I would be really happy if you could " +
                        "join me on this journey!")
                .createdAt(LocalDateTime.now())
                .major(csMajor)
                .topics(csTopics)
                .build();

        postRepository.save(post1);
    }

    private void insertComments() {
    }

    private void insertExchanges() {
    }
}
