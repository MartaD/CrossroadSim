CrossroadSim
============
Metody main znajdują się w klasach "Program.java" w każdej z paczek


###Model Wolframa - pojedynczy pas
  Prosty model w którym komórka sprawdza czy ta przed nią jest wolna, jeśli tak zmienia stan na 0 w następnej iteracji jeśli nie, to pozostawia 1
  (treningowy, może się przyda)


###Model NAGELA-SCHRECKENBERGA - pojedynczy pas, światła
  Komórki przechowują swoją prędkość. Komórka jest "żywa" jeśli ma prędkość (nie jest null), jej wartośc zależy od odległości od następnego pojazu lub świateł (jeśli jest czerwone)
  * Do modyfikacji częstotliwości zmiany świateł trzeba zmienić zmienną **lightsChange** (im wyższa tym dłuższa zmiana)
  * Do modyfikacji częstotliwości pojawiania się nowych samochodów służy zmienna **spawn**
