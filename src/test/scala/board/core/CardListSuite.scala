package board.core

import org.junit.Assert
import org.junit.Assert.*

import java.util.UUID

class CardListSuite extends munit.FunSuite :
  test("should create card list") {
    val cardlist = CardList.newEmptyList(UUID.randomUUID(), "cardlist")
    assertEquals(0, cardlist.size)
    assertNotNull(cardlist.id)
  }

  test("should add new card to empty card list") {
    val card = Card.newCard("title", "description", UUID.randomUUID)
    val cardList = CardList.newEmptyList(UUID.randomUUID, "cardlist").addCard(card)

    assertSame(card, cardList.cards(0))
    assertEquals(1, cardList.size)
  }

  test("should remove card") {
    val card = Card.newCard("title", "description", UUID.randomUUID)
    val cardList = CardList.newEmptyList(UUID.randomUUID, "cardlist")
      .addCard(card)
      .removeCard(card)

    assertTrue(cardList.isEmpty)
  }

  test("should reorder cards") {
    val card1 = Card.newCard("title1", "description1", UUID.randomUUID)
    val card2 = Card.newCard("title2", "description2", UUID.randomUUID)
    val cardList = CardList.newEmptyList(UUID.randomUUID, "title")
      .addCard(card1)
      .addCard(card2)

    val newCardList = cardList.setCardPosition(1, card2)

    assertSame(card2, newCardList.cards(1))
    assertSame(card1, newCardList.cards(0))
  }

  test("should insert card between two cards") {
    val card1 = Card.newCard("title1", "description1", UUID.randomUUID)
    val card2 = Card.newCard("title2", "description2", UUID.randomUUID)
    val card3 = Card.newCard("title3", "description3", UUID.randomUUID)

    val cardList = CardList.newEmptyList(UUID.randomUUID, "title")
      .addCard(card1)
      .addCard(card2)
      .addCard(card3)

    val newCardList = cardList.setCardPosition(1, card3)

    assertSame(card2, newCardList.cards(0))
    assertSame(card1, newCardList.cards(2))
  }

  test("should not change cardlist when out of bounds") {
    val card1 = Card.newCard("title1", "description1", UUID.randomUUID)
    val card2 = Card.newCard("title2", "description2", UUID.randomUUID)
    val cardList = CardList.newEmptyList(UUID.randomUUID(), "title")
      .addCard(card1)
      .addCard(card2)

    val newCardList = cardList.setCardPosition(2, card2)

    assertSame(cardList, newCardList)
  }

  test("should load existing card list") {
    val card1 = Card.newCard("title1", "description1", UUID.randomUUID)
    val card2 = Card.newCard("title2", "description2", UUID.randomUUID)
    val cards = List(card1, card2)
    val id = UUID.randomUUID
    val title = "title"
    val cardList = CardList.hydrateCardList(id, title, cards)
    assertEquals(2, cardList.size)
    assertEquals(id, cardList.id)
    assertEquals(title, cardList.title)
  }

  test("should load cardlist from old cardlist") {
    val oldList = CardList.newEmptyList(UUID.randomUUID(), "title")
    val cardList = CardList.hydrateCardList(oldList.id, oldList.title, oldList.cards)
    assertEquals(oldList, cardList)
  }
