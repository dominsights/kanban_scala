import org.junit.Test
import org.junit.Assert.*
import board.{CheckListItem, CheckList}
import board._

class BoardSuite extends munit.FunSuite:
  test("should add new checklist item") {
    val checklistItem = CheckListItem("item", false)
    val checklist = CheckList("checklist", List(checklistItem))
    assertEquals(1, checklist.items.size)

    val newChecklist = addChecklistItem(
      0,
      CheckListItem("new item", false),
      Some(List(checklist))
    )
    assertEquals(2, newChecklist.get(0).items.size)
  }
