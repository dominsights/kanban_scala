package board.core

class CheckList private(val title: String, val items: List[CheckListItem]) {
  def add(item: CheckListItem) = CheckList(title, item :: items)

  def size = items.size

  def remove(item: CheckListItem) = CheckList(title, items.filterNot(_ == item))

  def setPosition(i: Int, item: CheckListItem) =
    if i > items.size - 1 then this
    else
      val (left, right) = items.splitAt(i)
      CheckList(title, left ::: item :: right)

  def completeItem(item: CheckListItem) =
    val newItems = items.map(i => if i == item then item.complete() else i)
    CheckList(title, newItems)
}

object CheckList {
  private def apply(title: String, items: List[CheckListItem]): CheckList =
    new CheckList(title, items)

  def createEmpty(title: String) = CheckList(title, List.empty)
}
