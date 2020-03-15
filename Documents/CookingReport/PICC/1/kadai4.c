#include<pic.h>
#include"delay.h"
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void interr(void);
void bell(char);
int a = 0;
int b = 0;
char bell_flg = 0;
char x[13]={191,170,151,191,170,151,127,151,170,191,170,151,170};
char y[13]={1,1,2,1,1,2,1,1,1,1,1,1,2};

void main()
{
	RB1 = 0;
	interr();
	ioport();
	while(1)
	{
		while(RA0 == 0)
		{
			GIE = 1;
			bell(x[b]);
		}
		GIE = 0;
		RB1 = 0;
	}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}
void bell(char a)
{
	RB1 = 0;
	DelayUs(a);
	RB1 = 1;
	DelayUs(a);
}
void interr(void)
{
	GIE = 0;
	T0IE = 1;
	T0IF = 0;
 	TMR0 = 0;
	T0CS = 0;
	PSA = 0;
	PS0 = 1;
	PS1 = 1;
	PS2 = 1;
}	
interrupt isr()
{
	T0IF = 0;
	a = a + 1;
	if(a == 39*y[b])
	{
		b++;
		if(b == 13)
			{
				b = 0;	
			}
		a = 0;
	}
}
