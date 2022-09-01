sig Bag {
        contains : Int one -> Product
}

abstract sig Product {
        stock : one Int,
        weight : one Int
}
one sig Tea , Coffee , Milk extends Product {}

fact {
        all b : Bag , p : Product | b.contains.p >= 0
        all p : Product | p.weight >= 1
        all p : Product | p.stock >= 0 and p.stock <= 3
        Milk.weight = 10
        Milk.weight > Coffee.weight
        Coffee.weight = 3 fun/mul Tea.weight
        all p : Product | p.stock >= (sum b : Bag | b.contains.p)
}

run{ some Bag and all b : Bag, i : Product | b.contains.i > 0 } for 8 Int
run{ some Bag and all b : Bag, i : Product | b.contains.i > 0 } for 10 Int

assert hasMilk {
        all b : Bag | (sum p : Product | b.contains.p fun/mul p.weight) > 30 implies b.contains.Milk >= 1
}

check hasMilk for 3 but 2 Bag, 8 Int
check hasMilk for 3 but 2 Bag, 10 Int


assert hasNoMilk {
        all b : Bag | (sum p : Product | b.contains.p fun/mul p.weight) > 40 implies b.contains.Milk >= 1
}

check hasNoMilk for 3 but 2 Bag, 8 Int
check hasNoMilk for 3 but 2 Bag, 10 Int

