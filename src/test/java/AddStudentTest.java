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
        studentXMLRepository = new StudentXMLRepo("AddStudentTest"); // Assuming it is correctly initialized
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
    void testAddInvalidStudent_ShouldThrowValidationException() {
        Student invalidStudent = new Student("", "", -1, "");

        Exception exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                studentService.addStudent(invalidStudent);
            }
        });

        String expectedMessage = "Id incorect!"; // Change based on validation order
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}