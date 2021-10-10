package board.core

import java.util.{Date, UUID}

class Card private(val title: String, val description: String, val id: UUID,
                   val checklists: Option[List[CheckList]], dueDate: Option[Date],
                   val members: Option[List[Member]]) {

  def isOverdue(date: Date): Boolean = dueDate match
    case Some(d) => d.after(date)
    case None => false

  def setDueDate(date: Date) = Card(title, description, id, checklists, Some(date), members)

  def addCheckListItem(checkListIndex: Int, item: CheckListItem): Card = accessCheckList(checkListIndex, item, checklists, (i, c) => c.add(i))

  def completeCheckListItem(checkListIndex: Int, item: CheckListItem): Card = accessCheckList(checkListIndex, item, checklists, (i, c) => c.completeItem(i))

  def accessCheckList(index: Int, item: CheckListItem, checklists: Option[List[CheckList]], f: (CheckListItem, CheckList) => CheckList): Card =
    checklists match
      case Some(checklists) =>
        if index > checklists.size - 1 then this
        else
          val newChecklists = checklists.updated(index, f(item, checklists(index)))
          Card(title, description, id, Some(newChecklists), dueDate, members)
      case None => this

  def addCheckList(title: String): Card =
    val newCheckList = CheckList.createEmpty(title)
    checklists match
      case Some(list) => Card(title, description, id, Some(newCheckList :: list), dueDate, members)
      case None => Card(title, description, id, Some(List(newCheckList)), dueDate, members)

  def addMember(member: Member, memberValidator: Member => Boolean): Card =
    if memberValidator(member) then
      members match
        case Some(memberList) => Card(title, description, id, checklists, dueDate, Some(member :: memberList))
        case None => Card(title, description, id, checklists, dueDate, Some(List(member)))
    else this

  def canEqual(other: Any): Boolean = other.isInstanceOf[Card]

  override def equals(other: Any): Boolean = other match
    case that: Card =>
      (that canEqual this) &&
        title == that.title &&
        description == that.description &&
        id == that.id &&
        checklists == that.checklists &&
        members == that.members
    case _ => false

  override def hashCode(): Int =
    val state = Seq(title, description, id, checklists, members)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}

object Card {
  private def apply(title: String, description: String, id: UUID,
                    checklists: Option[List[CheckList]], dueDate: Option[Date],
                    members: Option[List[Member]]): Card = {
    new Card(title, description, id, checklists, dueDate, members)
  }

  def newCard(title: String, description: String, cardId: UUID) =
    Card(title, description, cardId, None, None, None)

  def hydrateCard(id: UUID, title: String, description: String) = Card(title, description, id, None, None, None)
}
