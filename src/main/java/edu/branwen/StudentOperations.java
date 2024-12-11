package edu.branwen;

import java.sql.*;
import java.util.ArrayList;

public class StudentOperations {
    static String DatabaseLocation = "jdbc:sqlite:src/db/SMCLite.db";

    /**
     * Gets all students in the SQLite database. Option 1
     * @return An ArrayList that has all the students in the database.
     */
    public static ArrayList<Student> ViewStudents() {
        Connection viewConnection = null;
        PreparedStatement viewStatement = null;
        ResultSet viewResult = null;

        ArrayList<Student> viewStudentsList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC"); //Java Database Controller
            viewConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "SELECT stu.Id, stu.Name, stu.Department, stu.Gender, stu.Phone, stu.Email, stu.Semester, scores.score1, scores.score2, scores.score3, scores.score4, scores.score5 FROM Students stu INNER JOIN SubjectScores scores ON stu.Id = scores.StudentId WHERE stu.Semester = scores.Semester ORDER BY stu.Department";

            viewStatement = viewConnection.prepareStatement(query);
            viewResult = viewStatement.executeQuery();

            while (viewResult.next()) {
                Student _myStudent = new Student(viewResult.getInt("Id"), viewResult.getString("Name"));

                _myStudent.setDepartment(viewResult.getString("Department"));
                _myStudent.setGender(viewResult.getString("Gender"));
                _myStudent.setPhone(viewResult.getString("Phone"));
                _myStudent.setEmail(viewResult.getString("Email"));
                _myStudent.setSemester(viewResult.getInt("Semester"));
                _myStudent.setScore1(viewResult.getDouble("Score1"));
                _myStudent.setScore2(viewResult.getDouble("Score2"));
                _myStudent.setScore3(viewResult.getDouble("Score3"));
                _myStudent.setScore4(viewResult.getDouble("Score4"));
                _myStudent.setScore5(viewResult.getDouble("Score5"));

                viewStudentsList.add(_myStudent);
            }
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (viewResult != null) {
                    viewResult.close();
                }
                if (viewStatement != null) {
                    viewStatement.close();
                }
                if (viewConnection != null) {
                    viewConnection.close();
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }

        return viewStudentsList;
    }

    /**
     * Search for students based on the provided arguments and returns their information. Option 2
     * @param studentNameOrId Search Query.
     * @return ArrayList with students that match the query.
     */
    public static ArrayList<Student> SearchStudents(String studentNameOrId) {
        Connection searchConnection = null;
        PreparedStatement searchStatement = null;
        ResultSet searchResult = null;

        ArrayList<Student> searchStudentList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            searchConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "SELECT stu.Id, stu.Name, stu.Department, stu.Gender, stu.Phone, stu.Email, stu.Semester, scores.score1, scores.score2, scores.score3, scores.score4, scores.score5 FROM Students stu INNER JOIN SubjectScores scores ON stu.Id = scores.StudentId WHERE stu.Semester = scores.Semester AND (stu.Name LIKE '%' || ? || '%' OR stu.Id LIKE '%' || ? || '%') ORDER BY stu.Department";

            searchStatement = searchConnection.prepareStatement(query);
            searchStatement.setString(1, studentNameOrId);
            searchStatement.setString(2, studentNameOrId);

            searchResult = searchStatement.executeQuery();

            while (searchResult.next()) {
                Student _myStudent = new Student(searchResult.getInt("Id"), searchResult.getString("Name"));

                _myStudent.setDepartment(searchResult.getString("Department"));
                _myStudent.setGender(searchResult.getString("Gender"));
                _myStudent.setPhone(searchResult.getString("Phone"));
                _myStudent.setEmail(searchResult.getString("Email"));
                _myStudent.setSemester(searchResult.getInt("Semester"));
                _myStudent.setScore1(searchResult.getDouble("Score1"));
                _myStudent.setScore2(searchResult.getDouble("Score2"));
                _myStudent.setScore3(searchResult.getDouble("Score3"));
                _myStudent.setScore4(searchResult.getDouble("Score4"));
                _myStudent.setScore5(searchResult.getDouble("Score5"));

                searchStudentList.add(_myStudent);
            }
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (searchResult != null) {
                    searchResult.close();
                }
                if (searchStatement != null) {
                    searchStatement.close();
                }
                if (searchConnection != null) {
                    searchConnection.close();
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }

        return searchStudentList;
    }

