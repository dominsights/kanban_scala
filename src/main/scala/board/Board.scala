package board

import java.util.UUID
import java.util.Date

case class CheckListItem(description: String, completed: Boolean)
case class CheckList(title: String, items: List[CheckListItem])
case class Member()
case class Card(
    title: String,
    description: String,
    id: UUID,
    checklists: Option[List[CheckList]],
    dueDate: Option[Date],
    members: Option[List[Member]]
)

def addChecklistItem(
    addItem: (CheckListItem, CheckList) => CheckList,
    checklistIndex: Int,
    item: CheckListItem,
    checklists: Option[List[CheckList]]
): Option[List[CheckList]] =
  checklists match
    case Some(checklists) =>
      val checklist = checklists(checklistIndex)
      val newChecklists =
        checklists.updated(checklistIndex, addItem(item, checklist))
      Some(newChecklists)
    case None =>
      None

def addChecklistItem(
    item: CheckListItem,
    checklist: CheckList
): CheckList =
  val newItems: List[CheckListItem] = checklist.items :+ item
  checklist.copy(items = newItems)

def completeChecklistItem(
    completeItem: (CheckListItem, CheckList) => CheckList,
    checklistItem: Int,
    item: CheckListItem,
    checklists: Option[List[CheckList]]
): Option[List[CheckList]] =
  checklists match
    case Some(checklists) =>
      val checklist = checklists(checklistItem)
      val newChecklists =
        checklists.updated(
          checklistItem,
          completeItem(item, checklist)
        )
      Some(newChecklists)
    case None =>
      None

def completeChecklistItem(
    item: CheckListItem,
    checklist: CheckList
): CheckList =
  val index = checklist.items.indexOf(item)
  val newItems =
    checklist.items.updated(index, item.copy(completed = true))
  checklist.copy(items = newItems)
