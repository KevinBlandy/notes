void toBinary(unsigned long number) {
	int bin;
	bin = number % 2;
	if (number >= 2) {
		toBinary(number / 2);
	}
	putchar(bin == 0 ? '0' : '1');
}
