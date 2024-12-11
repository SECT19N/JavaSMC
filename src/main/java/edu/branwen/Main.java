package edu.branwen;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Student> Students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    /**
     * Start of Application
     */
    public static void main(String[] args) {

        //Section4617

        try {
            while (true) {
                String username, password;

                System.out.print("Enter username: ");
                username = sc.next();

                System.out.print("Enter password: ");
                password = sc.next();

                if (LoginOperations.CheckUser(username, password) == false) {
                    if (TryAgainPrompt() == false) {
                        System.out.println("Closing application...");

                        return;
                    }
                } else {
                    break;
                }
            }

            int InputOption = 0;
            while (true) {
                System.out.println("1 - View All Students\n2 - Search\n3 - Add New Student\n4 - Close");
                System.out.print("\nSelect an option: ");
                InputOption = sc.nextInt();

                if (InputOption == 1) {
                    Students = StudentOperations.ViewStudents();
                    PrintList(Students);
                } else if (InputOption == 2) {
                    System.out.print("Please enter the student ID or Name: ");
                    String studentNameOrId = sc.next();

                    Students = StudentOperations.SearchStudents(studentNameOrId);
                    PrintList(Students);

                    System.out.println();
                } else if (InputOption == 3) {

                    System.out.print("Student ID: ");
                    String id = sc.next();
                    sc.nextLine(); //Because of error.

                    System.out.print("Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Department: ");
                    String department = sc.next();

                    System.out.print("Gender: ");
                    String gender = sc.next();

                    System.out.print("Phone: ");
                    String phone = sc.next();

                    System.out.print("Email: ");
                    String email = sc.next();

                    System.out.print("Semester: ");
                    int semester = sc.nextInt();

                    System.out.print("Subject 1: ");
                    double s1 = sc.nextDouble();

                    System.out.print("Subject 2: ");
                    double s2 = sc.nextDouble();

                    System.out.print("Subject 3: ");
                    double s3 = sc.nextDouble();

                    System.out.print("Subject 4: ");
                    double s4 = sc.nextDouble();

                    System.out.print("Subject 5: ");
                    double s5 = sc.nextDouble();

                    StudentOperations.AddStudent(id, name, department, email, phone, gender, semester);
                    StudentOperations.InsertScores(id, semester, s1, s2, s3, s4, s5);
                } else if (InputOption == 4) {
                    break;
                } else {
                    System.out.println("Wrong input... try again.\n");
                }
            }

            System.out.println("Closing Application...\n\n\n");
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * When user enters incorrect details, prompt them if they want to retry.
     * @return true if user enters yes
     */
    private static boolean TryAgainPrompt() {
        String yn;
        sc.nextLine();

        System.out.println("Username or Password is wrong!");
        System.out.print("Would you like to try again? (Y/N): ");

        yn = sc.nextLine().trim().toLowerCase();

        return yn.equals("y") || yn.contains("yes");
    }

    /**
     * Function that prints student list.
     * @param students given Student List to print.
     */
    private static void PrintList(ArrayList<Student> students) {
        System.out.println("+------+--------------------------+---------+-----------------+-------------+---------------------------+---------+--------+--------+--------+--------+--------+");
        System.out.println("| ID   | Name                     | Dept    | Gender          | Phone       | Email                     | Sem     | Score1 | Score2 | Score3 | Score4 | Score5 |");
        System.out.println("+------+--------------------------+---------+-----------------+-------------+---------------------------+---------+--------+--------+--------+--------+--------+");

        for (Student stu : students) {
            System.out.println(stu.toString());
        }

        System.out.println("+------+--------------------------+---------+-----------------+-------------+---------------------------+---------+--------+--------+--------+--------+--------+");
    }
}