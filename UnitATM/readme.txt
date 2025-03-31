Program wiersza poleceń, napisany w języku C, symuluje działanie abstrakcyjnej maszyny ATM. Przyjmuje na standardowe wejście kwotę, którą następnie rozmienia na żetony.

Obowiązkowe parametry:
  -m: zasoby maszyny, czyli ilość żetonów przekazane w formacie: <unit>:<uint>:<uint>, gdzie każda liczba odpowiada     
      odpowiedniemu nominałowi. Nominały maszyny to 5, 2 i 1.
  -t: parametr określający, gdzie mają być wypisywane żetony. Może być to standardowe wyjście.

Opcjonalne parametry:
  -u: skala nominałów maszyny, określona jako potęga liczby dziesięć. Parametr zmienia wartości nominałów maszyny. Wartość domyslna jest 0.
  -s: ziarno generatora. Wartość domyślna to wartość PID programu.
  -d: opóźnienie w wypisywaniu żetonów, w centysekundach. Wartość domyślna to 1.

Program oblicza ilość żetonów, a następnie wypisuje je na podane wyjście w losowej kolejności. W przypadku braku wystarczających zasobów wypisuje na standardowe wyjście kwotę, której nie udało się wydać.
