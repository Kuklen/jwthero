package com.example.jwthero.services;

import com.example.jwthero.entities.Post;
import com.example.jwthero.entities.User;
import com.example.jwthero.repos.PostRepository;
import com.example.jwthero.requests.PostCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository,UserService userService) {
        this.postRepository = postRepository;
        this.userService=userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if(userId.isPresent())
            return postRepository.findByUserId(userId.get());
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user= userService.getOneUser(newPostRequest.getUserId());
        if(user==null)
            return null;
        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);

    }
}