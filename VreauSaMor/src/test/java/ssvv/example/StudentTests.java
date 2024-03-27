package ssvv.example;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class StudentTests
{
    private StudentXMLRepo xmlRepo;
    private Service service;

    @BeforeAll
    static void createXML() {
        // Create a new xml file
        try {
            File xmlFile = new File("fisiere/test-Studenti.xml");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile))) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<inbox>\n" +
                        "\n" +
                        "</inbox>");
                writer.flush();
            }
        } catch (Exception e) {
            fail();
        }


    }

    @BeforeEach
    public void setUp() {
        xmlRepo = new StudentXMLRepo("fisiere/test-Studenti.xml");
        this.service = new Service(
                xmlRepo,
                new StudentValidator(),
                null,
                null,
                null,
                null);
    }

    @Test
    public void testAddStudent_DuplicateId()
    {
        Student student = new Student("8", "H", 8, "a@a");
        service.addStudent(student);

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Studentul exista deja!");
        }
    }

    @Test
    public void testAddStudent_Success()
    {
        Student student = new Student("2", "A", 1, "a@a");

        try {
            service.addStudent(student);

            // Get the student from the repository
            Student newStudent = xmlRepo.findOne("2");

            assertEquals(student, newStudent);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testAddStudent_EmptyId()
    {
        Student student = new Student("", "B", 2, "a@a");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Id incorect!");
        }
    }

    @Test
    public void testAddStudent_NullId()
    {
        Student student = new Student(null, "B", 2, "a@a");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Id incorect!");
        }
    }

    @Test
    public void testAddStudent_EmptyName()
    {
        Student student = new Student("3", "", 3, "a@a");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Nume incorect!");
        }
    }

    @Test
    public void testAddStudent_NullName()
    {
        Student student = new Student("4", null, 4, "a@a");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Nume incorect!");
        }
    }


    @Test
    public void testAddStudent_EmptyEmail()
    {
        Student student = new Student("5", "E", 5, "");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Email incorect!");
        }
    }

    @Test
    public void testAddStudent_NullEmail()
    {
        Student student = new Student("6", "F", 6, null);

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Email incorect!");
        }
    }

    @Test
    public void testAddStudent_NegativeGroup()
    {
        Student student = new Student("7", "G", -7, "a@a");

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Grupa incorecta!");
        }
    }


}