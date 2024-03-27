package ssvv.example;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class AssignmentTests
{
    private TemaXMLRepo xmlRepo;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/test-teme.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.xmlRepo = new TemaXMLRepo("fisiere/test-teme.xml");
        this.service = new Service(
                null,
                null,
                this.xmlRepo,
                new TemaValidator(),
                null,
                null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/test-teme.xml").delete();
    }

    @Test
    void testAddAssignment_Success() {
        Tema newTema = new Tema("1", "a", 1, 1);

        this.service.addTema(newTema);

        assertEquals(newTema, this.xmlRepo.findOne("1"));
    }

}
