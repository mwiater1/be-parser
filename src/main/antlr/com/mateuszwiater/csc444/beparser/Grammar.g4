grammar Grammar;

goal : ID q ;
q : QMARK EOF | EQ e EOF ;
e : g ep ;
ep : XOR g ep | ;
g : h gp ;
gp : OR h gp | ;
h : t hp ;
hp : AND t hp | ;
t : NOT e | LP e RP | ID | TRUE | FALSE ;

ID : [a-z] ;
AND : [&] ;
OR : [|] ;
XOR : [\^] ;
NOT : [~] ;
EQ : [=] ;
QMARK : [?] ;
FALSE : [0] ;
TRUE : [1] ;
LP : [(] ;
RP : [)] ;
EOL : [\n] ;
WS : [ \r\t\n]+ -> skip ;


