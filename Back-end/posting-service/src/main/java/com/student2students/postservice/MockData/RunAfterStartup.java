package com.student2students.postservice.MockData;

import com.student2students.postservice.model.*;
import com.student2students.postservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
                .authorEmail("torvaldslinusrs@gmail.com")
                .headline("Please review my Open Source software")
                .body("This is some small software I'm currently working on. I don't know " + "" +
                        "if it has some potential or not, but i hope you can review it and " +
                        "perhaps give me some feedback as what i can improve and in what direction " +"" +
                        "this project should be developing. How many of you work on " + "" +
                        "Open Source software? I would be really happy if you could " +
                        "join me on this journey! \uD83D\uDE00")
                .createdAt(LocalDateTime.now())
                .major(csMajor)
                .topics(csTopics)
                .build();

        Major medMajor = majorRepository.findByMajorName("Medicine")
                .orElseThrow(() -> new IllegalStateException("Majors not loaded"));
        Topic immunology = topicRepository.findByTopicName("Immunology")
                .orElseThrow(() -> new IllegalStateException("Topics not loaded"));
        Set<Topic> topics = new HashSet<>();
        topics.add(immunology);

        Post post2 = Post.builder()
                .studentUsername("ivan91")
                .authorEmail("student@gmail.com")
                .headline("Any tips on how to avoid virus infections")
                .body("Hey! I have one question for those in Immunology major. How should we prevent " +
                        " getting sick \ud83d\ude01. Any advice would be helpful.")
                .createdAt(LocalDateTime.now())
                .major(medMajor)
                .topics(topics)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
    }
    private void insertComments() {
        Post post = postRepository.findByHeadline("Any tips on how to avoid virus infections")
                .orElseThrow(() -> new IllegalStateException("Posts not loaded"));
        Comment comment1 = Comment.builder()
                .username("linus69")
                .body("Try to avoid mass gatherings, especially indoors \uD83D\uDE37 . Wear mask and eat healthy, " +
                        "those are some basic rules. I hope this was a bit helpful. Stay safe \uD83D\uDC4D")
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment1);
    }

    private void insertExchanges() {
        Exchange exchange = Exchange.builder()
                .studentUsername("linus69")
                .country("Finland")
                .university("Computer Science - University Of Helsinki")
                .minNumberOfAttendees(5)
                .maxNumberOfAttendees(45)
                .exchangeStart(LocalDate.now().plusMonths(2))
                .exchangeEnding(LocalDate.now().plusMonths(3).plusDays(15))
                .createdAt(LocalDateTime.now())
                .price(1250)
                .body("If anyone is interested in an exchange in Finland" + "" +
                        " contact me via email and let's arrange something. "+
                        "You will be learning a ton of new stuff while having fun. " + "" +
                        "What's not to like? \uD83C\uDDEB\uD83C\uDDEE \uD83D\uDE0A")
                .build();

        exchangeRepository.save(exchange);
    }
}
