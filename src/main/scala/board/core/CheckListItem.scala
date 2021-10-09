package board.core

class CheckListItem(description: String, val completed: Boolean) {
  def complete(): CheckListItem = CheckListItem(description, true)
}

object CheckListItem {
  def apply(description: String, completed: Boolean) = new CheckListItem(description, completed)
}
