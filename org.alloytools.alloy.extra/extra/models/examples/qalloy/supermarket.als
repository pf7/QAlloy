sig Bag {
	int contains : set Product
}

one sig Oz {}
abstract sig Product {
	int weight : one Oz
}
one sig Tea , Coffee , Milk extends Product {}
int sig stock in Product {}

fact {
	contains >= 0 ** (Bag -> Product)
	weight >= 1 ** (Product -> Oz)
	stock >= 0 ** Product and stock <= 3** Product
	Milk ; weight = 10 ** Oz
	Milk ; weight > Coffee ; weight
	Coffee ; weight = 3 **( Tea ; weight )
	Bag ; contains in stock
}

run{
	some Bag and all b : Bag, p : Product | p in b.contains
} for 3

assert hasMilk {
	all b : Bag | b;contains;weight > 30 ** Oz implies b;contains >= 1 ** Milk
}

assert hasNoMilk{
	all b : Bag | b;contains;weight > 40 ** Oz implies b;contains >= 1 ** Milk
}

check hasMilk for 3 but 2 Bag
check hasNoMilk for 3 but 2 Bag




