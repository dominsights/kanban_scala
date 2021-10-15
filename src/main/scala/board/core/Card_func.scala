package board.core

import java.util.{Date, UUID}

case class Card_func(title: String, description: String, id: UUID,
                     checklists: Option[List[CheckList]], dueDate: Option[Date],
                     members: Option[List[Member]])

def isOverdue(card: Card_func, date: Date): Boolean = card.dueDate match
  case Some(d) => d.after(date)
  case None => false

def setDueDate(card: Card_func, date: Date) = card.copy(dueDate = Some(date))

def addCheckListItem(card: Card_func, checkListIndex: Int, item: CheckListItem): Card_func =
  accessCheckList(card: Card_func, checkListIndex, item, card.checklists, (i, c) => c.add(i))

def completeCheckListItem(card: Card_func, checkListIndex: Int, item: CheckListItem): Card_func =
  accessCheckList(card, checkListIndex, item, card.checklists, (i, c) => c.completeItem(i))

def accessCheckList(card: Card_func, index: Int, item: CheckListItem, checklists: Option[List[CheckList]], f: (CheckListItem, CheckList) => CheckList): Card_func =
  checklists match
    case Some(checklists) =>
      if index > checklists.size - 1 then card
      else
        val newChecklists = checklists.updated(index, f(item, checklists(index)))
        card.copy(checklists = Some(newChecklists))
    case None => card

def addCheckList(card: Card_func, title: String): Card_func =
  val newCheckList = CheckList.createEmpty(title)
  card.checklists match
    case Some(list) => card.copy(checklists = Some(newCheckList :: list))
    case None => card.copy(checklists = Some(List(newCheckList)))

def addMember(card: Card_func, member: Member, memberValidator: Member => Boolean): Card_func =
  if memberValidator(member) then
    card.members match
      case Some(memberList) => card.copy(members = Some(member :: memberList))
      case None => card.copy(members = Some(List(member)))
  else card

def removeMember(card: Card_func, member: Member) = card.members match
  case Some(m) => card.copy(members = Some(m.filterNot(_ == member)))
  case None => card