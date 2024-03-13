package tests;

import domain.Student;
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


public class AddStudentTests {
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

    @Test
    public void testAddStudent_Success()
    {
        Student student = new Student("1", "A", 1, "a@a");
        Student newStudent = service.addStudent(student);

        assertEquals(student, newStudent);
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
}
