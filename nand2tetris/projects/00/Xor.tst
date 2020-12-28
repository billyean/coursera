load Xor.hdl,
output-file Xor.out,
output-list a b out;

set a 0, set b 0, eval;
set a 0, set b 1, eval;
set a 1, set b 0, eval;
set a 1, set b 1, eval;