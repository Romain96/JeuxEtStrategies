########################################################################
# Makefile
# Projet : Jeux et Stratégies
# Auteur : Romain PERRIN 
########################################################################

# compile les fichiers du projet
compil:
	echo "Compilation des fichiers du projet pour test"
	javac *.java
	
# nettoie le répertoire du projet
clean:
	echo "Nettoyage de tous les fichier class"
	rm *.class
	
########################################################################
# cibles temporaires (utilisées pour les tests)
########################################################################

# initialise un remiregistry sur le port 9000
reg:
	rmiregistry 9000 &

# lance un coordinateur avec 2 agents et 1 producteur
# format des arguments de ObjetCoordinateur :
# <port rmiregistry> <nbAgents> <nbProducteurs> <typeRessource> <quantiteRessource>
# arguments utilisés : 9000 2 1 gold 100
coord:
	java ObjetCoordinateur 9000 2 1 gold 100

# lance un producteur
# format des arguments de ObjetProducteur :
# <machine du Serveur> <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource>
# arguments utilisés : localhost 9000 0 gold 200
prod:
	java ObjetProducteur localhost 9000 0 gold 200

# lance un agent (deux versions un coop et un indiv)
# format des arguments de ObjetAgent :
# <machine du Serveur> <port du rmiregistry> <personnalite> <idAgent> <typeRessource> <quantiteRessource> <objectif>
# arguments utilisés : localhost 9000 coop/indiv 0,1,...,n gold 0 50
agent1:
	java ObjetAgent localhost 9000 coop 0 gold 0 50
	
agent2:
	java ObjetAgent localhost 9000 indiv 1 gold 0 50
