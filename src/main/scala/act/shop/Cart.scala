package act.shop

import akka.actor._
import act.shop.Cart._
import act.shop.UserView.CartItems
import act.shop.OrderManager.CreateOrder

object Cart {
  def props(session: ActorRef): Props = Props(new Cart(session))

  trait Item
  case class AddItem(i: Item)
  case object ListItems
  case object Checkout
}


import akka.actor._

class Cart(session: ActorRef) extends Actor {
  var items = Seq[Item]()

  override def receive = {
    case AddItem(i) => items +:= i
    case ListItems => sender() ! CartItems(items)
    case Checkout =>
      val orderMgr = context.actorSelection("/user/orderMgr")
      orderMgr ! CreateOrder(session, items)
      items = Seq()
  }
}
