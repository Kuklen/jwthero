package com.example.jwthero.services;

import com.example.jwthero.entities.Comment;
import com.example.jwthero.entities.Post;
import com.example.jwthero.entities.User;
import com.example.jwthero.repos.CommentRepository;
import com.example.jwthero.requests.CommentCreateRequest;
import com.example.jwthero.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository,UserService userService,PostService postService) {
        this.commentRepository = commentRepository;
        this.userService= userService;
        this.postService=postService;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if(userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        }
        else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }
        else
            return commentRepository.findAll();

    }
    public Comment getOneCommentById(Long commentId) {
        return commentRepository.getOneCommentById(commentId);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getUserId());
        if (user!= null && post !=null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            return commentRepository.save(commentToSave);
        }else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(request.getText());
            return commentRepository.save(commentToUpdate);
        }else
            return null;

    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById((commentId));
    }
}
