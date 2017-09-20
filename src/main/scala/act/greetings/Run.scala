package act.greetings

import act.greetings.Greeter.{Greet, WhoToGreet}
import akka.actor.ActorSystem

object Run extends App {
  val system = ActorSystem.create("greetings")

  val printer = system.actorOf(Printer.props, "Printer")
  val howdyGreeter = system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
  val helloGreeter = system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
  val goodDayGreeter = system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")


  howdyGreeter ! WhoToGreet("Alice")
  helloGreeter ! WhoToGreet("Bob")
  goodDayGreeter ! WhoToGreet("Carol")

  howdyGreeter ! Greet
  helloGreeter ! Greet
  goodDayGreeter ! Greet

  Thread.sleep(3000)
  system.terminate()
}
