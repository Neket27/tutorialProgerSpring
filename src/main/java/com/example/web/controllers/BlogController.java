package com.example.web.controllers;

import com.example.web.models.Post;
import com.example.web.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public  String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        model.addAttribute("title"," Блог");
        return "blogMain";
    }

    @GetMapping("/blog/add")
    public  String blogAdd(Model model){
        model.addAttribute("title"," Добавление статьи");
        return "blogAdd";
    }

    @PostMapping("blog/add")
    public String blogPostAdd(@RequestParam String MyTitle,@RequestParam String MyAnons,@RequestParam String MyFullText, Model model){
        Post post =new Post(MyTitle,MyAnons,MyFullText);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String PostInId(@PathVariable(value="id")  long postId, Model model){ //@PathVariable() принимает динамический параметр
       Optional<Post> post = postRepository.findById(postId);
        Post postVallue2 = postRepository.findById(postId).orElseThrow();
        ArrayList<Post> onePost = new ArrayList<>();

        post.ifPresent(onePost::add);// преобразовывает из объекта Optional в список Post

        post.get().setCountView(onePost.get(0).getCountView()+1);
        postRepository.save(post.get());
       model.addAttribute("post",onePost);
       model.addAttribute("title","Пост");
        return "blogDetail";
    }

    @GetMapping("/blog/{id}/edit")
    public String PostInIdEdit(@PathVariable(value="id") long postId, Model model){ //@PathVariable() принимает динамический параметр
        Optional<Post> post = postRepository.findById(postId);
        ArrayList<Post> onePost = new ArrayList<>();
        post.ifPresent(onePost::add);// преобразовывает из объекта Optional в список Post
        model.addAttribute("post",onePost);
        model.addAttribute("title"," Редактирования");
        return "blogEdit";
    }
    @PostMapping("blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long post_id, @RequestParam String MyTitle,@RequestParam String MyAnons,@RequestParam String MyFullText, Model model){
        Post post = postRepository.findById(post_id).orElseThrow(); //orElseThrow(); бросает исключение если что то не так(без неё приётся создавать Optional<Post>)
        post.setTitle(MyTitle);
        post.setAnons(MyAnons);
        post.setFullText(MyFullText);
        postRepository.save(post);

        return "redirect:/blog";
    }


    @GetMapping("/blog/{id}/delete")
    public String PostInIdDelete(@PathVariable(value="id") long postId, Model model) { //@PathVariable() принимает динамический параметр
        Optional<Post> post = postRepository.findById(postId);
        ArrayList<Post> onePost = new ArrayList<>();
        post.ifPresent(onePost::add);// преобразовывает из объекта Optional в список Post
        model.addAttribute("post", onePost);
        model.addAttribute("title","Удаление поста");
        return "blogDelete";
    }

    @PostMapping("blog/{id}/delete")
    public String blogPostDelete(@PathVariable(value = "id") long post_id, Model model){
        Post post = postRepository.findById(post_id).orElseThrow(); //orElseThrow(); бросает исключение если что то не так(без неё приётся создавать Optional<Post>)
        postRepository.delete(post);

        return "redirect:/blog";
    }

}
