package com.example.ghosh_2207110_cvbuilder;

public class PersonCV {
    private final long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final String education;
    private final String skills;
    private final String work;
    private final String project;
    private final String imagePath;

    public PersonCV(long id, String name, String email, String phone, String address,
                    String education, String skills, String work, String project, String imagePath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.education = education;
        this.skills = skills;
        this.work = work;
        this.project = project;
        this.imagePath = imagePath;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEducation() { return education; }
    public String getSkills() { return skills; }
    public String getWork() { return work; }
    public String getProject() { return project; }
    public String getImagePath() { return imagePath; }
}