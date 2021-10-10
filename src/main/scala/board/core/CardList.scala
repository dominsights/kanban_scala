package board.core

import java.util.UUID

class CardList private(val id: UUID, val cards: List[Card], val title: String) {
  def addCard(card: Card) = CardList(id, card :: cards, title)

  def removeCard(card: Card) = CardList(id, cards.filterNot(_ == card), title)

  def setCardPosition(i: Int, card: Card) =
    if i > cards.size - 1 then this
    else
      val (left, right) = cards.filterNot(_ == card).splitAt(i)
      CardList(id, left ::: card :: right, title)

  def isEmpty = cards.isEmpty

  def canEqual(other: Any): Boolean = other.isInstanceOf[CardList]

  override def equals(other: Any): Boolean = other match
    case that: CardList =>
      (that canEqual this) &&
        id == that.id &&
        cards == that.cards &&
        title == that.title
    case _ => false

  override def hashCode(): Int =
    val state = Seq(id, cards, title)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}

object CardList {
  def apply(id: UUID, cards: List[Card], title: String) =
    new CardList(id, cards, title)

  def newEmptyList(id: UUID, title: String) = CardList(id, List.empty, title)

  def hydrateCardList(id: UUID, title: String, cards: List[Card]) =
    new CardList(id, cards, title)
}
