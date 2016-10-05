automatic_gesture_generation
============================

http://hdl.handle.net/10230/22188

Humanitzant avatars: Generació automàtica de gestos a partir de la parla.

El programa, que utilitza un algorisme d’aprenentatge automàtic, disposarà d’un conjunt de dades d’entrenament
que hem obtingut prèviament mitjançant l’etiquetatge manual.

Les dades d’entrada del programa es passaran en format XML, tant les dades d’entrenament com les dades de test.

4classes:

-  XMLreader: en aquesta classe es tracten els fitxers d’entrada en format XML, se n’extreuen les dades i es guarden
en classes Assignacions o AssignacionsTest en funció de si son dades d’entrenament o de test, creant una instancia 
per a cada tag. També es crida en aquesta classe a les funcions que tracten les dades i imprimeixen els resultats
demanats.

-	MainTFG: és el main del programa, només executa l’XMLreader.
-	Assignacions: és la classe que tracta les dades d’entrenament; es crea un objecte Assignacions per a cada tag.
-	AssignacionsTest: en aquesta classe es tracten les dades de test, també es crea un objecte per a cada tag.

Learn more about this project: http://hdl.handle.net/10230/22188
