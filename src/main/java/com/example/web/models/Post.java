package com.example.web.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // аннотациа, что поля будут в базе данных
public class Post {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO) //генерация случайного id
    private Long id;
    private String title, anons, fullText;
    private int countView;
    public Post(){}

    public Post(String myTitle, String myAnons, String myFullText) {
        this.title=myTitle;
        this.anons=myAnons;
        this.fullText =myFullText;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAnons() {return anons;}
    public void setAnons(String anons) {this.anons = anons;}
    public String getFullText() {return fullText;}
    public void setFullText(String fullText) {this.fullText = fullText;}
    public int getCountView() {return countView;}
    public void setCountView(int countView) {this.countView = countView;}
}
