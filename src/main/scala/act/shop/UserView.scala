package act.shop

import act.shop.Cart.{AddItem, Checkout, Item, ListItems}
import act.shop.UserView._
import akka.actor._

object UserView {
  trait User

  var session = 0
  def props(user: User) = {
    val next = session
    session += 1
    Props(new UserView(next, user))
  }

  case object CreateCart
  case class CartItems(items: Seq[Item])
  case object SendUser
  case class UserIs(session: Long, user: User)
}

class UserView(session: Long, user: UserView.User) extends Actor {
  var cart: ActorRef = null

  override def receive = {
    case CreateCart => if (cart == null) cart = context.actorOf(Cart.props(self))
    case CartItems(items) => println(s"Items in cart of $user: $items")
    case SendUser => sender ! UserIs(session, user)
    case msg@AddItem(i) => cart ! msg
    case msg@ListItems => cart ! msg
    case msg@Checkout => cart ! msg
  }
}
