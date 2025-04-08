import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaXMLRepo;
import service.Service;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddAssignmentTest {

    private Service assignmentService;
    private TemaXMLRepo assignmentXMLRepository;
    private TemaValidator assignmentValidator;

    @BeforeEach
    void setUp() {
        assignmentValidator = new TemaValidator();
        assignmentXMLRepository = new TemaXMLRepo("fisiere/Teme.xml"); // Assuming it is correctly initialized
        assignmentService = new Service(null, null, assignmentXMLRepository, assignmentValidator, null, null);
    }

    @Test
    void testAddValidAssignment() {
        Tema assignment = new Tema("1", "Tema 1", 3, 1);
        Tema result = assignmentService.addTema(assignment);

        assertNotNull(result);
        assertEquals(assignment.getID(), result.getID());
        assertEquals(assignment.getDescriere(), result.getDescriere());
        assertEquals(assignment.getDeadline(), result.getDeadline());
        assertEquals(assignment.getPrimire(), result.getPrimire());

    }

    @Test
    void testAddAssignmentWithEmptyNr_ShouldThrowValidationException() {
        Tema assignment = new Tema("", "Tema 1", 3, 1);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Numar tema invalid!"));
    }

    @Test
    void testAddAssignmentWithEmptyDescription_ShouldThrowValidationException() {
        Tema assignment = new Tema("1", "", 3, 1);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Descriere invalida!"));
    }

    @Test
    void testAddAssignmentWithDeadlineTooEarly_ShouldThrowValidationException() {
        Tema assignment = new Tema("1", "Tema 1", 0, 1);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Deadlineul trebuie sa fie intre 1-14."));
    }


    @Test
    void testAddAssignmentWithDeadlineTooLate_ShouldThrowValidationException() {
        Tema assignment = new Tema("1", "Tema 1", 15, 1);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Deadlineul trebuie sa fie intre 1-14."));
    }

    @Test
    void testAddAssignmentWithPrimireTooEarly_ShouldThrowValidationException() {
        Tema assignment = new Tema("1", "Tema 1", 3, 0);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Saptamana primirii trebuie sa fie intre 1-14."));
    }


    @Test
    void testAddAssignmentWithPrimireTooLate_ShouldThrowValidationException() {
        Tema assignment = new Tema("1", "Tema 1", 3, 15);
        Exception exception = assertThrows(ValidationException.class, () -> assignmentService.addTema(assignment));
        assertTrue(exception.getMessage().contains("Saptamana primirii trebuie sa fie intre 1-14."));
    }

}