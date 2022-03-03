package com.example.social_network.controller;

import com.example.social_network.models.Comment;
import com.example.social_network.models.Post;
import com.example.social_network.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
// @CrossOrigin
// @CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostController {
    MyLogger<PostController> myLogger = new MyLogger<>(PostController.class);

    @Autowired
    PostRepository postRepository;

    // @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost(@RequestParam(required = false) String title) throws IOException {
        try {
            List<Post> posts = new ArrayList<Post>();

            // if (title == null)
            postRepository.findAll().forEach(posts::add);
            // else
            // contestRepository.findByTitleContaining(title).forEach(contests::add);
            System.out.println(posts);
//            myLogger.myLog(PostController.toString(), "INFO");
            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) throws IOException {
        try {
            Post _post = postRepository
                    .save(new Post(post.getName(), post.getUsername(), post.getUserId(), post.getDescription(),
                            post.getLike(), post.getVisible(), post.getComments()));
            return new ResponseEntity<>(_post, HttpStatus.CREATED);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<Post> createComment(@PathVariable("postId") String postId, @RequestBody Comment comment) throws IOException {
        try {
            System.out.println(comment.getContent());
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                post.addComment(comment);
                postRepository.save(post);
            }
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //
    // @GetMapping("/contests/{id}/tasks")
    // public ResponseEntity<Contest> getContestById(@PathVariable("id") String id)
    // {
    // Optional<Contest> contestData = contestRepository.findById(id);
    //
    // if (contestData.isPresent()) {
    // return new ResponseEntity<>(contestData.get(), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    // @GetMapping("/contests/{id}/tasks")
    // public ResponseEntity<Contest> getContestById(@PathVariable("id") String id)
    // {
    // Optional<Contest> contestData = contestRepository.findById(id);
    //
    // if (contestData.isPresent()) {
    // return new ResponseEntity<>(contestData.get(), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }
    //

    //
    // @PutMapping("/contests/{id}")
    // public ResponseEntity<Contest> updateContest(@PathVariable("id") String id,
    // @RequestBody Contest contest) {
    // Optional<Contest> contestData = contestRepository.findById(id);
    //
    // if (contestData.isPresent()) {
    // Contest _contest = contestData.get();
    // _contest.setTitle(contest.getTitle());
    // _contest.setDescription(contest.getDescription());
    // _contest.setPublished(contest.isPublished());
    // return new ResponseEntity<>(contestRepository.save(_contest), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }
    //
    // @DeleteMapping("/contests/{id}")
    // public ResponseEntity<HttpStatus> deleteContest(@PathVariable("id") String
    // id) {
    // try {
    // contestRepository.deleteById(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
    //
    // @DeleteMapping("/contests")
    // public ResponseEntity<HttpStatus> deleteAllContests() {
    // try {
    // contestRepository.deleteAll();
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
    //
    // @GetMapping("/contests/published")
    // public ResponseEntity<List<Contest>> findByPublished() {
    // try {
    // List<Contest> contests = contestRepository.findByPublished(true);
    //
    // if (contests.isEmpty()) {
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
    // return new ResponseEntity<>(contests, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
}
