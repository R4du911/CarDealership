# Model.Car-Dealership

Ein Model.Car-Dealership braucht eine Anwendung, an der sich Kunden für das Kaufen von Model.Merchandise anmelden können.

Jedes Auto ist eine Model.Merchandise, die einen Brand, ein Model, ein Registrierungsjahr, eine Motorisierung, einen Kaufpreis und eine Liste von verwendbaren Parts hat. Jeder Model.Part ist auch eine Model.Merchandise, die einen Brand, ein Model und eine Liste von Cars hat, für denen es verwendbar ist.    

Jeder Kunde ist eine Model.Person, die eine Geldsumme und eine Liste von Orders hat. Dazu, jeder Verkäufer ist eine Model.Person, die einen Gehalt und Zugriff auf das Inventar von Model.Merchandise besitzt. Das Inventar stellt alle Merch aus dem Dealership dar.

Außerdem, jede Model.Order ist eine Liste von Model.Merchandise  , die er kaufen möchte, und zudem noch das Datum, an dem die Model.Order abgeschlossen wurde.

1.	Die Anwendung ermöglicht es den Kunden Model.Merchandise zu kaufen, entweder ein bestimmtes Auto oder einen bestimmten Model.Part.

2.	Mit der Anwendung können Sie die verfügbaren Model.Merchandise(Autos und Parts) und deren Kaufpreis sehen.

3.	Die Anwendung ermöglicht die Anzeige von Autos und von Parts, die ein Kunde besitzt.

4.	Jeder Kunde kann Model.Merchandise(Autos und Parts) kaufen, solange er Geld dafür hat. Wenn der Kunde ein Auto oder einen Model.Part kaufen möchte,  aber kein Geld dafür hat, erhält er eine Fehlermeldung und das Auto/Model.Part wird nicht zu seiner Orderliste hinzugefügt.

5.	Die Anwendung bietet die Möglichkeit an, mehrere Autos/Parts auf eine Model.Order zu bestellen.





Klassendiagramm
----------------------------------------------------------------------------------------------------------------------------------------------------------------
![Diagrama3](https://user-images.githubusercontent.com/114949622/198679873-cf849bc9-8cd6-4023-b6f6-2a2baf5317b2.png)


