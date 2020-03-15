#include<pic.h>
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void main()
{
	ioport();
	while(1)
	{
		int a;
		PORTB = 0b1111;
		for(a=0;a<10000;a++);
		PORTB=0;
		for(a;a>0;a--);
	}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}