package act.shop

import act.shop.Cart.Item
import act.shop.UserView.{SendUser, UserIs}
import akka.actor._


object Order {
  def props(userView: ActorRef, items: Seq[Item]) = Props(new Order(userView, items))
}

class Order(userView: ActorRef, items: Seq[Item]) extends Actor {

  userView ! SendUser

  def receive = {
    case UserIs(_, user) =>
      println(s"Processing order for $user: $items")
      context.stop(self)
  }
}
