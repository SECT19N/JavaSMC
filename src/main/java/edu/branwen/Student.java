package edu.branwen;

public class Student {
    private int Id, Semester;
    private double Score1, Score2, Score3, Score4, Score5;
    private String Name, Department, Gender, Phone, Email;

    /**
     * Constructor for class
     */
    public Student() { }

    public Student(int id, String name) {
        Id = id;
        Name = name;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-24s | %-7s | %-15s | %-11s | %-25s | %-7d | %-6.2f | %-6.2f | %-6.2f | %-6.2f | %-6.2f |",
                getId(),
                getName(),
                getDepartment(),
                getGender(),
                getPhone(),
                getEmail(),
                getSemester(),
                getScore1(),
                getScore2(),
                getScore3(),
                getScore4(),
                getScore5());
    }

    //<editor-fold desc="Setter Functions">

    public void setId(int id) {
        Id = id;
    }

    public void setSemester(int semester) {
        Semester = semester;
    }

    public void setScore1(double score1) {
        Score1 = score1;
    }

    public void setScore2(double score2) {
        Score2 = score2;
    }

    public void setScore3(double score3) {
        Score3 = score3;
    }

    public void setScore4(double score4) {
        Score4 = score4;
    }

    public void setScore5(double score5) {
        Score5 = score5;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    //</editor-fold>

    //<editor-fold desc="Getter Functions">

    public int getId() {
        return Id;
    }

    public int getSemester() {
        return Semester;
    }

    public double getScore1() {
        return Score1;
    }

    public double getScore2() {
        return Score2;
    }

    public double getScore3() {
        return Score3;
    }

    public double getScore4() {
        return Score4;
    }

    public double getScore5() {
        return Score5;
    }

    public String getName() {
        return Name;
    }

    public String getDepartment() {
        return Department;
    }

    public String getGender() {
        return Gender;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    //</editor-fold>

}