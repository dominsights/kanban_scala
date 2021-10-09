package board.core

import org.junit.Assert.*

class CheckListSuite extends munit.FunSuite :
  test("should add new checklist item to checklist") {
    val checkListItem = CheckListItem("item", false)
    val checkList = CheckList.createEmpty("checklist")
    assertEquals(1, checkList.add(checkListItem).size)
    assertEquals(checkListItem, checkList.add(checkListItem).items.head)
  }

  test("should complete item") {
    val item1 = CheckListItem("item1", false)
    val item2 = CheckListItem("item2", false)
    val checkList = CheckList.createEmpty("checklist").add(item1).add(item2)
    val newCheckList = checkList.completeItem(item2)
    assertTrue(newCheckList.items.tail.head == item1)
    assertTrue(newCheckList.items.head.completed)
  }

  test("should remove item") {
    val checklist = CheckList.createEmpty("checklist")
    val item = CheckListItem("item", false)
    val newChecklist = checklist.add(item).remove(item)
    assertEquals(0, newChecklist.size)
  }

  test("should reorder item") {
    val checklist = CheckList.createEmpty("checklist")
    val item1 = CheckListItem("item1", false)
    val item2 = CheckListItem("item2", false)
    val newChecklist = checklist.add(item1).add(item2)
    val reorderedChecklist = newChecklist.setPosition(1, item2)
    assertSame(item1, reorderedChecklist.items(0))
    assertSame(item2, reorderedChecklist.items(1))
  }

  test("should reorder item") {
    val checklist = CheckList.createEmpty("checklist")
    val item1 = CheckListItem("item1", false)
    val item2 = CheckListItem("item2", false)
    val newChecklist = checklist.add(item1).add(item2)
    val reorderedChecklist = newChecklist.setPosition(1, item2)
    assertSame(item1, reorderedChecklist.items(0))
    assertSame(item2, reorderedChecklist.items(1))
  }
