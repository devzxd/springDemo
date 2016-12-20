package com.demo.bean;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by CJL on 2016/12/15 0015.
 */
@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", nullable = false, length = 32, unique = true)
    private String id;

    @Column
    private  String name;

    @Column
    private String age;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
