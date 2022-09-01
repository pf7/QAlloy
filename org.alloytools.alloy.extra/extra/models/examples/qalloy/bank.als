one sig Coin {}

sig Client {}

sig Bank {
    int balance : Client -> Coin
}

sig Order {
    from, to : Client,
    int ammount : some Coin
} {
    from != to
}

pred Transfer[b,b" : Bank, o : Order] {
    o ; ammount in o.from ; (b ; balance)
    o.from ; (b" ; balance) = o.from ; (b ; balance) fun/sub o ; ammount
    o.to ; (b" ; balance) = o.to ; (b ; balance) fun/add o ; ammount
    all c : Client - o.(from+to) | c ; (b" ; balance) = c ; (b ; balance)
}

fact NonNegativity{
    balance >= 0 ** (Bank -> Client -> Coin)
    ammount >= 0 ** (Order -> Coin)
}


run{ some b, b' : Bank, o : Order | Transfer[b, b', o] } for 4 but exactly 2 Bank, 1 Order

assert TotalCoinsPreserved {
    all b,b" : Bank, o : Order | Transfer[b,b",o] implies Client ; (b" ; balance) = Client ; (b ; balance)
} 

check TotalCoinsPreserved for 3 but 2 Bank
check TotalCoinsPreserved for 4 but 2 Bank
check TotalCoinsPreserved for 5 but 2 Bank

