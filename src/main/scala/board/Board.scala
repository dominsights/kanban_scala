package board

import java.util.UUID
import java.util.Date

case class CheckListItem(description: String, completed: Boolean)
case class CheckList(title: String, items: List[CheckListItem])
case class Member()
case class Card(title: String, description: String, id: UUID, checklists: Option[List[CheckList]], dueDate: Option[Date], members: Option[List[Member]])

def addChecklistItem(index: Int, item: CheckListItem, checklists: Option[List[CheckList]]): Option[List[CheckList]] = 
    checklists match
        case Some(checklists) =>
            val checklist = checklists(index)
            val (left, right) = checklist.items.splitAt(index)
            val newItems: List[CheckListItem] = left ::: item :: right
            val newCheckList = checklists.updated(index, CheckList(checklist.title, newItems))
            Some(newCheckList)
        case None => None