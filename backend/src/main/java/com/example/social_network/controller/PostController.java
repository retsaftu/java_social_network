package com.example.social_network.controller;

import com.example.social_network.models.Post;
import com.example.social_network.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
// @CrossOrigin
// @CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostController {

    @Autowired
    PostRepository postRepository;

    // @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost(@RequestParam(required = false) String title) {
        try {
            List<Post> posts = new ArrayList<Post>();

            // if (title == null)
            postRepository.findAll().forEach(posts::add);
            // else
            // contestRepository.findByTitleContaining(title).forEach(contests::add);
            System.out.println(posts);
            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //
//    @GetMapping("/contests/{id}/tasks")
//    public ResponseEntity<Contest> getContestById(@PathVariable("id") String id) {
//        Optional<Contest> contestData = contestRepository.findById(id);
//
//        if (contestData.isPresent()) {
//            return new ResponseEntity<>(contestData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

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
//    @PostMapping("/contests")
//    public ResponseEntity<Contest> createContest(@RequestBody Contest contest) {
//        try {
//            Contest _contest = contestRepository
//                    .save(new Contest(contest.getName(), contest.getDescription(), contest.getTasks()));
//            return new ResponseEntity<>(_contest, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
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
