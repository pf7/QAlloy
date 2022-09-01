sig Node {
        int Capacity : Litre lone -> set Node,
        int Flow : Litre lone -> set Node
}
one sig Source, Sink extends Node {}

one sig Litre{}

fun Adj[N : Node -> Litre -> Node] : Node -> Node {
	{ n, n' : Node | n->Litre->n' in N }
}

fact FlowNetwork {
	Capacity >= 0 ** (Node -> Litre -> Node)
	Flow >= 0 ** (Node -> Litre -> Node)

	no Adj[Capacity] & iden
	 all n : Node - Source | Source in ^(Adj[Flow]) . n

	Flow in Capacity
	all n : Node - Source - Sink | n ; Flow ; Node = Node ; Flow ; n

	Source ; Flow ; Node >= Node ; Flow ; Source
	Node ; Flow ; Sink >= Sink ; Flow ; Node
}

run threeNodes{} for 4 but exactly 3 Node

assert OutEqualsIn {
	Source ; Flow ; Node fun/sub Node ; Flow ; Source = Node ; Flow ; Sink fun/sub Sink ; Flow ; Node 
} 
check OutEqualsIn for 3
check OutEqualsIn for 4
check OutEqualsIn for 5

