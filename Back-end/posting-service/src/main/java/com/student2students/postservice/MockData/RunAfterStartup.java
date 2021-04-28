package com.student2students.postservice.MockData;

import com.student2students.postservice.repository.CommentRepository;
import com.student2students.postservice.repository.ExchangeRepository;
import com.student2students.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartup {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ExchangeRepository exchangeRepository;

    @Autowired
    public RunAfterStartup(PostRepository postRepository,
                           CommentRepository commentRepository,
                           ExchangeRepository exchangeRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @EventListener
    public void runAfterStartup(ApplicationStartedEvent event) {
        if(postRepository.count() == 0)
            insertPosts();
        if(commentRepository.count() == 0)
            insertComments();
        if(exchangeRepository.count() == 0)
            insertExchanges();
    }

    // TODO: Implement all these things after you add at least one more user in Zuul & Student2Students
    private void insertPosts() {
    }

    private void insertComments() {
    }

    private void insertExchanges() {
    }
}
