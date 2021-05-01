# ConceptionEtDeveloppement21
Projet2021

Le lien pour télécharger la librairie APACHE POI : https://www.apache.org/dyn/closer.lua/poi/release/bin/poi-bin-5.0.0-20210120.zip

Mode d'emploi :

Dans le package controleur, dans la classe Controleur, ajouter un nouvel utilisateur. Pour se faire, rajouter un case dans le switch en dessous de ceux existants, avec le chemin d'accès du dossier DE SORTIE. Tous les fichiers crées par la suite seront dans ce dossier.

Exemple d'ajout d'utilisateur : 
          case "Professeur" :
                this.arrivee ="C:/Documents/L2/ProjetCDA/";
                break;

Une fois l'utilisateur ajouté, il ne reste plus qu'à se rendre dans la classe Main, et à remplacer l'utilisateur courant par votre nom d'utilisateur dans la création de l'objet Controleur. Ex: Controleur controleur = new Controleur("Professeur");

L'utilisation de l'interface graphique se fera au moyen de la commande "controleur.controleurIHM();"
Sinon, le Controleur dispose de plusieurs méthodes permettant de tout réaliser manuellement, laissés en commentaire dans le main pour qui voudrait les tester. (notamment le test de diversité qui n'est pas une option de l'interface graphique)

