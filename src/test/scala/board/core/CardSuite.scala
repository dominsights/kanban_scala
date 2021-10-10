package board.core

import org.junit.Assert.*
import java.util.{ UUID, Calendar, Date }

class CardSuite extends munit.FunSuite :
  test("should create card with title and description") {
    val title = "title"
    val description = "description"

    val card = Card.newCard(title, description, UUID.randomUUID)
    assertEquals(title, card.title)
    assertEquals(description, card.description)
    assertNotNull(card.id)
  }

  test("should load existing card with id, title and description") {
    val title = "title"
    val description = "description"
    val id = UUID.randomUUID
    val card = Card.hydrateCard(id, title, description)
    assertEquals(title, card.title)
    assertEquals(description, card.description)
    assertEquals(id, card.id)
  }

  test("should return false when date not overdue") {
    val card = Card.newCard("card", "description", UUID.randomUUID)
                .setDueDate(new Date)
    assertFalse(card.isOverdue(new Date))
  }

  test("should return true when date is overdue") {
    val calendar = Calendar.getInstance
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val card = Card.newCard("card", "description", UUID.randomUUID)
                .setDueDate(calendar.getTime)
    assertTrue(card.isOverdue(new Date))
  }

  test("shoud add new item to checklist") {
    val item = CheckListItem("description", false)
    val card = Card.newCard("card", "description", UUID.randomUUID)
                .addCheckList("checklist")
                .addCheckListItem(0, item)

    assertEquals(1, card.checklists.get(0).size)
  }

  test("shoud complete item") {
    val item = CheckListItem("description", false)
    val card = Card.newCard("card", "description", UUID.randomUUID)
                .addCheckList("checklist")
                .addCheckListItem(0, item)
                .completeCheckListItem(0, item)

    val itemChanged = card.checklists.get(0).items.head
    assertTrue(itemChanged.completed)
  }

  test("should not add item when checklist doesn't exist") {
    val item = CheckListItem("description", false)
    val card = Card.newCard("card", "description", UUID.randomUUID)
    val newCard = card.addCheckListItem(0, item)

    assertEquals(card, newCard)
  }

  test("should add member to card") {
    val member = Member.newMember(UUID.randomUUID, "member", "member")
    val card = Card.newCard("card", "description", UUID.randomUUID)
                    .addMember(member, _ => true)
    assertSame(member, card.members.get(0))
  }

  test("shouldn't add members not in board") {
    val member = Member.newMember(UUID.randomUUID, "member", "member")
    val card = Card.newCard("card", "description", UUID.randomUUID)
    val newCard = card.addMember(member, _ => false)

    assertSame(card, newCard)
  }

  test("should remove member from card") {
    val member = Member.newMember(UUID.randomUUID, "member", "member")
    val card = Card.newCard("card", "description", UUID.randomUUID)
                    .addMember(member, _ => true)
                    .removeMember(member)
    
    assertEquals(0, card.members.get.size)                    
  }