    /**
     * Insert the students scores in a separate SQLite table called SubjectScores.
     * @param studentId ID of the student
     * @param semester Student Semester
     * @param s1 First Subject
     * @param s2 Second Subject
     * @param s3 Third Subject
     * @param s4 Fourth Subject
     * @param s5 Fifth Subject
     */
    public static void InsertScores(String studentId, int semester, double s1, double s2, double s3, double s4, double s5) {
        Connection insertScoresConnection = null;
        PreparedStatement insertScoresStatement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            insertScoresConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "INSERT INTO SubjectScores (StudentId, Semester, Score1, Score2, Score3, Score4, Score5) VALUES (?, ?, ?, ?, ?, ?, ?)";

            insertScoresStatement = insertScoresConnection.prepareStatement(query);

            insertScoresStatement.setString(1, studentId);
            insertScoresStatement.setInt(2, semester);
            insertScoresStatement.setDouble(3, s1);
            insertScoresStatement.setDouble(4, s2);
            insertScoresStatement.setDouble(5, s3);
            insertScoresStatement.setDouble(6, s4);
            insertScoresStatement.setDouble(7, s5);

            insertScoresStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (insertScoresStatement != null) {
                    insertScoresStatement.close();
                }
                if (insertScoresConnection != null) {
                    insertScoresConnection.close();
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }
    }

    /**
     * Inserts the students detail in the Students Table
     * @param id Student's ID
     * @param name Student's Name
     * @param department Department
     * @param email Email
     * @param phone Student Phone Number
     * @param gender Gender, How many ever are there...
     * @param semester Current Students Semester
     */
    public static void AddStudent(String id, String name, String department, String email, String phone, String gender, int semester) {
        if (CheckIdExists(id) == true) {
            System.out.println("ID already exists!...\n\n");
            return;
        }

        Connection addStudentConnection = null;
        PreparedStatement addStudentStatement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            addStudentConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "INSERT INTO Students (Id, Name, Department, Gender, Phone, Email, Semester) VALUES (?, ?, ?, ?, ?, ?, ?)";

            addStudentStatement = addStudentConnection.prepareStatement(query);

            addStudentStatement.setString(1, id);
            addStudentStatement.setString(2, name);
            addStudentStatement.setString(3, department);
            addStudentStatement.setString(4, gender);
            addStudentStatement.setString(5, phone);
            addStudentStatement.setString(6, email);
            addStudentStatement.setInt(7, semester);

            addStudentStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (addStudentStatement != null) {
                    addStudentStatement.close();
                }
                if (addStudentConnection != null) {
                    addStudentConnection.close();
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }
    }

    /**
     * Before inserting new student, check if their ID already exists in the database table.
     * @param id Students Id
     * @return true if the id already exists in the table.
     */
    private static boolean CheckIdExists(String id) {
        boolean output = false;
        Connection checkIdConnection = null;
        PreparedStatement checkIdStatement = null;
        ResultSet checkIdResult = null;

        try {
            Class.forName("org.sqlite.JDBC");
            checkIdConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "SELECT COUNT(*) FROM Students WHERE [Id] = ?";
            checkIdStatement = checkIdConnection.prepareStatement(query);
            checkIdStatement.setString(1, id);

            checkIdResult = checkIdStatement.getResultSet();

            output = checkIdResult.next();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (checkIdResult != null) {
                    checkIdResult.close();
                }
                if (checkIdStatement != null) {
                    checkIdStatement.close();
                }
                if (checkIdConnection != null) {
                    checkIdConnection.close();
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }

        return output;
    }
}