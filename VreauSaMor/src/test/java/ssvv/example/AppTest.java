package ssvv.example;

import domain.Student;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    private StudentXMLRepo xmlRepo;
    private Service service;

    @BeforeEach
    public void setUp() {
        xmlRepo = new StudentXMLRepo("fisiere/Studenti.xml");
        TemaXMLRepo temeRepo = new TemaXMLRepo(("fisiere/Teme.xml"));
        service = new Service(
                xmlRepo,
                new StudentValidator(),
                temeRepo,
                new TemaValidator(),
                new NotaXMLRepo("fisiere/Note.xml"),
                new NotaValidator(xmlRepo, temeRepo));
    }

    @org.junit.jupiter.api.Test
    public void testAddStudent_Success()
    {
        Student student = new Student("1", "A", 1, "a@a");
        Student newStudent = service.addStudent(student);

        assertEquals(student, newStudent);
    }

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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


    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
    public void testAddStudent_DuplicateId()
    {
        Student student = new Student("8", "H", 8, "a@a");
        service.addStudent(student);

        try {
            service.addStudent(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Id incorect!");
        }
    }
}
