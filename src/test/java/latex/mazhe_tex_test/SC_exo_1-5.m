M=
[3			0		-1		0		0	0 
 4			0		0		-1		0	0
(1.25)*5	-0.21	0	    0		0	0
 0			0.21	-1		-0.5	0	-1
 0			0		1		1		1	1
 0			0.79	0		0		-1	0
 ]
u = [0 0 0 0 100 0]

reponse = M\u'
   %3.1484
   %93.7031
   % 9.4453
   %12.5937
   %74.0255
   % 3.9355

air = reponse(2)	% Parce qu'on a mis A dans la 2ième colone de M
 %93.703
