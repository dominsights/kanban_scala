import org.junit.Test
import org.junit.Assert.*
import board.{CheckListItem, CheckList}
import board._

class BoardSuite extends munit.FunSuite:
  test("should add new checklist item to checklist list") {
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

  test("should add new checklist item to checklist") {
    val checklistItem = CheckListItem("item", false)
    val checklist = CheckList("checklist", List(checklistItem))
    assertEquals(1, checklist.items.size)

    val newChecklist = addChecklistItem(
      CheckListItem("new item", false),
      checklist
    )
    assertEquals(2, newChecklist.items.size)
  }

  test("should complete checklist item in checklist list") {
    val checklistItem = CheckListItem("item", false)
    val checklist = CheckList("checklist", List(checklistItem))
    val newChecklist =
      completeChecklistItem(0, checklistItem, Some(List(checklist)))
    assertTrue(newChecklist.get(0).items(0).completed)
  }

  test("should complete checklist item in checklist") {
    val checklistItem = CheckListItem("item", false)
    val checklist = CheckList("checklist", List(checklistItem))
    val newChecklist = completeChecklistItem(checklistItem, checklist)
    assertTrue(newChecklist.items(0).completed)
  }
