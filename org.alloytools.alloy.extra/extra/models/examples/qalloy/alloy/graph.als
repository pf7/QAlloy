sig Node {
        label : one Label,
        adj : set Node
}

sig Label {
        adj : Int one -> Label,
        count : one Int
}

fact Counting {
        all l : Label | l.count = #(label.l)
        all l0,l1 : Label | l0.adj.l1 = #(adj & label.l0 -> label.l1)
}

run {} for 5 but 8 Int
run {} for 5 but 10 Int

assert Counting {
        #Node = (sum l : Label | l.count)
        #(Node <: adj) = (sum l0,l1 : Label | l0.adj.l1)
}

check Counting for 3 but 8 Int
check Counting for 4 but 8 Int
check Counting for 5 but 8 Int

check Counting for 3 but 10 Int
check Counting for 4 but 10 Int
check Counting for 5 but 10 Int

pred Forest {
        all n : Node | n not in n.^adj
        all n : Node | lone adj.n
}

fun Roots : set Label {
        (Node - Node.adj).label
}

run Forest for 5 but 8 Int
run Forest for 5 but 10 Int

assert ForestInDegree {
        Forest implies (all l0 : Label - Roots | l0.count = (sum l1 : Label | l1.adj.l0))
}

check ForestInDegree for 3 but 8 Int
check ForestInDegree for 4 but 8 Int
check ForestInDegree for 5 but 8 Int

check ForestInDegree for 3 but 10 Int
check ForestInDegree for 4 but 10 Int
check ForestInDegree for 5 but 10 Int

pred Connected {
        all n : Node | Node in n.*adj
}

run Connected for 5 but 8 Int
run Connected for 5 but 10 Int

assert ConnectedInDegree {
        Connected and #Node > 1 implies (all l0 : Label | l0.count <= (sum l1 : Label | l1.adj.l0))
} 

check ConnectedInDegree for 3 but 8 Int
check ConnectedInDegree for 4 but 8 Int
check ConnectedInDegree for 5 but 8 Int

check ConnectedInDegree for 3 but 10 Int
check ConnectedInDegree for 4 but 10 Int
check ConnectedInDegree for 5 but 10 Int


