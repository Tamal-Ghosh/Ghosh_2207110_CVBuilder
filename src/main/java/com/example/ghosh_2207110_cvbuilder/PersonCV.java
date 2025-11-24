package com.example.ghosh_2207110_cvbuilder;

public class PersonCV {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String education;
    private String skills;
    private String work;
    private String project;
    private String imagePath;

    public PersonCV(
            int id, String name, String email, String phone,
            String address, String education, String skills,
            String work, String project, String imagePath
    ) {
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

    public int getId() { return id; }
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
