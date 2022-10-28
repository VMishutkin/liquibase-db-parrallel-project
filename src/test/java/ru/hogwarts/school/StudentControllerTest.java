package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void addTest() throws Exception {
        Student student = new Student();
        student.setId(100L);
        student.setAge(15);
        student.setName("test");
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, String.class)).contains("test");
    }

    @Test
    public void putTest() throws Exception {
        Student student = new Student();
        student.setId(100L);
        student.setAge(15);
        student.setName("puttest");
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class)).isNotNull();
    }


    @Test
    public void getByIdTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class)).isNotNull();
    }

    @Test
    public void deleteTest() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/student/10", String.class);
    }

    @Test
    public void getByAgeTest() throws Exception {
        Student student = new Student();
        student.setAge(99);
        student.setName("agetest");
        student.setFaculty(null);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, String.class)).contains("agetest");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/byAge/99", String.class)).contains("agetest");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/byAge/99", String.class)).contains("agetest");
    }

    @Test
    public void getStudentsFacultyTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setAge(15);
        student.setName("puttest");

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("test");
        faculty.setName("faculty1");
        student.setFaculty(faculty);
        this.restTemplate.put("http://localhost:" + port + "/student/1", student, Student.class);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getfaculty/1", String.class)).isNotNull();

    }
}
