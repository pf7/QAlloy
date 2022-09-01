sig Client {}

sig Bank {
    balance : Client set -> one Int
}

sig Order {
    from, to : Client,
    ammount : one Int
} {
    from != to
}

pred Transfer[b,b" : Bank, o : Order] {
    o.ammount <= b.balance[o.from]
    b".balance[o.from] = b.balance[o.from] - o.ammount
    b".balance[o.to] = b.balance[o.to] + o.ammount
    all c : Client - o.(from+to) | b".balance[c] = b.balance[c]
}

fact NoNegativeBalance{
	all b : Bank, c : Client | b.balance[c] >= 0
}

fact PositiveAmmount{
	all o : Order | o.ammount > 0
}

run{ some b, b" : Bank, o : Order | Transfer[b, b", o] } for 4 but exactly 2 Bank, 1 Order, 8 Int
run{ some b, b" : Bank, o : Order | Transfer[b, b", o] } for 4 but exactly 2 Bank, 1 Order, 10 Int

assert TotalCoinsPreserved {
    all b,b" : Bank, o : Order | Transfer[b,b",o] implies b".balance[Client] = b.balance[Client]
}

check TotalCoinsPreserved for 3 but 2 Bank, 8 Int
check TotalCoinsPreserved for 4 but 2 Bank, 8 Int
check TotalCoinsPreserved for 5 but 2 Bank, 8 Int

check TotalCoinsPreserved for 3 but 2 Bank, 10 Int
check TotalCoinsPreserved for 4 but 2 Bank, 10 Int
check TotalCoinsPreserved for 5 but 2 Bank, 10 Int


