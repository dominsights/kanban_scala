package board.core

import java.util.UUID

class Member private (id: UUID, name: String, username: String) {

}

object Member {
    def newMember(id: UUID, name: String, username: String) =
        new Member(id, name, username)
}
