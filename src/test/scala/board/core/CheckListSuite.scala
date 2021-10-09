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
