package machine

data class Coffee(
    val water: Int = 0,
    val milk: Int = 0,
    val beans: Int = 0,
    val cup: Int = 1,
    var price: Int = 0
)


fun main() {
    var coffeeMachine = CoffeeMachine()

    val cappuccino = Coffee(
        water = 200,
        milk = 100,
        beans = 12,
        cup = 1,
        price = 6
    )
    val latte = Coffee(
        water = 350,
        milk = 75,
        beans = 20,
        cup = 1,
        price = 7
    )
    val espresso = Coffee(
        water = 250,
        milk = 0,
        beans = 16,
        cup = 1,
        price = 4,
    )

    mainLoop@ while (true) {
        println("Write action (buy, fill, take, remaining, exit): >")
        val input = readLine()!!
        coffeeMachine.saveState(input)
        when (input) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: >")
                val input2 = readLine()!!
                coffeeMachine.saveState(input2)
                when (input2) {
                    "1" -> {
                        if (coffeeMachine.isEnough(coffeeMachine, espresso)) {
                            println("I have enough resources, making you a coffee!")
                            coffeeMachine = coffeeMachine.makeCoffee(espresso, coffeeMachine)
                        } else {
                            println("Sorry, not enough ${coffeeMachine.whatsLeft(coffeeMachine, espresso)}!")
                        }
                    }
                    "2" -> {
                        if (coffeeMachine.isEnough(coffeeMachine, latte)) {
                            println("I have enough resources, making you a coffee!")
                            coffeeMachine = coffeeMachine.makeCoffee(latte, coffeeMachine)
                        } else {
                            println("Sorry, not enough ${coffeeMachine.whatsLeft(coffeeMachine, latte)}!")
                        }
                    }
                    "3" -> {

                        if (coffeeMachine.isEnough(coffeeMachine, cappuccino)) {
                            println("I have enough resources, making you a coffee!")
                            coffeeMachine.makeCoffee(cappuccino, coffeeMachine)
                        } else {
                            println("Sorry, not enough ${coffeeMachine.whatsLeft(coffeeMachine, cappuccino)}!")
                        }
                    }
                    "back" -> continue@mainLoop
                }
            }
            "fill" -> {
                coffeeMachine = coffeeMachine.fill(coffeeMachine)
            }
            "take" -> {
                println()
                println("I gave you ${coffeeMachine.totalMoney}")
                coffeeMachine.totalMoney = 0
                println()
            }
            "remaining" -> coffeeMachine.printState(coffeeMachine)
            "exit" -> break@mainLoop
        }
    }
}

class CoffeeMachine(
    var totalWater: Int = 400,
    var totalMilk: Int = 540,
    var totalCoffeeBeans: Int = 120,
    var totalMoney: Int = 550,
    var totalCups: Int = 9
) {
    var savedState = ""

    fun saveState(state: String): String {
        savedState = state
        return state
    }

    fun makeCoffee(coffee: Coffee, coffeeMachine: CoffeeMachine): CoffeeMachine {
        val coffeeReplace = CoffeeMachine()
        coffeeMachine.totalWater -= coffee.water
        coffeeMachine.totalMilk -= coffee.milk
        coffeeMachine.totalCoffeeBeans -= coffee.beans
        coffeeMachine.totalMoney += coffee.price
        coffeeMachine.totalCups--
        return coffeeReplace
    }

    fun isEnough(coffeeMachine: CoffeeMachine, coffee: Coffee): Boolean {
        return  coffeeMachine.totalWater > coffee.water &&
                coffeeMachine.totalMilk > coffee.milk &&
                coffeeMachine.totalCoffeeBeans > coffee.beans &&
                coffeeMachine.totalCups > 0
    }

    fun whatsLeft(coffeeMachine: CoffeeMachine, coffee: Coffee): String {
        var toReturn = ""
        when {
            coffeeMachine.totalWater < coffee.water -> toReturn = "water"
            coffeeMachine.totalMilk < coffee.milk -> toReturn = "milk"
            coffeeMachine.totalCoffeeBeans < coffee.beans -> toReturn = "coffee beans"
            coffeeMachine.totalCups < coffee.cup -> toReturn = "cups"
        }
        return  toReturn
    }

    fun printState(coffeeMachine: CoffeeMachine) {
        println()
        println("The coffee machine has:")
        println("${coffeeMachine.totalWater} ml of water")
        println("${coffeeMachine.totalMilk } ml of milk")
        println("${coffeeMachine.totalCoffeeBeans} g of coffee beans")
        println("${coffeeMachine.totalCups} disposable cups")
        println("${coffeeMachine.totalMoney} of money")
        println()
    }
    fun fill(machine: CoffeeMachine): CoffeeMachine {
        println()
        println("Write how many ml of water do you want to add: >")
        machine.totalWater += readLine()!!.toInt()
        println("Write how many ml of milk do you want to add: >")
        machine.totalMilk += readLine()!!.toInt()
        println("Write how many grams of coffee beans do you want to add: >")
        machine.totalCoffeeBeans += readLine()!!.toInt()
        println("Write how many disposable cups of coffee do you want to add: >")
        machine.totalCups += readLine()!!.toInt()
        println()
        return machine
    }
}













