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

# lance un coordinateur avec 3 agents et 1 producteur
# format des arguments de CoordinateurImpl :
# <port rmiregistry> <nbAgents> <nbProducteurs>
# arguments utilisés : 9000 3 1 gold 100
coord:
	java CoordinateurImpl 9000 2 1

# lance un producteur
# format des arguments de ProducteurImpl :
# <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource>
# arguments utilisés : 9000 0 gold 200
prod:
	java ProducteurImpl 9000 0 gold 200

# lance un agent (trois versions un coop, un indiv et un humain)
# format des arguments de Agentxxx :
# <port du rmiregistry> <idAgent> <typeRessource> <quantiteRessource> <objectif>
# arguments utilisés : 9000 0,1,...,n gold 0 50
agent1:
	java AgentCoop 9000 0 gold 0 50
	
agent2:
	java AgentIndiv 9000 1 gold 0 50
	
agenth:
	java AgentHumain 9000 2 gold 0 50
