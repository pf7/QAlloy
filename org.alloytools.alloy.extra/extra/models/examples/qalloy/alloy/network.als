sig Node {
        adj : set Node,
        capacity : Int one -> Node,
        flow : Int one -> Node
}

one sig Source, Sink extends Node {}

fact FlowNetwork {
        no adj & iden
        let F = { n, n' : Node | n.flow.n' > 0 } | all n : Node - Source | Source in ^F.n

        all a,b : Node | a.capacity.b > 0 implies a->b in adj
        all a,b : Node | a.capacity.b >= 0
        all a,b : Node | a.flow.b >= 0

        all a,b : Node | a.capacity.b >= a.flow.b
        all n : Node - Source - Sink | (sum x : Node | n.flow.x) = (sum x : Node | x.flow.n)   

        (sum x : Node  | Source.flow.x) >= (sum x : Node | x.flow.Source)
        (sum x : Node | x.flow.Sink) >= (sum x : Node | Sink.flow.x)
}


run threeNodes{} for 4 but exactly 3 Node, 8 Int
run threeNodes{} for 4 but exactly 3 Node, 10 Int


assert OutEqualsIn {
        (sum x : Node | Source.flow.x) fun/sub (sum x : Node | x.flow.Source) = (sum x : Node | x.flow.Sink) fun/sub (sum x : Node | Sink.flow.x)
} 

check OutEqualsIn for 3 but 8 Int
check OutEqualsIn for 4 but 8 Int
check OutEqualsIn for 5 but 8 Int

check OutEqualsIn for 3 but 10 Int
check OutEqualsIn for 4 but 10 Int
check OutEqualsIn for 5 but 10 Int



