package act.shop

import act.shop.Cart.{Item}
import act.shop.OrderManager.CreateOrder
import akka.actor._

object OrderManager {
  def props(): Props = Props(new OrderManager())
  case class CreateOrder(userView: ActorRef, items: Seq[Item])
}

class OrderManager extends Actor {
  def receive = {
    case CreateOrder(userView, items) => context.actorOf(Order.props(userView, items))
  }
}
