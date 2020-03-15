#include<pic.h>
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void main()
{
	ioport();
	while(1)
	{
		RB1 = RA0;
	}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}