sig Node {
        int Capacity : set Node,
        int Flow : set Node
}
one sig Source, Sink extends Node {}

fact FlowNetwork {
	Capacity >= 0 ** (Node -> Node)
	Flow  >= 0 ** (Node -> Node)

	no Capacity & iden
	all n : Node - Source | Source in ^(drop Flow) . n

	Flow in Capacity
	all n : Node - Source - Sink | #(n ; Flow) = #(Flow ; n)

	#Source ; Flow >= #Flow ; Source
	#Flow ; Sink >= #Sink ; Flow
}


run threeNodes{} for 4 but exactly 3 Node

assert OutEqualsIn {
	#(Source ; Flow) fun/sub #(Flow ; Source) = #(Flow ; Sink) fun/sub #(Sink ; Flow) 
} 
check OutEqualsIn for 3
check OutEqualsIn for 4


