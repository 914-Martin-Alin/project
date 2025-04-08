import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentTest {
    private Service studentService;
    private StudentXMLRepo studentXMLRepository;
    private StudentValidator studentValidator;

    @BeforeEach
    void setUp() {
        studentValidator = new StudentValidator();
        studentXMLRepository = new StudentXMLRepo("fisiere/Studenti.xml"); // Assuming it is correctly initialized
        studentService = new Service(studentXMLRepository, studentValidator, null, null, null, null);
    }

    @Test
    void testAddValidStudent() {
        Student student = new Student("1", "John Doe", 101, "john.doe@example.com");
        Student result = studentService.addStudent(student);

        assertNotNull(result);
        assertEquals(student.getID(), result.getID());
        assertEquals(student.getNume(), result.getNume());
        assertEquals(student.getGrupa(), result.getGrupa());
        assertEquals(student.getEmail(), result.getEmail());
    }

    @Test
    void testAddInvalidStudent_EmptyFields_ShouldThrowValidationException() {
        Student invalidStudent = new Student("", "", -1, "");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(invalidStudent));
        assertTrue(exception.getMessage().contains("Id incorect!"));
    }

    @Test
    void testAddStudentWithEmptyName_ShouldThrowValidationException() {
        Student student = new Student("2", "", 103, "jane.doe@example.com");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Nume incorect!"));
    }

    @Test
    void testAddStudentWithEmptyId_ShouldThrowValidationException() {
        Student student = new Student("", "Jane Doe", 103, "jane.doe@example.com");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Id incorect!"));
    }

    @Test
    void testAddStudentWithNullId_ShouldThrowValidationException() {
        Student student = new Student(null, "Jane Doe", 103, "jane.doe@example.com");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Id incorect!"));
    }

    @Test
    void testAddStudentWithNullName_ShouldThrowValidationException() {
        Student student = new Student("2", null, 103, "jane.doe@example.com");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Nume incorect!"));
    }

    @Test
    void testAddStudentWithNullEmail_ShouldThrowValidationException() {
        Student student = new Student("3", "Alex Smith", 104, null);
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Email incorect!"));
    }

    @Test
    void testAddStudentWithEmptyEmail_ShouldThrowValidationException() {
        Student student = new Student("4", "Emma Brown", 105, "");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Email incorect!"));
    }

    @Test
    void testAddStudentWithNegativeGroup_ShouldThrowValidationException() {
        Student student = new Student("5", "Mark Johnson", -10, "mark.johnson@example.com");
        Exception exception = assertThrows(ValidationException.class, () -> studentService.addStudent(student));
        assertTrue(exception.getMessage().contains("Grupa incorecta!"));
    }
}