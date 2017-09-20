package act.shop

import act.greetings.Greeter.{Greet, WhoToGreet}
import act.shop.Cart.{AddItem, Checkout, Item, ListItems}
import act.shop.UserView.CreateCart
import akka.actor.{ActorSystem, Props}

case class SimpleUser(name: String) extends UserView.User
case class SimpleItem(name: String) extends Cart.Item

object Run extends App {
  val system = ActorSystem.create("shop")

  val view1 = system.actorOf(UserView.props(SimpleUser("alice")))
  val view2 = system.actorOf(UserView.props(SimpleUser("bob")))

  val orderMgr = system.actorOf(OrderManager.props(), "orderMgr")

  view1 ! CreateCart
  view2 ! CreateCart

  view1 ! AddItem(SimpleItem("Gum"))
  view2 ! AddItem(SimpleItem("Juice"))

  view1 ! AddItem(SimpleItem("Lemons"))
  view2 ! AddItem(SimpleItem("Salmon"))

  view1 ! ListItems
  view2 ! ListItems

  view2 ! AddItem(SimpleItem("Garlic"))
  view2 ! AddItem(SimpleItem("Oil"))

  view1 ! ListItems
  view2 ! ListItems

  view1 ! Checkout
  view2 ! Checkout

  Thread.sleep(1000)
  system.terminate()
}
