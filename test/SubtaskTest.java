import org.junit.jupiter.api.Test;
import task.Subtask;

import static org.junit.jupiter.api.Assertions.*;
class SubtaskTest {
    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask sub1 = new Subtask("Sub 1", "Desc", 1);
        Subtask sub2 = new Subtask("Sub 2", "Other", 2);
        sub1.setId(100);
        sub2.setId(100);
        assertEquals(sub1, sub2);
    }
    @Test
    void testSubtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask("Sub", "desc", 10);
        assertNotEquals(subtask.getEpicId(), subtask.getId(), "Подзадача не должна быть своим же эпиком");
    }
}